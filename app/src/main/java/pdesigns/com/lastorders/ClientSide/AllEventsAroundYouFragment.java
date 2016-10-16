package pdesigns.com.lastorders.ClientSide;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pdesigns.com.lastorders.Adapters.AroundEventCustomListAdapter;
import pdesigns.com.lastorders.DTO.Event;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.GPSTracker;
import pdesigns.com.lastorders.provider.JSONParser;
import pdesigns.com.lastorders.provider.Server_Connections;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type Around you fragment.
 */
public class AllEventsAroundYouFragment extends Fragment implements Serializable, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    //JSON node names
    private static final String TAG_EVENTS = "event";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CITYNAME = "city_name";
    private static final String TAG_COUNTRY = "country_name";
    private static final String TAG_COUNTRYABBR = "country_abbr";
    private static final String TAG_REGION = "region_name";
    private static final String TAG_STARTTIME = "start_time";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LONG = "longitude";
    private static final String TAG_LAT = "latitude";
    private static final String TAG_ADDR = "venue_address";


    /**
     * The Prgm images.
     */
    public static ArrayList<String> prgmImages;
    /**
     * The Prgm fbs.
     */
    public static ArrayList<String> prgmFbs;
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private static String url_all_events = "http://api.eventful.com/json/events/search?app_key=GZbQKhM9tQRRvH2w&location=";

    /**
     * The Json parser.
     */
// create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Bar list.
     */
    ArrayList<HashMap<Event, String>> eventList;
    /**
     * The B.
     */
    Event e;
    /**
     * The Sc.
     */
    Server_Connections sc = new Server_Connections();
    /**
     * The Gps.
     */
// GPSTracker class
    GPSTracker gps;
    /**
     * The Bars.
     */
// images JSON Array
    JSONArray events = null;
    /**
     * The Refresh toggle.
     */
    boolean refreshToggle = false;
    /**
     * The Loc 1.
     */
    Location loc1;
    /**
     * The Session.
     */
