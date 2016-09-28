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

import pdesigns.com.lastorders.Adapters.AroundCustomListAdapter;
import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.DTO.Sorting;
import pdesigns.com.lastorders.ImageHandlers.ImageCache;
import pdesigns.com.lastorders.ImageHandlers.ImageFetcher;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.GPSTracker;
import pdesigns.com.lastorders.provider.JSONParser;
import pdesigns.com.lastorders.provider.JsonService;
import pdesigns.com.lastorders.provider.Server_Connections;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type Around you fragment.
 */
public class AroundYouFragment extends Fragment implements Serializable, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BARS = "bars";
    private static final String TAG_PID = "barId";
    private static final String TAG_NAME = "barName";
    private static final String TAG_PICURL = "pictureUrl";
    private static final String TAG_LOCATION = "barLocation";
    private static final String TAG_HOURS = "openingHours";
    private static final String TAG_PHONE = "phoneNo";
    private static final String TAG_FBPAGE = "barFBPage";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private static final String TAG_AVERAGE = "average";
    /**
     * The Prgm images.
     */
    public static ArrayList<String> prgmImages;
    /**
     * The Prgm fbs.
     */
    public static ArrayList<String> prgmFbs;
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private static String url_all_bars = Server_Connections.SERVER + "get_all_bars.php";
    /**
     * The Json parser.
     */
// create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Bar list.
     */
    ArrayList<HashMap<Bar, String>> barList;
    /**
     * The B.
     */
    Bar b;
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
    JSONArray bars = null;
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
    private AroundCustomListAdapter mAdapter;
    private ArrayList<Bar> mStrings = new ArrayList<Bar>();
    private ArrayList<Bar> mBars;
    private ArrayList<Integer> dis;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageFetcher mImageFetcher;
    private ListView simpleList = null;
    //SwipeView Stuffs
    private SwipeRefreshLayout swipeView;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //hashmap for GridView
            mStrings = new ArrayList<Bar>();

            refreshToggle = true;
            mAdapter.notifyDataSetChanged();
            mAdapter.clear();
            new LoadAllBars().execute();


            mAdapter = new AroundCustomListAdapter(getActivity(), mStrings, answer);
            simpleList.setAdapter(new AroundCustomListAdapter(getActivity(), mStrings, answer));


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

        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // Session class instance
        session = new SessionManager(this.getContext());

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.no);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
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
                mImageFetcher.clearCache();
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

            case R.id.startService:
                intent = new Intent(this.getContext(), JsonService.class);
                getActivity().startService(intent);
                return true;

            case R.id.sortbyrating:

                if (answer == true) {
                    Toast.makeText(getActivity(), R.string.sortingbyrating,
                            Toast.LENGTH_SHORT).show();
                    answer = false;
                } else if (answer == false) {
                    Toast.makeText(getActivity(), R.string.sortingbydistanec,
                            Toast.LENGTH_SHORT).show();
                    answer = true;
                }

                new LoadAllBars().execute();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_around_you, container, false);

        swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeView.setDistanceToTriggerSync(20);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used


        //hashmap for GridView
        barList = new ArrayList<HashMap<Bar, String>>();
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

        new LoadAllBars().execute();


        Log.d("MStrings", mStrings.toString());


        mAdapter = new AroundCustomListAdapter(this.getActivity(), mStrings, answer);

        simpleList = (ListView) v.findViewById(R.id.listallaround);
        simpleList.setAdapter(new AroundCustomListAdapter(this.getActivity(), mStrings, answer));

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
        Intent itents = new Intent(getActivity(), BarMainViewActivity.class);
        itents.putExtra("barzzz", mStrings.get(i));
        startActivity(itents);
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
    private class LoadAllBars extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            myDialog = new ProgressDialog(getActivity());
            myDialog.setMessage("Loading Nearby Bars.  Please wait....");
            myDialog.setIndeterminate(false);
            myDialog.setCancelable(false);
            myDialog.show();
            //hashmap for GridView
            mStrings = new ArrayList<Bar>();
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "on the doInBackground part!!");
            List<NameValuePair> params = new ArrayList<NameValuePair>();


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


                Log.d("jsonLength", bars.toString() + "");
                // time to loop through all the images
                for (int i = 0; i < bars.length(); i++) {
                    Log.d("i aye ", i + "");
                    JSONObject c = bars.getJSONObject(i);
                    Log.d("ccc", bars.getJSONObject(i).toString());
                    //Storing each json item in varible

                    String name = c.getString(TAG_NAME);

                    String fbUrl = c.getString(TAG_FBPAGE);

                    String barPic = c.getString(TAG_PICURL);

                    String locale = c.getString(TAG_LOCATION);

                    String phoneNo = c.getString(TAG_PHONE);

                    String openingHours = c.getString(TAG_HOURS);

                    String rtn = c.getString(TAG_AVERAGE);

                    int id = c.getInt(TAG_PID);


                    b = new Bar(id, name, locale, phoneNo, openingHours, fbUrl, barPic, rtn);

                    int comma = b.getBarLocale().indexOf(",");
                    String longA = b.getBarLocale().substring(0, comma - 1);
                    String lat = b.getBarLocale().substring(comma + 1, b.getBarLocale().length() - 1);

                    Location loc2 = new Location("");
                    loc2.setLatitude(Double.parseDouble(longA));
                    loc2.setLongitude(Double.parseDouble(lat));
                    Log.d("Location", loc2 + "");
                    Integer distanceInMeters = Math.round(loc1.distanceTo(loc2) / 1000);
                    Log.d("Distanfe", distanceInMeters + "");


                    b.setDistanceAway(distanceInMeters);
                    mStrings.add(b);
                    Log.d("Array", b.toString());
                }

            } catch (Exception e) {
                myDialog.dismiss();

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

                            if (answer == false) {
                                Sorting sorting = new Sorting();
                                ArrayList<Bar> tmp = new ArrayList<Bar>();
                                tmp = sorting.bubbleSortNumbers(mStrings);
                                mStrings = tmp;
                                mAdapter = new AroundCustomListAdapter(getActivity(), mStrings, answer);
                                simpleList.setAdapter(mAdapter);

                            } else if (answer == true) {
                                Collections.sort(mStrings);
                                mAdapter = new AroundCustomListAdapter(getActivity(), mStrings, answer);
                                simpleList.setAdapter(mAdapter);

                            }


                        }
                    });
        }


    }


}
