package com.example.cynthia.mapsdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

/**
 * Created by Cynthia on 4/15/2017.
 */
public class MapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        com.google.android.gms.location.LocationListener{
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Location targetLocation = new Location("");
    private LocationRequest mLocationRequest;

    private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
    private int curMapTypeIndex = 0;

    Marker m;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
                setHasOptionsMenu(true);
                mGoogleApiClient = new GoogleApiClient.Builder( getActivity())
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners();
    }

    private void initListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener( this );
        getMap().setOnMapClickListener(this);
    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target( new LatLng( location.getLatitude(),
                        location.getLongitude() ) )
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera( CameraUpdateFactory
                .newCameraPosition( position ), null );

        getMap().setMapType(MAP_TYPES[curMapTypeIndex]);
        getMap().setTrafficEnabled(true);
        getMap().setMyLocationEnabled(true);
        getMap().getUiSettings().setZoomControlsEnabled(true);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this.getActivity(), // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        MarkerOptions tMarker = new MarkerOptions()//this is the marker object, don't lose it
                .position(new LatLng(0, 0))
                .title("Target");
        m = getMap().addMarker(tMarker);

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient );

        initCamera(mCurrentLocation);
    }
    @Override
    public void onLocationChanged(Location location) {
        //Current location of Player
        mCurrentLocation = location;

        try{
            ((onPlayerLocationChangedListener) getActivity()).sendPlayerLocation(location.getLatitude(),location.getLongitude());
        }catch(ClassCastException cce){

        }

        //Set location of target
        updateTargetLoc(0.0d, 0.0d);


        //Update location of player
        updatePlayerLoc(location.getLatitude(), location.getLongitude());

        //Check distance to see if a kill is possible
        updateIsStrikePossible();

    }

    public interface onPlayerLocationChangedListener{
        public void sendPlayerLocation(double lat, double lon);
    }

    public void updateTargetLoc(double Latitude, double Longitude)
    {
        targetLocation.setLatitude(Latitude);
        targetLocation.setLongitude(Longitude);
    }

    public void updatePlayerLoc(double Latitude, double Longitude)
    {
        LatLng latLng = new LatLng(Latitude,Longitude);
        m.setPosition(latLng);
    }

    public boolean updateIsStrikePossible()
    {
        double distance = mCurrentLocation.distanceTo(targetLocation);
        if (distance <= 100){
            Button strikeButt = (Button) getActivity().findViewById(R.id.killButton);
            strikeButt.setVisibility(View.VISIBLE);
            return true;
        }
        else{
            Button strikeButt = (Button) getActivity().findViewById(R.id.killButton);
            strikeButt.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }
    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {


                }
                return;
            }
        }
    }
}