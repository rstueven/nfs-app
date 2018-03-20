package com.agsimplified.android.views;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.models.FieldActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rstueven on 3/13/18.
 * <p>Field Activity Map and Directions</p>
 */

public class FieldActivityMapFragment extends Fragment
        implements OnMapReadyCallback, AgSimplifiedActivity.LocationListener {
    private GoogleMap mMap;

    public static FieldActivityMapFragment newInstance(FieldActivity fa) {
        Log.d("nfs", "FieldActivityMapFragment.newInstance()");
        if (fa == null) {
            throw new IllegalArgumentException("null fa");
        }

        FieldActivityMapFragment frag = new FieldActivityMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("fa", fa);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "FieldActivityMapFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_field_activity_map, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            FieldActivity fa = (FieldActivity) args.getSerializable("fa");
            if (fa == null) {
                throw new IllegalStateException("null fa");
            }

            FragmentManager fm = getChildFragmentManager();

            FieldActivityFragment fieldActivityFragment = FieldActivityFragment.newInstance(fa);

            DirectionsFragment directionsFragment = DirectionsFragment.newInstance("DIRECTIONS");

            SupportMapFragment mapFragment = new SupportMapFragment();

            fm.beginTransaction()
                    .add(R.id.fieldActivityFrame, fieldActivityFragment, "fieldActivity")
                    .add(R.id.directionsFrame, directionsFragment, "directions")
                    .add(R.id.mapFrame, mapFragment, "map")
                    .commit();

            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("nfs", "FieldActivityMapFragment.onMapReady()");

        mMap = map;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(41.736533, -95.701810)));

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setTiltGesturesEnabled(false);

        AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();
        if (activity == null) {
            throw new IllegalStateException("null activity");
        }

        activity.registerLocationListener(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        Log.d("nfs", "FieldActivityMapFragment.onLocationUpdated()");
        Log.d("nfs", "DS MAP FRAG LOCATION");
        if (location != null) {
            Log.d("nfs", location.toString());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }
}