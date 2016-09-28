package pdesigns.com.lastorders.ImageHandlers;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Patrick on 06/08/14.
 */
public class MemoryCach {
    private static final String TAG = "MemoryCache";
    private Map<String, Bitmap> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, Bitmap>(10, 1.5f, true)); // last argument true for LRU Ordering

    private long size = 0; //current aloctated size

    private long limit = 1000000; // max memory in bytes

    /**
     * Instantiates a new Memory cach.
     */
    public MemoryCach() {
        //user 25% of the avalible heap size
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    /**
     * Sets limit.
     *
     * @param new_limit the new limit
     */
    public void setLimit(long new_limit) {
        limit = new_limit;
        Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
    }

    /**
     * Get bitmap.
     *
     * @param id the id
     * @return the bitmap
     */
    public Bitmap get(String id) {
        try {
            if (!cache.containsKey(id))

            {
                return null;
            }
            //NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78
            return cache.get(id);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Put.
     *
     * @param id     the id
     * @param bitmap the bitmap
     */
    public void put(String id, Bitmap bitmap) {
        try {
            if (cache.containsKey(id)) {
                size = size - getSizeInBytes(cache.get(id));
            }
            cache.put(id, bitmap);
            size = size + getSizeInBytes(bitmap);
            checkSize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void checkSize() {
        Log.i(TAG, "cache size=" + size + " length= " + cache.size());

        if (size > limit) {
            Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator(); //the oldest accesed will be the first iterated


            while (iter.hasNext()) {
                Entry<String, Bitmap> entery = iter.next();
                size = size - getSizeInBytes(entery.getValue());
                iter.remove();
                if (size <= limit) {
                    break;
                }
            }
            Log.i(TAG, "Clean Cache. New Size " + cache.size());
        }
    }

    /**
     * Clear.
     */
    public void clear() {
        try {
            cache.clear();
            size = 0;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }

        return bitmap.getRowBytes() * bitmap.getHeight();
    }


}
