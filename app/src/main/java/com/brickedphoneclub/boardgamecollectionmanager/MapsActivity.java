package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Dialog;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    private Geocoder gio;
    protected double lat = 0.0;
    protected double lng = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            lat = location.getLatitude();
            lng = location.getLongitude();

            if(location!=null){
                onLocationChanged(location);
            }
            //locationManager.requestLocationUpdates(provider, 20000, 0, this);
        }

        List<Address> suggestedAddresses = null;

        gio = new Geocoder(getBaseContext());
        try {
            suggestedAddresses = gio.getFromLocation(lat, lng, 10);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (suggestedAddresses != null) {
            /*
            for (Address A : suggestedAddresses) {
                Log.d("Address", A.toString());
            }*/
            Address A = suggestedAddresses.get(0);  //blindly grab the first address

            String line0 = A.getAddressLine(0);
            String line1 = A.getAddressLine(1);
            String line2 = A.getAddressLine(2);

            Log.d("GPS Address", "Best First Guess");
            Log.d("GPS Address", line0);
            Log.d("GPS Address", line1);
            Log.d("GPS Address", line2);

            ((EditText) findViewById(R.id.record_street_address_field)).setText(line0);
            ((EditText) findViewById(R.id.record_citystate_field)).setText(line1);
            ((EditText) findViewById(R.id.record_country_field)).setText(line2);

        }
        //setUpMapIfNeeded();
    }

    @Override
    public void onLocationChanged(Location location) {

        TextView tvLocation = (TextView) findViewById(R.id.tv_location);

        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Setting latitude and longitude in the TextView tv_location
        tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #googleMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (googleMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    private void setUpMap() {
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        setMapMarkerAt(new LatLng(0, 0));
        //LatLng ll;
    }

    private void setMapMarkerAt(LatLng location) {
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker"));
    }
}

