package com.yoannlt.velostar.renneslevlostar;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yoannlt.velostar.renneslevlostar.api.StarVelo;
import com.yoannlt.velostar.renneslevlostar.model.Enregistrement;
import com.yoannlt.velostar.renneslevlostar.model.JSONStationResponse;

import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

import static com.google.android.gms.location.LocationServices.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, UpdateLocationCallback {

    private final static String LOG_TAG = MapsActivity.class.getSimpleName();
    private final static String ENDPOINT = "https://data.explore.star.fr/api/records/1.0/search";
    private final static String ETAT = "En fonctionnement";
    private final static int MY_LOCATION_REQUEST_CODE = 1;
    private final static long INTERVAL = 0;

    private GoogleApiClient client;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location mLastLocation;
    private StarVelo api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get the locationmanager from the systemService
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        MyLocListener myLocListener = new MyLocListener(this);

        // Check if user authorized location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Update the location
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVAL, 0, myLocListener);
        } else {
            // Get the location permission from user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_LOCATION_REQUEST_CODE);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).addApi(API).build();

        api = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(StarVelo.class);
        //RxJava
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
                        loadStationsOnMap(args);
                        // Move the camera to the user's current location
                        LatLng currentPos = new LatLng(mLastLocation.getAltitude(), mLastLocation.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 10));
                    }
                });
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
        mMap = googleMap;


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

    }

    private void loadStationsOnMap(JSONStationResponse stationResponse) {
        LatLng latLng;
        for (Enregistrement record : stationResponse.getRecords()) {
            double distance = 0;
            latLng = new LatLng(record.getFields().getCoordonnees()[0], record.getFields().getCoordonnees()[1]);
            if (mLastLocation != null ) {
                distance = distFrom(record.getFields().getCoordonnees()[0], record.getFields().getCoordonnees()[1], mLastLocation.getLatitude(), mLastLocation.getLongitude());
            } else {
                Log.d(LOG_TAG, "Location is null");
            }

            if (record.getFields().getEtat().equals(ETAT)) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("à " + distance + "m " + record.getFields().getNom() + " vélo(s) : " + record.getFields().getNombrevelosdisponibles() + " emplacement(s) : " + record.getFields().getNombreemplacementsdisponibles()));
            } else {
                mMap.addMarker(new MarkerOptions().position(latLng).title(record.getFields().getNom() + " En panne."));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.yoannlt.velostar.renneslevlostar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.yoannlt.velostar.renneslevlostar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void updateLocation(Location location) {
        mLastLocation = location;
        Log.e("location : ", location.getLatitude() + " / " + location.getLongitude());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getApplicationContext(), "Impossible d'obtenir la localisation", Toast.LENGTH_SHORT).show();
            }
        }
    }

}