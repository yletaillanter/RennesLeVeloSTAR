package com.yoannlt.velostar.renneslevlostar;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yoannlt.velostar.renneslevlostar.api.StarVelo;
import com.yoannlt.velostar.renneslevlostar.model.Enregistrement;
import com.yoannlt.velostar.renneslevlostar.model.JSONStationResponse;

import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

import static com.google.android.gms.location.LocationServices.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static String LOG_TAG = MapsActivity.class.getSimpleName();
    private final static String ENDPOINT = "https://data.explore.star.fr/api/records/1.0/search";
    private final static String ETAT = "En fonctionnement";
    private final static int MY_LOCATION_REQUEST_CODE = 1;
    private final static long INTERVAL = 0;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private GoogleMap mMap;
    private Location mLastLocation;
    private StarVelo api;
    private JSONStationResponse jsonStationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //init the google API client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Create the map
        mMap = googleMap;

        //Load the data
        loadData();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Activate user's position
            mMap.setMyLocationEnabled(true);

            //Test if lastLocation is known
            if(mLastLocation != null) {
                // Moce the camera to the user's position
                LatLng currentPos = new LatLng(mLastLocation.getAltitude(), mLastLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 13));
            }
        } else {
            // Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
    }

    private void loadData() {

        // Prepare the endpoint
        api = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(StarVelo.class);

        //RxJava part
        api.getAllStations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONStationResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG_TAG, "COMPLETE");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG, e.toString());
                    }

                    @Override
                    public void onNext(JSONStationResponse args) {
                        Log.d(LOG_TAG, args.toString());
                        jsonStationResponse = args;
                        loadStationsOnMap();
                    }
                });
    }

    private void loadStationsOnMap() {
        LatLng latLng;

        if (jsonStationResponse != null) {
            //mMap.clear();

            // For each stations
            for (Enregistrement record : jsonStationResponse.getRecords()) {
                double distance = 0;
                latLng = new LatLng(record.getFields().getCoordonnees()[0], record.getFields().getCoordonnees()[1]);
                if (mLastLocation != null ) {
                    distance = distFrom(record.getFields().getCoordonnees()[0], record.getFields().getCoordonnees()[1], mLastLocation.getLatitude(), mLastLocation.getLongitude());
                } else {
                    Log.d(LOG_TAG, "Location is null");
                }

                if (record.getFields().getEtat().equals(ETAT)) {
                    MarkerOptions marker = new MarkerOptions().position(latLng).title("à " + distance + "m " + record.getFields().getNom() + " vélo(s) : " + record.getFields().getNombrevelosdisponibles() + " emplacement(s) : " + record.getFields().getNombreemplacementsdisponibles());
                    mMap.addMarker(marker);
                } else {
                    MarkerOptions marker = new MarkerOptions().position(latLng).title("à " + distance + "m " + record.getFields().getNom() + " vélo(s) : " + record.getFields().getNombrevelosdisponibles() + " emplacement(s) : " + record.getFields().getNombreemplacementsdisponibles());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(record.getFields().getNom() + " En panne."));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //Init the location request
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(1000);

        // Check if user authorized location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        loadStationsOnMap();
        Log.e("location : ", location.getLatitude() + " / " + location.getLongitude());
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, ""+connectionResult.describeContents());
    }

    /**
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = Math.floor(earthRadius * c);

        return dist;
    }
}