// Session Manager Class
    SessionManager session;
    boolean answer = true;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private int mActionBarHeight = 0;
    //Diaglog to show db connections status
    private ProgressDialog myDialog;
    private AroundEventCustomListAdapter mAdapter;
    private ArrayList<Event> mStrings = new ArrayList<Event>();
    private ArrayList<Event> mBars;
    private ArrayList<Integer> dis;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ListView simpleList = null;
    //SwipeView Stuffs
    private SwipeRefreshLayout swipeView;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //hashmap for GridView
            mStrings = new ArrayList<Event>();

            refreshToggle = true;
            mAdapter.notifyDataSetChanged();
            mAdapter.clear();
            new LoadAllComments().execute();


            mAdapter = new AroundEventCustomListAdapter(getActivity(), mStrings);
            simpleList.setAdapter(new AroundEventCustomListAdapter(getActivity(), mStrings));


            swipeView.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction("pdesigns.com.lastorders.ClientSide");
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    getActivity().sendBroadcast(intent);

                    Toast.makeText(getContext(),
                            "Refreshing.....", Toast.LENGTH_SHORT).show();
                    swipeView.setRefreshing(false);
                }
            }, 1000);
        }

    };
    /**
     * The Handler.
     */
    private String sortMe = "distance";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        session = new SessionManager(this.getContext());
        session.checkLogin();


        // Session class instance
        session = new SessionManager(this.getContext());


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_first_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.clear_cache:
                Toast.makeText(getActivity(), R.string.clear_cache_menu,
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.weview1ToMe:
                Intent i = new Intent(this.getContext(), BarWebView.class);
                startActivity(i);
                return true;
            case R.id.logout:
                session.logoutUser();
            case R.id.refreshData:
                intent = new Intent();
                intent.setAction("pdesigns.com.lastorders.ClientSide");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                this.getActivity().sendBroadcast(intent);
                this.onResume();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_eventlist, container, false);

        swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeView.setDistanceToTriggerSync(20);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used


        //hashmap for GridView
        eventList = new ArrayList<HashMap<Event, String>>();
        loc1 = new Location("");

        gps = new GPSTracker(this.getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            loc1.setLatitude(latitude);
            loc1.setLongitude(longitude);

            // \n is for new line
        } else {
            // can't get location
            // GPS or Network is not enabledz
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        new LoadAllComments().execute();


        Log.d("MStrings", mStrings.toString());


        //   mAdapter = new AroundCustomListAdapter(this.getActivity(), mStrings, prgmImages, dis);
        mAdapter = new AroundEventCustomListAdapter(this.getActivity(), mStrings);

        simpleList = (ListView) v.findViewById(R.id.list_all_events);
        simpleList.setAdapter(new AroundEventCustomListAdapter(this.getActivity(), mStrings));

        simpleList.setOnItemClickListener(this);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Log.d("list item selected was ", 1 + "");

        Log.d("The Dbb Array", mStrings.toString());
        Intent intent = new Intent(getActivity(), EventMainViewActivity.class);
        intent.putExtra("eventszzz", mStrings.get(i));
        intent.putExtra("barzzz", "");
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        swipeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeView.setRefreshing(true);
                handler.sendEmptyMessage(0);
            }
        }, 1000);

    }

    // A background async task to load all bars by making http request
    private class LoadAllComments extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            myDialog = new ProgressDialog(getActivity());
            myDialog.setMessage("Loading Events.  Please wait....");
            myDialog.setIndeterminate(false);
            myDialog.setCancelable(false);
            myDialog.show();
            //hashmap for GridView
            mStrings = new ArrayList<Event>();
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "on the doInBackground part!!");
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            try {
                //getting json string from url

                Log.d("All url to send", url_all_events + loc1.getLatitude() + "," + loc1.getLongitude() + "&within=25&page_size=20");
                JSONObject jsonObject = jsonParser.makeHttpRequest(url_all_events + loc1.getLatitude() + "," + loc1.getLongitude() + "&within=25&page_size=20", "GET", params);

                // log information for the jspon responce
                Log.d("All images", jsonObject.toString());


                // check for success tag
                System.out.println("CONNNECTION --------------------------------------------------");
                // int success = jsonObject.getInt(TAG_SUCCESS);
                //System.out.println(success + "");
                // the images was found
                // Getting the array of the images

                Log.d("Bob", "hi Bob");
                String jsonTmp;
                jsonTmp = jsonObject.toString();
                int finalPostion = jsonTmp.indexOf(",\"events\":");
                StringBuilder str = new StringBuilder(jsonTmp);
                str.replace(0, finalPostion + 10, "");
                int lastBracker = str.lastIndexOf("}");
                str.replace(lastBracker, lastBracker, "");
                jsonTmp = str.toString();
                //  Log.d("parsedString", jsonTmp);
                System.out.println(jsonTmp);
                jsonObject = new JSONObject(jsonTmp);
                events = jsonObject.getJSONArray(TAG_EVENTS);

                Log.d("jsonLength", events.toString() + "");
                // time to loop through all the images
                for (int i = 0; i < events.length(); i++) {
                    Log.d("i aye ", i + "");
                    JSONObject c = events.getJSONObject(i);
                    Log.d("ccc", events.getJSONObject(i).toString());
                    //Storing each json item in varible
                    String name = c.getString(TAG_TITLE);
                    Log.d("name", name);
                    String citName = c.getString(TAG_CITYNAME);
                    Log.d("citName", citName);
                    String country = c.getString(TAG_COUNTRY);
                    Log.d("country", country);
                    String countAbbr = c.getString(TAG_COUNTRYABBR);
                    Log.d("countAbbr", countAbbr);
                    String region = c.getString(TAG_REGION);
                    Log.d("region", region);
                    String startTime = c.getString(TAG_STARTTIME);
                    Log.d("startTime", startTime);
                    String description = c.getString(TAG_DESCRIPTION);
                    Log.d("description", description);
                    String lata = c.getString(TAG_LONG);
                    String longa = c.getString(TAG_LAT);

                    Log.d("lata", lata);
                    String address = c.getString(TAG_ADDR);
                    Log.d("address", address);
                    e = new Event(name, citName, country, countAbbr, region, startTime, description, address, longa, lata);
                    //    e = new Event(id, name, locale, phoneNo, openingHours, fbUrl, barPic, rtn);

                    String longA = e.getLonga();
                    String lat = e.getLata();

                    Location loc2 = new Location("");
                    //   40.7128° N, 74.0059° W
                    loc2.setLatitude(Double.parseDouble(longA));
                    loc2.setLongitude(Double.parseDouble(lat));
                    Log.d("Location", loc2 + "");
                    Integer distanceInMeters = Math.round(loc1.distanceTo(loc2) / 1000);
                    Log.d("Distanfe", distanceInMeters + "");


                    e.setDistanceAway(distanceInMeters);
                    mStrings.add(e);
                    Log.d("Array", e.toString());


                }

            } catch (Exception e) {
                myDialog.dismiss();
                e.printStackTrace();
                Log.d("Exception123", e.getMessage());
                // If you need update UI, simply do this:
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // update your UI component here. in order to display friendly error report
                        Toast.makeText(getActivity(), R.string.server_is_down,
                                Toast.LENGTH_SHORT).show();
                    }
                });


                // e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            // dismiss the dialog after getting all products
            myDialog.dismiss();
            // updating UI from Background Thread
            // updating UI from Background Thread


            // updating UI from Background Thread
            getActivity().runOnUiThread(
                    new Runnable() {
                        public void run() {

                            Collections.sort(mStrings);
                            mAdapter = new AroundEventCustomListAdapter(getActivity(), mStrings);
                            simpleList.setAdapter(mAdapter);
                            simpleList.setAdapter(mAdapter);


                        }
                    });
        }


    }


}
