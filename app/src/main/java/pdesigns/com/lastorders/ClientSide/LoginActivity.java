package pdesigns.com.lastorders.ClientSide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pdesigns.com.lastorders.DTO.User;
import pdesigns.com.lastorders.DatabaseArrays.DatabaseUsers;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.AppController;
import pdesigns.com.lastorders.provider.JSONParser;
import pdesigns.com.lastorders.provider.Server_Connections;
import pdesigns.com.lastorders.provider.SessionManager;


/**
 * The type Login activity.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERS = "user";
    private static final String TAG_UID = "id";
    private static final String TAG_FNAME = "fName";
    private static final String TAG_LNAME = "lName";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSOWORD = "uPassword";
    /**
     * The Count me.
     */
    static int countMe = 0;
    /**
     * The B login.
     */
    Button bLogin,
    /**
     * The B register.
     */
    bRegister;
    /**
     * The Register link.
     */
    TextView registerLink;
    /**
     * The Email box.
     */
    EditText emailBox,
    /**
     * The Password box.
     */
    passwordBox,
    /**
     * The Fname box.
     */
    fnameBox,
    /**
     * The L name box.
     */
    lNameBox;
    /**
     * The Email string.
     */
    String emailString = "";
    /**
     * The Password string.
     */
    String passwordString = "";
    /**
     * The F name string.
     */
    String fNameString = "";
    /**
     * The L name string.
     */
    String lNameString = "";
    /**
     * The Debug i.
     */
    int debugI = 0;
    /**
     * The Full users list.
     */
    ArrayList<HashMap<String, User>> fullUsersList;
    /**
     * The Db u.
     */
    DatabaseUsers dbU = new DatabaseUsers();
    /**
     * The Json parser.
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * The Use me.
     */
    User useMe;
    /**
     * The Session.
     */
// Session Manager Class
    SessionManager session;
    /**
     * The Users.
     */
