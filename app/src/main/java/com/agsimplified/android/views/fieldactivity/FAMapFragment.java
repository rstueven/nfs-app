package com.agsimplified.android.views.fieldactivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.agsimplified.android.models.fieldactivity.FieldActivity;
import com.agsimplified.android.views.AgSimplifiedActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rstueven on 35/13/18.
 * <p>FA Application Map</p>
 */

public class FAMapFragment extends Fragment
        implements OnMapReadyCallback, AgSimplifiedActivity.LocationListener {
    private GoogleMap mMap;

    public static FAMapFragment newInstance(FieldActivity fieldActivity) {
        Log.d("nfs", "FAMapFragment.newInstance()");
        if (fieldActivity == null) {
            throw new IllegalArgumentException("null fieldActivity");
        }

        FAMapFragment frag = new FAMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("fieldActivity", fieldActivity);
        frag.setArguments(args);
        return frag;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("nfs", "FAMapFragment.onMapReady()");

        mMap = map;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(41.736533, -95.701810)));

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);

        AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();
        if (activity == null) {
            throw new IllegalStateException("null activity");
        }

        activity.registerLocationListener(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        Log.d("nfs", "FAMapFragment.onLocationUpdated()");
        Log.d("nfs", "FA MAP LOCATION");
        if (location != null) {
            Log.d("nfs", location.toString());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }
}
