package com.yoannlt.velostar.renneslevlostar;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by yoannlt on 28/02/2016.
 */
public class MyLocListener implements LocationListener {

    private final static String LOG_TAG = MapsActivity.class.getSimpleName();

    private UpdateLocationCallback updateLocationCallback;

    public MyLocListener(UpdateLocationCallback updateLocationCallback) {
        this.updateLocationCallback = updateLocationCallback;
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocationCallback.updateLocation(location);
        Log.d(LOG_TAG, "Location Updated : " + location.getAltitude() + " " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
