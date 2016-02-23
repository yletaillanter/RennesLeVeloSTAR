package com.yoannlt.velostar.renneslevlostar;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yoannlt.velostar.renneslevlostar.api.StarVelo;
import com.yoannlt.velostar.renneslevlostar.model.Record;
import com.yoannlt.velostar.renneslevlostar.model.StationResponse;

import retrofit.RestAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static String LOG_TAG = MapsActivity.class.getSimpleName();
    private final static String LOG_TAG_PRE = "DEBUG RETROFIT = ";
    private GoogleMap mMap;
    private StarVelo api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        api = new RestAdapter.Builder()
                .setEndpoint("https://data.explore.star.fr/api/records/1.0/search")
                .build()
                .create(StarVelo.class);

        //RxJava
        api.getAllStations()
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribeOn(Schedulers.newThread())*/
                .subscribe(new Observer<StationResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(LOG_TAG_PRE + LOG_TAG, "COMPLETE");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LOG_TAG_PRE + LOG_TAG + "error", e.toString());
                    }

                    @Override
                    public void onNext(StationResponse args) {
                        Log.d(LOG_TAG_PRE + LOG_TAG, "Nombre de stations retournées : " + args.getRecords().length);
                        loadStationsOnMap(args);
                    }
                });

        // ASYNC CALL
        /*api.getAllStations(new Callback<StationResponse>() {
            @Override
            public void success(StationResponse stationResponse, Response response) {
                Log.d(LOG_TAG_PRE + LOG_TAG + response, stationResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG_PRE + LOG_TAG + "error", error.toString());
            }
        });*/
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

        // Add a marker in Sydney and move the camera
        LatLng rennes = new LatLng(48.116582, -1.678843);
        mMap.addMarker(new MarkerOptions().position(rennes).title("Rennes"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rennes));
    }

    private void loadStationsOnMap(StationResponse stationResponse){

        LatLng latLng;
        for (Record record : stationResponse.getRecords()) {
            latLng = new LatLng(record.getFields().getCoordonnees()[0], record.getFields().getCoordonnees()[1]);
            mMap.addMarker(new MarkerOptions().position(latLng).title(record.getFields().getNom()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
