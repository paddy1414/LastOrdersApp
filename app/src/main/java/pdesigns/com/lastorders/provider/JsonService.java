package pdesigns.com.lastorders.provider;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.DatabaseArrays.DatabaseFbPages;
import pdesigns.com.lastorders.DatabaseArrays.databaseBars;
import pdesigns.com.lastorders.DatabaseArrays.databaseImages;

/**
 * Created by Patrick on 04/03/2016.
 */
public class JsonService extends Service {
    private static final String TAG =
            "JsonService";
    //JSON node names
    private static final String TAG_BARS = "bars";
    private static final String TAG_PID = "barId";
    private static final String TAG_NAME = "barName";
    private static final String TAG_PICURL = "pictureUrl";
    private static final String TAG_LOCATION = "barLocation";
    private static final String TAG_FBPAGE = "barFBPage";
    //JSON node names
    private static final String TAG_SUCCESS = "success";
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private static String url_all_bars = Server_Connections.SERVER + "get_all_bars.php";
    /**
     * The Json parser.
     */
// create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Loc 1.
     */
    Location loc1;
    /**
     * The Bar list.
     */
    ArrayList<HashMap<String, String>> barList;
    /**
     * The Images list.
     */
    ArrayList<HashMap<String, String>> imagesList;
    /**
     * The B.
     */
    Bar b = new Bar();
    /**
     * The Dbb.
     */
//Object Arrays
    databaseBars dbb = new databaseBars();
    /**
     * The Dbl.
     */
    databaseImages dbl = new databaseImages();
    /**
     * The Db f.
     */
    DatabaseFbPages dbF = new DatabaseFbPages();
    /**
     * The Bars.
     */
// images JSON Array
    JSONArray bars = null;
    /**
     * The Gps.
     */
    GPSTracker gps;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        loc1 = new Location("");

        gps = new GPSTracker(this.getApplicationContext());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            loc1.setLatitude(latitude);
            loc1.setLongitude(longitude);

            // \n is for new line
            Toast.makeText(this.getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        Log.i(TAG, "JsonService onStartCommand " + startId);

        final int currentId = startId;
        Toast.makeText(this.getApplicationContext(), "Json Service Started",
                Toast.LENGTH_SHORT).show();

        Runnable r = new Runnable() {
            public void run() {

                Log.d("doInBackground", "on the doInBackground part!!");
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                synchronized (this) {
                    try {
                        //getting json string from url

                        JSONObject jsonObject = jsonParser.makeHttpRequest(url_all_bars, "GET", params);

                        // log information for the jspon responce
                        Log.d("All images", jsonObject.toString());


                        // check for success tag
                        System.out.println("CONNNECTION --------------------------------------------------");
                        int success = jsonObject.getInt(TAG_SUCCESS);
                        System.out.println(success + "");
                        // the images was found
                        // Getting the array of the images
                        bars = jsonObject.getJSONArray(TAG_BARS);

                        // time to loop through all the images
                        for (int i = 0; i < bars.length(); i++) {
                            JSONObject c = bars.getJSONObject(i);

                            //Storing each json item in varible

                            String name = c.getString(TAG_NAME);
                            String fbUrl = c.getString(TAG_FBPAGE);

                            String barPic = c.getString(TAG_PICURL);

                            String locale = c.getString(TAG_LOCATION);

                            int id = c.getInt(TAG_PID);

                            b.setId(id);
                            b.setBarName(name);
                            b.setBarFb(fbUrl);
                            b.setBarLocale(locale);
                            b.setBarPic(fbUrl);
                            // creating a new hashmap
                            //   HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to the hashMap key --> value
                            // map.put(TAG_PID, id);
                            // map.put(TAG_NAME, name);

//                    imagesList.add(map);

                            int comma = b.getBarLocale().indexOf(",");
                            String longA = b.getBarLocale().substring(0, comma - 1);
                            String lat = b.getBarLocale().substring(comma + 1, b.getBarLocale().length() - 1);

                            Location loc2 = new Location("");
                            loc2.setLatitude(Double.parseDouble(longA));
                            loc2.setLongitude(Double.parseDouble(lat));
                            Log.d("Location", loc2 + "");
                            Integer distanceInMeters = Math.round(loc1.distanceTo(loc2) / 1000);
                            Log.d("Distanfe", distanceInMeters + "");


                            // mStrings.add(i, name);
                            dbb.addBars(id, name, locale, fbUrl, barPic, distanceInMeters);

                            //  dbl.addImages(i, barPic);
                            //  dbF.addFbList(i, fbUrl);
                            Log.i(TAG, "Service running " + currentId);
                        }
                        stopSelf();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this.getApplicationContext(), "Json Service Ended",
                Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Service onDestroy");
    }
}
