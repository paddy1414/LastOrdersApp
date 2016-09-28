package pdesigns.com.lastorders.ImageHandlers;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Patrick on 07/08/14.
 */
public class FileCach {
    private File cachDir;

    /**
     * Instantiates a new File cach.
     *
     * @param context the context
     */
    public FileCach(Context context) {
        //find the directoryu tp save cached images
        if (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)) {
            cachDir = new File(Environment.getExternalStorageDirectory(), "HappyAppy");
        } else {
            cachDir = context.getCacheDir();
        }

        if (!cachDir.exists()) {
            cachDir.mkdirs();
        }
    }

    /**
     * Gets file.
     *
     * @param url the url
     * @return the file
     */
    public File getFile(String url) {
        // Identtify image by the hashcode.
        String filename = String.valueOf(url.hashCode());

        //String filename = URLEncoder.encode(url)
        File f = new File(cachDir, filename);
        return f;
    }

    /**
     * Clear.
     */
    public void clear() {
        File[] files = cachDir.listFiles();
        if (files == null) {
            return;
        }

        for (File f : files) {
            f.delete();
        }
    }
}
