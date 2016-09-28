package pdesigns.com.lastorders.ClientSide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pdesigns.com.lastorders.ImageHandlers.ImageFetcher;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type First view activity.
 */
public class FirstViewActivity extends AppCompatActivity {

    /**
     * The Session.
     */
    SessionManager session;

    private ImageFetcher mImageFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Session class instance
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        mImageFetcher = new ImageFetcher(this, 25);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.clear_cache:
                mImageFetcher.clearCache();
                Toast.makeText(this, R.string.clear_cache_menu,
                        Toast.LENGTH_SHORT).show();
                return true;


            case R.id.weview1ToMe:
                Intent i = new Intent(this, BarWebView.class);
                startActivity(i);
                return true;
            case R.id.logout:
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();

            case R.id.refreshData:
                intent = new Intent();
                intent.setAction("pdesigns.com.lastorders.ClientSide");
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                this.sendBroadcast(intent);
                this.onResume();


        }
        return super.onOptionsItemSelected(item);
    }
}
