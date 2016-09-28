package pdesigns.com.lastorders.ClientSide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.HashMap;

import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.DTO.Event;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.JSONParser;
import pdesigns.com.lastorders.provider.SessionManager;

/**
 * The type Bar profile.
 */
public class EventProfileActivity extends Fragment {


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
    Event eventO;
    /**
     * The B.
     */

    //Ui stuff

    TextView txtTitle;
    TextView txtStart;
    TextView txtcity;
    TextView txtRegion;
    TextView txtAddr;
    TextView txtCOuntry;
    TextView txtCountryAppr;
    TextView txtDesc;

    Bar b;
    SessionManager session;

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

        eventO = (Event) getActivity().getIntent().getExtras().get("eventszzz");


        // To retrieve object in second Activity

        View rowView = inflater.inflate(R.layout.fragment_bar_profile, null, true);

        txtTitle = (TextView) rowView.findViewById(R.id.event_ptitle);
        txtStart = (TextView) rowView.findViewById(R.id.event_start_time);
        txtAddr = (TextView) rowView.findViewById(R.id.event_venue_address);
        txtcity = (TextView) rowView.findViewById(R.id.event_city_name);
        txtRegion = (TextView) rowView.findViewById(R.id.event_region_name);
        txtCOuntry = (TextView) rowView.findViewById(R.id.event_country_name);
        txtCountryAppr = (TextView) rowView.findViewById(R.id.event_country_abbr);
        txtDesc = (TextView) rowView.findViewById(R.id.event_description);

        txtTitle.setText(eventO.getTitle());
        txtStart.setText(eventO.getStartTime());
        txtcity.setText(eventO.getCityName());
        txtRegion.setText(eventO.getRegionName());
        txtCOuntry.setText(eventO.getCountryName());
        txtCountryAppr.setText(eventO.getCountryAbbr());
        txtDesc.setText(eventO.getDescription());


        HashMap<String, String> user = session.getUserDetails();
        //  barId = barO.getId();

        uId = user.get(SessionManager.KEY_ID);


        return rowView;

    }


}
