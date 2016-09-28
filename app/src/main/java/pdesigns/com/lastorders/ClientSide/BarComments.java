package pdesigns.com.lastorders.ClientSide;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pdesigns.com.lastorders.Adapters.CommentCustomListAdapter;
import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.DTO.Comments;
import pdesigns.com.lastorders.DatabaseArrays.DatabaseCommentText;
import pdesigns.com.lastorders.DatabaseArrays.DatabaseComments;
import pdesigns.com.lastorders.DatabaseArrays.DatabaseName;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.AppController;
import pdesigns.com.lastorders.provider.JSONParser;
import pdesigns.com.lastorders.provider.Server_Connections;
import pdesigns.com.lastorders.provider.SessionManager;


/**
 * The type Bar comments.
 */
public class BarComments extends Fragment implements Serializable, OnClickListener {


    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COMMENTS = "comments";
    private static final String TAG_FNAME = "fName";
    private static final String TAG_LNAME = "lName";
    private static final String TAG_COMMENTTEST = "commentText";
    /**
     * The Json parser.
     */
// create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Name list.
     */
    ArrayList<HashMap<String, String>> nameList;
    /**
     * The Comment list.
     */
    ArrayList<HashMap<String, String>> commentList;
    /**
     * The Dbc.
     */
    DatabaseComments dbc = new DatabaseComments();
    /**
     * The Dbn.
     */
    DatabaseName dbn = new DatabaseName();
    DatabaseCommentText dbct = new DatabaseCommentText();
    /**
     * The Comments obj.
     */
    Comments commentsObj = new Comments();
    /**
     * The Baro.
     */
    Bar baro;
    /**
     * The Bar id.
     */
    int barId;
    /**
     * The U id.
     */
    String uId;
    /**
     * The Comment string.
     */
    String commentString;
    /**
     * The Sc.
     */
    Server_Connections sc = new Server_Connections();
    /**
     * The Btn comment.
     */
    Button btnComment;
    /**
     * The Txt comment.
     */
    TextView txtComment;
    /**
     * The Comments.
     */
// comments JSON Array
    JSONArray comments = null;
    /**
     * The Session.
     */
// Session Manager Class
    SessionManager session;
    /**
     * The Dbct.
     */

    private ArrayList<Comments> mStrings = new ArrayList<Comments>();
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private String url_all_comments;
    private CommentCustomListAdapter mAdapter;
    private ArrayList<String> mSComments;
    private ArrayList<String> mNaames;
    private ListView simpleList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Session Manager
        session = new SessionManager(this.getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comments_list, container, false);

        baro = (Bar) getActivity().getIntent().getExtras().get("barzzz");
        barId = baro.getId();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        //User Id Session
        uId = user.get(SessionManager.KEY_ID);
        url_all_comments = Server_Connections.SERVER + "get_all_comments.php?barId=" + barId;

        txtComment = (TextView) v.findViewById(R.id.commentbox);

        btnComment = (Button) v.findViewById(R.id.buttoncommentpost);
        //hashmap for GridView
        commentList = new ArrayList<HashMap<String, String>>();
        nameList = new ArrayList<HashMap<String, String>>();
        btnComment.setOnClickListener(this);

        simpleList = (ListView) v.findViewById(R.id.listallcomments);

        new LoadAllComments().execute();


        btnComment.setOnClickListener(this);

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
    public void onClick(View view) {

        if (txtComment.getText().toString().equals("")) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
            dialogBuilder.setMessage("Your Comment Be Empty");
            dialogBuilder.setPositiveButton("Ok", null);
            dialogBuilder.show();
        } else {
            commentString = txtComment.getText().toString();
            Log.d("commentString", commentString);
            new postComent().execute();
        }
    }

    private void commentResult() {
        new LoadAllComments().execute();
        txtComment.setText("");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
        dialogBuilder.setMessage("Comment Successfully Posted");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();


    }


    // A background async task to load all bars by making http request
    private class LoadAllComments extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            mStrings = new ArrayList<Comments>();

        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "on the doInBackground part!!");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            try {
                //getting json string from url
                JSONObject jsonObject = jsonParser.makeHttpRequest(url_all_comments, "GET", params);
                Log.d("BarCommentsbarid", url_all_comments + "");
                // log information for the jspon responce
                Log.d("BarComments All bars", jsonObject.toString());
                // check for success tag
                System.out.println("CONNNECTION --------------------------------------------------");
                int success = jsonObject.getInt(TAG_SUCCESS);
                System.out.println(success + "");
                // the images was found
                // Getting the array of the images
                comments = jsonObject.getJSONArray(TAG_COMMENTS);
                // time to loop through all the images
                for (int i = 0; i < comments.length(); i++) {
                    JSONObject c = comments.getJSONObject(i);

                    //Storing each json item in varible
                    String fname = c.getString(TAG_FNAME);
                    String lname = c.getString(TAG_LNAME);
                    String commentsStuff = c.getString(TAG_COMMENTTEST);
                    commentsObj = new Comments(fname, lname, commentsStuff);
                    mStrings.add(i, commentsObj);
                }

            } catch (Exception e) {


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

            // updating UI from Background Thread
            // updating UI from Background Thread
            getActivity().runOnUiThread(
                    new Runnable() {
                        public void run() {
                            mAdapter = new CommentCustomListAdapter(getActivity(), mStrings);
                            simpleList.setAdapter(mAdapter);
                        }
                    });
        }
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
            String tag_string_req = "req_post_comment";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Server_Connections.URL_POSTCOMMENT, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jObj = new JSONObject(response);
                        Log.d("Jsonobj", jObj.toString());
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (error == false) {

                            // user successfully logged in
                            // Create login session

                            JSONObject comment = jObj.getJSONObject("comment");
                            Log.d("comment", comment.toString());


                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
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
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("barId", barId + "");
                    params.put("userId", uId);
                    params.put("commentText", commentString);

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
                            commentResult();

                        }
                    });
        }


    }


}
