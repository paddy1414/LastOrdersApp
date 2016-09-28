package pdesigns.com.lastorders.ClientSide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * The type Web view activity.
 */
public class WebViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProfileFragement uf = new ProfileFragement();

        Intent i = this.getIntent();


        //  getFragmentManager().beginTransaction().add(android.R.id.content, uf).commit();

    }


}
