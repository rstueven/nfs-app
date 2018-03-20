package com.agsimplified.android.views;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.agsimplified.android.models.DistributionSale;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rstueven on 3/13/18.
 * <p>DS Map and Directions</p>
 */

public class DistributionSaleMapFragment extends Fragment
        implements OnMapReadyCallback, AgSimplifiedActivity.LocationListener {
    private GoogleMap mMap;

    public static DistributionSaleMapFragment newInstance(DistributionSale ds) {
        Log.d("nfs", "DistributionSaleMapFragment.newInstance()");
        if (ds == null) {
            throw new IllegalArgumentException("null ds");
        }

        DistributionSaleMapFragment frag = new DistributionSaleMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("ds", ds);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "DistributionSaleMapFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_distribution_sale_map, container, false);

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

            LoadSheetFragment loadSheetFragment = LoadSheetFragment.newInstance(ds);

            DirectionsFragment directionsFragment = DirectionsFragment.newInstance(ds);

            SupportMapFragment mapFragment = new SupportMapFragment();

            fm.beginTransaction()
                    .add(R.id.loadSheetFrame, loadSheetFragment, "loadSheet")
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
        Log.d("nfs", "DistributionSaleMapFragment.onMapReady()");

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
        Log.d("nfs", "DistributionSaleMapFragment.onLocationUpdated()");
        Log.d("nfs", "DS MAP FRAG LOCATION");
        if (location != null) {
            Log.d("nfs", location.toString());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }
}