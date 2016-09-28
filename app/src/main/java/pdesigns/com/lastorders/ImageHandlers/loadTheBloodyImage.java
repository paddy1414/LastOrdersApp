package pdesigns.com.lastorders.ImageHandlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pdesigns.com.lastorders.R;


/**
 * Created by Patrick on 06/08/14.
 */
public class loadTheBloodyImage {
    /**
     * The Loading id.
     */
    final int loading_id = R.drawable.loading1;
    /**
     * The Memory cach.
     */
    MemoryCach memoryCach = new MemoryCach();
    /**
     * The Executor service.
     */
    ExecutorService executorService;
    /**
     * The Handler.
     */
//handler to display images in the UI thread
    Handler handler = new Handler();
    /**
     * The File cache.
     */
    FileCach fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    /**
     * Instantiates a new Load the bloody image.
     *
     * @param c the c
     */
    public loadTheBloodyImage(Context c) {
        fileCache = new FileCach(c);
        executorService = Executors.newFixedThreadPool(5);
    }

    /**
     * Decode file bitmap.
     *
     * @param f the f
     * @return the bitmap
     */
    public static Bitmap decodeFile(File f) {
        try {
            //first decode with inJudtDevodeBounds = true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, options);

            //calculate inSampleSIze
            final int reqHeight = 70;
            final int reqWidth = 70;
            final int oldHeight = options.outHeight;
            final int oldWidth = options.outWidth;

            int scale = 1;

            if (oldHeight > reqHeight || oldWidth > reqHeight) {
                final int halfHeight = oldHeight / 2;
                final int halfWidth = oldWidth / 2;

                //calculate the largest inSameSize value that is a power of 2 and keep both
                //height and width larger than requested heighnt and width

                while ((halfHeight / scale) > reqHeight && (halfWidth / scale) > reqWidth) {
                    scale *= 2;
                }
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);

            return bitmap;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Display image.
     *
     * @param url       the url
     * @param imageView the image view
     */
    public void DisplayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCach.get(url);
        if (bitmap != null) {
            // sets the rescaled image to my specified size
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
        } else {
            queuePhoto(url, imageView);
            imageView.setImageResource(loading_id);
        }


    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);

        //from SD card cach
        Bitmap b = decodeFile(f);
        if (b != null) {
            return b;
        }

        //from web/// VERY IMPORTANT

        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setInstanceFollowRedirects(true);
            InputStream is = connection.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            connection.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError) {
                memoryCach.clear();

            }
            return null;
        }
    }

    private boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        return tag == null || !tag.equals(photoToLoad.url);
    }

    /**
     * Clear cache.
     */
    public void clearCache() {
        memoryCach.clear();
        fileCache.clear();
    }

    private class PhotoToLoad {
        /**
         * The Url.
         */
        public String url;
        /**
         * The Image view.
         */
        public ImageView imageView;

        /**
         * Instantiates a new Photo to load.
         *
         * @param u  the u
         * @param iv the iv
         */
        public PhotoToLoad(String u, ImageView iv) {
            url = u;
            imageView = iv;
        }
    }

    private class PhotosLoader implements Runnable {
        /**
         * The Photo to load 1.
         */
        PhotoToLoad photoToLoad1;

        /**
         * Instantiates a new Photos loader.
         *
         * @param photoToLoadInternal the photo to load internal
         */
        PhotosLoader(PhotoToLoad photoToLoadInternal) {
            this.photoToLoad1 = photoToLoadInternal;
        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad1)) {
                    return;
                }
                Bitmap bmp = getBitmap(photoToLoad1.url);
                memoryCach.put(photoToLoad1.url, bmp);

                if (imageViewReused(photoToLoad1)) {
                    return;
                }

                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad1);
                handler.post(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private class BitmapDisplayer implements Runnable {
        /**
         * The Bitmap.
         */
        Bitmap bitmap;
        /**
         * The Photo to load 3.
         */
        PhotoToLoad photoToLoad3;

        /**
         * Instantiates a new Bitmap displayer.
         *
         * @param b the b
         * @param p the p
         */
        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad3 = p;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad3)) {
                return;
            }
            if (bitmap != null) {
                photoToLoad3.imageView.setImageBitmap(bitmap);
            } else {
                photoToLoad3.imageView.setImageResource(loading_id);
            }

        }
    }


}
