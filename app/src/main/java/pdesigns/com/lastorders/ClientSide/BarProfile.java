package pdesigns.com.lastorders.ClientSide;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.ImageHandlers.ImageCache;
import pdesigns.com.lastorders.ImageHandlers.ImageFetcher;
import pdesigns.com.lastorders.ImageHandlers.loadTheBloodyImage;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.AppController;
import pdesigns.com.lastorders.provider.JSONParser;
import pdesigns.com.lastorders.provider.Server_Connections;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type Bar profile.
 */
public class BarProfile extends Fragment implements View.OnClickListener {

    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_BARS = "bars";
    private static final String TAG_PID = "barId";
    private static final String TAG_NAME = "barName";
    private static final String TAG_PICURL = "pictureUrl";
    private static final String TAG_WHEELCHAR = "wheelchair";
    private static final String TAG_LOCATION = "barLocale";
    private static final String TAG_FBPAGE = "barFBPage";
    private static final String IMAGE_CACHE_DIR = "thumbs";


    String rateString;
    int barId;
    String uId;

    /**
     * The Json parser.
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * The Bars.
     */
// images JSON Array
    JSONArray bars = null;

    /**
     * The Bar o.
     */
//   Bar bar;
    Bar barO;
    /**
     * The B.
     */

    //Ui stuff
    RatingBar ratingsBar;
    ImageView imageView;
    TextView txtName;
    TextView txtAddr;
    TextView txtFb;
    TextView txtPhone;
    TextView txtOpen;
    Button bntRate;

    Bar b;
    SessionManager session;
    private ProgressDialog myDialog;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageFetcher mImageFetcher;
    private loadTheBloodyImage loadTheBloodyImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Session Manager
        session = new SessionManager(this.getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();

        barO = (Bar) getActivity().getIntent().getExtras().get("barzzz");

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);


        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.no);

        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);


        ImageView image = new ImageView(getActivity());

        // To retrieve object in second Activity

        View rowView = inflater.inflate(R.layout.fragment_bar_profile, null, true);

        imageView = (ImageView) rowView.findViewById(R.id.barImage);
        txtName = (TextView) rowView.findViewById(R.id.barName);
        txtAddr = (TextView) rowView.findViewById(R.id.barAddr);
        txtFb = (TextView) rowView.findViewById(R.id.barAddr);
        txtPhone = (TextView) rowView.findViewById(R.id.barPhoneNo);
        txtOpen = (TextView) rowView.findViewById(R.id.barOpenHours);
        ratingsBar = (RatingBar) rowView.findViewById(R.id.ratingBar);
        bntRate = (Button) rowView.findViewById(R.id.rateBtn);

        txtName.setText(barO.getBarName());
        txtAddr.setText(barO.getBarLocale());
        txtFb.setText(barO.getBarFb());
        txtPhone.setText(barO.getPhoneNo());
        txtOpen.setText(barO.getOpeningHours());


        float rtn = Float.parseFloat(barO.getAverageRating());
        ratingsBar.setRating(rtn);


        HashMap<String, String> user = session.getUserDetails();
        barId = barO.getId();

        uId = user.get(SessionManager.KEY_ID);
        loadTheBloodyImage = new loadTheBloodyImage(getActivity());
        loadTheBloodyImage.DisplayImage(barO.getBarPic(), imageView);

        bntRate.setOnClickListener(this);

        return rowView;

    }


    @Override
    public void onClick(View view) {

        float rate = ratingsBar.getRating();
        rateString = String.valueOf(rate);

        Log.d("ratingsBar", String.valueOf(rateString));
        new postComent().execute();

    }

    private void rateResult() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        dialogBuilder.setMessage("Rating Successfully Posted");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    // A background async task to load all bars by making http request
    private class postComent extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();

            Log.d("OnPreExecute", "got to here");
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_post_rating.";

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Server_Connections.URL_POSTRATING, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d("errrrrrr", response);
                    try {
                        JSONObject jObj = new JSONObject(response);
                        Log.d("Jsonobj", jObj.toString());
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (error == false) {

                            // user successfully logged in
                            // Create login session

                            JSONObject comment = jObj.getJSONObject("rating");
                            Log.d("rating", comment.toString());

                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Log.d("More json probs", e.getMessage());
                        Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                        // If you need update UI, simply do this:
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                // update your UI component here. in order to display friendly error report
                                Toast.makeText(getContext(), R.string.server_is_down,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Json", error.getMessage());
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("barId", barId + "");
                    params.put("userId", uId);
                    params.put("ratingNo", rateString);

                    Log.d("Streng sent", params.toString());
                    return params;
                }

            };
            Log.d("Sting reqsss", strReq.toString());

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

            // dismiss the dialog after getting all products
            // updating UI from Background Thread
            // updating UI from Background Thread

            // updating UI from Background Thread
            getActivity().runOnUiThread(
                    new Runnable() {
                        public void run() {
                            /**
                             * Updating parsed JSON data into gridview
                             * */
                            // loginResult(useMe);
                            rateResult();
                            //      Log.d("ArrayStuff", dbU.getUsersList().toString());
                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

                        }
                    });
        }

    }


}
