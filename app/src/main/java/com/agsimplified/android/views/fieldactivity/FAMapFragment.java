package com.agsimplified.android.views.fieldactivity;

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
import android.widget.Button;

import com.agsimplified.android.AgSimplified;
import com.agsimplified.android.R;
import com.agsimplified.android.models.fieldactivity.FieldActivity;
import com.agsimplified.android.views.AgSimplifiedActivity;
import com.agsimplified.android.views.DirectionsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rstueven on 5/13/18.
 * <p>fieldActivity Application Map</p>
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "FAMapFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fa_map_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            FieldActivity fieldActivity = (FieldActivity) args.getSerializable("fieldActivity");
            if (fieldActivity == null) {
                throw new IllegalStateException("null fieldActivity");
            }

            FragmentManager fm = getChildFragmentManager();

            SupportMapFragment mapFragment = new SupportMapFragment();

            fm.beginTransaction()
                    .add(R.id.mapFrame, mapFragment, "map")
                    .commit();

            mapFragment.getMapAsync(this);

            Button addLoadButton = rootView.findViewById(R.id.addLoadButton);
            addLoadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fakePath();
                }
            });
        }

        return rootView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("nfs", "FAMapFragment.onMapReady()");

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

//        activity.registerLocationListener(this);
    }

    @Override
    public void onLocationUpdated(Location location) {
        Log.d("nfs", "FAMapFragment.onLocationUpdated()");
        Log.d("nfs", "fieldActivity MAP LOCATION");
        if (location != null) {
            Log.d("nfs", location.toString());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        }
    }

    private static final List<LatLng> fakePoints = new ArrayList<>();
    static {
        fakePoints.add(new LatLng(41.733228, -95.699589));
        fakePoints.add(new LatLng(41.733405, -95.699441));
        fakePoints.add(new LatLng(41.733607, -95.699275));
        fakePoints.add(new LatLng(41.733806, -95.699127));
        fakePoints.add(new LatLng(41.733913, -95.699026));
        fakePoints.add(new LatLng(41.734046, -95.698929));
        fakePoints.add(new LatLng(41.734172, -95.698844));
        fakePoints.add(new LatLng(41.734273, -95.698743));
        fakePoints.add(new LatLng(41.734380, -95.698646));
        fakePoints.add(new LatLng(41.734500, -95.698544));
        fakePoints.add(new LatLng(41.734566, -95.698413));
        fakePoints.add(new LatLng(41.734551, -95.698320));
        fakePoints.add(new LatLng(41.734500, -95.698295));
        fakePoints.add(new LatLng(41.734437, -95.698341));
        fakePoints.add(new LatLng(41.734342, -95.698447));
        fakePoints.add(new LatLng(41.734257, -95.698515));
        fakePoints.add(new LatLng(41.734140, -95.698599));
        fakePoints.add(new LatLng(41.734033, -95.698700));
        fakePoints.add(new LatLng(41.733901, -95.698811));
        fakePoints.add(new LatLng(41.733790, -95.698887));
        fakePoints.add(new LatLng(41.733686, -95.698980));
        fakePoints.add(new LatLng(41.733576, -95.699064));
        fakePoints.add(new LatLng(41.733488, -95.699140));
        fakePoints.add(new LatLng(41.733396, -95.699233));
        fakePoints.add(new LatLng(41.733311, -95.699280));
        fakePoints.add(new LatLng(41.733226, -95.699220));
        fakePoints.add(new LatLng(41.733223, -95.699094));
        fakePoints.add(new LatLng(41.733329, -95.699010));
        fakePoints.add(new LatLng(41.733408, -95.698929));
        fakePoints.add(new LatLng(41.733513, -95.698861));
        fakePoints.add(new LatLng(41.733607, -95.698773));
        fakePoints.add(new LatLng(41.733715, -95.698688));
        fakePoints.add(new LatLng(41.733831, -95.698591));
        fakePoints.add(new LatLng(41.733907, -95.698523));
        fakePoints.add(new LatLng(41.734058, -95.698413));
        fakePoints.add(new LatLng(41.734156, -95.698320));
        fakePoints.add(new LatLng(41.734267, -95.698231));
        fakePoints.add(new LatLng(41.734368, -95.698147));
    };

    public void fakePath() {
        final AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (LatLng p : fakePoints) {
                    final Location l = new Location("fake");
                    l.setLatitude(p.latitude);
                    l.setLongitude(p.longitude);
                    Log.d("nfs", "FAKE: " + l.toString());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(l.getLatitude(), l.getLongitude())));
                        }
                    });
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
