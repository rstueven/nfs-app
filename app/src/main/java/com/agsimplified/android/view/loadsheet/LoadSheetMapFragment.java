package com.agsimplified.android.view.loadsheet;

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
import com.agsimplified.android.model.LoadSheetDETAIL;
import com.agsimplified.android.view.AgSimplifiedActivity;
import com.agsimplified.android.view.DirectionsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rstueven on 3/13/18.
 * <p>distributionSale Map and Directions</p>
 */

public class LoadSheetMapFragment extends Fragment
        implements OnMapReadyCallback, AgSimplifiedActivity.LocationListener {
    private GoogleMap mMap;

    public static LoadSheetMapFragment newInstance(LoadSheetDETAIL loadSheet) {
        Log.d("nfs", "LoadSheetMapFragment.newInstance()");
        if (loadSheet == null) {
            throw new IllegalArgumentException("null loadSheet");
        }

        LoadSheetMapFragment frag = new LoadSheetMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheet", loadSheet);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "LoadSheetMapFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.load_sheet_map_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            LoadSheetDETAIL loadSheet = (LoadSheetDETAIL) args.getSerializable("loadSheet");
            if (loadSheet == null) {
                throw new IllegalStateException("null loadSheet");
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
        Log.d("nfs", "LoadSheetMapFragment.onMapReady()");

        mMap = map;

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

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(41.736533, -95.701810))
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        activity.registerLocationListener(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        Log.d("nfs", "LoadSheetMapFragment.onLocationUpdated()");
        Log.d("nfs", "LOAD SHEET MAP FRAG LOCATION");
        if (location != null) {
            Log.d("nfs", location.toString());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }
}