package com.agsimplified.android.views.distributionsale;

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
import com.agsimplified.android.models.distributionsale.DistributionSale;
import com.agsimplified.android.views.AgSimplifiedActivity;
import com.agsimplified.android.views.DirectionsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rstueven on 3/13/18.
 * <p>DS Map and Directions</p>
 */

public class DSMapFragment extends Fragment
        implements OnMapReadyCallback, AgSimplifiedActivity.LocationListener {
    private GoogleMap mMap;

    public static DSMapFragment newInstance(DistributionSale ds) {
        Log.d("nfs", "DSMapFragment.newInstance()");
        if (ds == null) {
            throw new IllegalArgumentException("null ds");
        }

        DSMapFragment frag = new DSMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("ds", ds);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "DSMapFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ds_map_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            DistributionSale ds = (DistributionSale) args.getSerializable("ds");
            if (ds == null) {
                throw new IllegalStateException("null ds");
            }

            FragmentManager fm = getChildFragmentManager();

            DirectionsFragment directionsFragment = DirectionsFragment.newInstance("DIRECTIONS");

            SupportMapFragment mapFragment = new SupportMapFragment();

            fm.beginTransaction()
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
        Log.d("nfs", "DSMapFragment.onMapReady()");

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
        Log.d("nfs", "DSMapFragment.onLocationUpdated()");
        Log.d("nfs", "DS MAP FRAG LOCATION");
        if (location != null) {
            Log.d("nfs", location.toString());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }
}