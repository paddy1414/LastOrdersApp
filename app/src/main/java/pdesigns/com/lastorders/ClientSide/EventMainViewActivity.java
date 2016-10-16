package pdesigns.com.lastorders.ClientSide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pdesigns.com.lastorders.ImageHandlers.ImageFetcher;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type Bar main view activity.
 */
public class EventMainViewActivity extends AppCompatActivity {

    /**
     * The Session.
     */
// Session Manager Class
    SessionManager session;
    //ImageFetcherClass
    private ImageFetcher mImageFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_pageer_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Event Information"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mImageFetcher = new ImageFetcher(this, 25);

        session = new SessionManager(getApplicationContext());
        //Check the login State
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final EventPagerView adapter = new EventPagerView
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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


    private void addMapFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
    }
}

