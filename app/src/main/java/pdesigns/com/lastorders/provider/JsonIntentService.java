package pdesigns.com.lastorders.provider;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Patrick on 04/03/2016.
 */
public class JsonIntentService extends IntentService {

    private static final String TAG =
            "JsonService";

    /**
     * Instantiates a new Json intent service.
     */
    public JsonIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
        Log.i(TAG, "Intent Service started");
    }
}
