package pdesigns.com.lastorders.ClientSide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pdesigns.com.lastorders.DTO.Bar;
import pdesigns.com.lastorders.R;
import pdesigns.com.lastorders.provider.GPSTracker;

/**
 * The type Map fragment.
 */
public class MapFragment extends Fragment implements View.OnClickListener {

    /**
     * The M map view.
     */
    MapView mMapView;
    /**
     * The Bar o.
     */
    Bar barO;
    /**
     * The Gps.
     */
// GPSTracker class
    GPSTracker gps;
    private GoogleMap googleMap;
    private Button btnChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        btnChange = (Button) v.findViewById(R.id.mapchange);
        gps = new GPSTracker(this.getActivity());

        mMapView.onResume();// needed to get the map to display immediately
        barO = (Bar) getActivity().getIntent().getExtras().get("barzzz");

        // latitude and longitude
        String mapsLoc = barO.getBarLocale();
        int comma = mapsLoc.indexOf(",");
        String longA = mapsLoc.substring(0, comma - 1);
        String lat = mapsLoc.substring(comma + 1, mapsLoc.length() - 1);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();


            MarkerOptions yourlocation = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Current Location");
            yourlocation.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            googleMap.addMarker(yourlocation);
        }
        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(Double.parseDouble(longA), Double.parseDouble(lat))).title(barO.getBarName());
        marker.snippet(barO.getPhoneNo());

        double log = 2.2522222;

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(longA), Double.parseDouble(lat))).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        btnChange.setOnClickListener(this);
        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mapchange:
                if (googleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    btnChange.setText("Change to Normal View");
                    Toast.makeText(getActivity(), "Change to Satellite", Toast.LENGTH_SHORT).show();
                } else {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    btnChange.setText("Change to Satellite View");
                }
                break;

        }
    }
}