// images JSON Array
    JSONArray users = null;
    private ProgressDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(R.id.email_sign_in_button);
        bRegister = (Button) findViewById(R.id.register_button);
        emailBox = (EditText) findViewById(R.id.emailBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        fnameBox = (EditText) findViewById(R.id.regfname);
        lNameBox = (EditText) findViewById(R.id.reglname);
        // registerLink = (TextView) findViewById(R.id.tvRegisterLink);
        fnameBox.setVisibility(View.GONE);
        lNameBox.setVisibility(View.GONE);

        Log.d("countMe", countMe + "");
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        // Session Manager
        session = new SessionManager(getApplicationContext());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                emailString = emailBox.getText().toString();
                passwordString = passwordBox.getText().toString();

                //      User user = new User(emailString, emailString);

                authenticate(emailString, passwordString);
                break;
            case R.id.register_button:
                if (!fnameBox.isShown()) {
                    fnameBox.setVisibility(View.VISIBLE);
                    lNameBox.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Reg not shown", Toast.LENGTH_LONG).show();
                } else {
                    emailString = emailBox.getText().toString();
                    passwordString = passwordBox.getText().toString();
                    fNameString = fnameBox.getText().toString();
                    lNameString = lNameBox.getText().toString();
                    if (emailString.equals("") || passwordString.equals("") || fNameString.equals("") || lNameString.equals("")) {
                        showErrorMessage();
                    } else {
                        authenticateRegister(emailString, emailString, fNameString, lNameString);

                    }

                }
                //      User user = new User(emailString, emailString);
                break;
        }
    }


    private void authenticateRegister(String email, String password, String fname, String lname) {
        Log.d("authenticate", "and it go to authenticate $$$$$$$$$$$$$$$$");
        Log.d("Email", email + "%%%%%%%%%%%%%%%%%");
        new RegisterUser().execute();

    }

    private void authenticate(String email, String password) {
        Log.d("authenticate", "and it go to authenticate $$$$$$$$$$$$$$$$");
        Log.d("Email", email + "%%%%%%%%%%%%%%%%%");
        new LoginUser().execute();

    }

    private void loginResult(User user) {
        if (user != null) {
            //if (!user.getfName().equals("") & !user.getEmail().equals("")) {
            Log.d("User", user.toString() + " /nUser from the success if");

            Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
            session.createLoginSession(user.getUid(), user.getfName(), user.getlName(), user.getEmail(), user.getuPassword());
            Intent i = new Intent(this, FirstViewActivity.class);
            startActivity(i);
            finish();
        } else {
            //     Log.d("User", user.toString() + " /nUser from the error else");
            showErrorMessage();
        }
    }


    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }


    // A background async task to load all bars by making http request
    private class LoginUser extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog

        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            Log.d("Debug i", (debugI++) + "");
            super.onPreExecute();
            myDialog = new ProgressDialog(LoginActivity.this);
            myDialog.setMessage("Logging you in.  Please wait....");
            myDialog.setIndeterminate(false);
            myDialog.setCancelable(false);
            myDialog.show();
            Log.d("OnPreExecute", "got to here");
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            Log.d("doInBackground", "got to here");

            // Tag used to cancel the request
            String tag_string_req = "req_login";


            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Server_Connections.URL_LOGIN, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Login Response:" + response.toString());
                    myDialog.dismiss();


                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (error == false) {

                            // user successfully logged in
                            // Create login session

                            JSONObject user = jObj.getJSONObject("user");
                            Log.d("Jsonobj", jObj.toString());
                            String uid = jObj.getString("uid");

                            String fname = user.getString("fName");
                            String lName = user.getString("lName");
                            String email = user.getString("email");


                            // Inserting row in users table

                            useMe = new User(uid, fname, lName, email);
                            //        useMe = new User(uid, fname, lName, email);


                            loginResult(useMe);

                            Log.d("UserMeLogin", useMe.toString());

                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        myDialog.dismiss();
                        e.printStackTrace();
                        // If you need update UI, simply do this:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // update your UI component here. in order to display friendly error report
                                Toast.makeText(getApplicationContext(), R.string.server_is_down,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    myDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", emailString);
                    params.put("password", passwordString);

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
            myDialog.dismiss();
            // updating UI from Background Thread
            LoginActivity.this.runOnUiThread(
                    new Runnable() {
                        public void run() {

                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
                        }
                    });
        }


    }

    // A background async task to load all bars by making http request
    private class RegisterUser extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            myDialog = new ProgressDialog(LoginActivity.this);
            myDialog.setMessage("Registering.  Please wait....");
            myDialog.setIndeterminate(false);
            myDialog.setCancelable(false);
            myDialog.show();
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {

            // Tag used to cancel the request
            String tag_string_req = "req_register";

            myDialog.setMessage("Registering ...");
            myDialog.show();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    Server_Connections.URL_REGISTER, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    myDialog.dismiss();
                    Log.d("jobasfd", response.toString());
                    try {
                        JSONObject jObj = new JSONObject(response);
                        Log.d("jobasfd", jObj.toString());
                        boolean error = jObj.getBoolean("error");
                        if (!error) {
                            // User successfully stored in MySQL
                            // Now store the user in an object
                            String uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("user");
                            String fname = user.getString("fName");
                            String lName = user.getString("lName");
                            String email = user.getString("email");
                            // Inserting row in users table

                            useMe = new User(uid, fname, lName, email);
                            Log.d("UserMeReg", useMe.toString());
                            Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                            loginResult(useMe);
                            finish();
                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    myDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("fname", fNameString);
                    params.put("lname", lNameString);
                    params.put("email", emailString);
                    params.put("password", passwordString);

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

            return null;
        }

        protected void onPostExecute(String file_url) {

            // dismiss the dialog after getting all products
            myDialog.dismiss();
            // updating UI from Background Thread
            LoginActivity.this.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            /**
                             * Updating parsed JSON data into gridview
                             * */


                            finish();
                            Log.d("ArrayStuff", dbU.getUsersList().toString());
                            Log.d("Users", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

                        }
                    });
        }


    }

}
