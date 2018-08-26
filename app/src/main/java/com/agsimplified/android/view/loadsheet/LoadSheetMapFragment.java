package com.agsimplified.android.view.loadsheet;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.agsimplified.android.model.Destinationable;
import com.agsimplified.android.model.LoadSheetDetail;
import com.agsimplified.android.view.AgSimplifiedActivity;
import com.agsimplified.android.view.DirectionsFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rstueven on 3/13/18.
 * <p>distributionSale Map and Directions</p>
 */

public class LoadSheetMapFragment extends Fragment
        implements OnMapReadyCallback, AgSimplifiedActivity.LocationListener {
    private LoadSheetDetail loadSheetDetail;
    private GoogleMap mMap;
    private DirectionsFragment directionsFragment;

    public static LoadSheetMapFragment newInstance(LoadSheetDetail loadSheetDetail) {
        Log.d("nfs", "LoadSheetMapFragment.newInstance()");
        if (loadSheetDetail == null) {
            throw new IllegalArgumentException("null loadSheetDetail");
        }

        LoadSheetMapFragment frag = new LoadSheetMapFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheetDetail", loadSheetDetail);
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

            loadSheetDetail = (LoadSheetDetail) args.getSerializable("loadSheetDetail");
            if (loadSheetDetail == null) {
                throw new IllegalStateException("null loadSheetDetail");
            }

            FragmentManager fm = getChildFragmentManager();

            directionsFragment = DirectionsFragment.newInstance(loadSheetDetail.getDistributionSale().getDirections());

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

        try {
            Destinationable destination = loadSheetDetail.getDestination();
            if (destination == null) {
                Log.w("nfs", "NULL DESTINATION");
            } else {
                Log.d("nfs", destination.toString());
                JSONObject jsonObject = new JSONObject(destination.getGeoJson());
                JSONObject firstFeature = jsonObject.getJSONArray("features").getJSONObject(0);
                JSONObject geometry = firstFeature.getJSONObject("geometry");
                String geoType = geometry.getString("type");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                JSONArray coord;
                LatLng point;

                switch (geoType) {
                    case "Point":
                        point = new LatLng(coordinates.getDouble(1), coordinates.getDouble(0));
                        mMap.addMarker(new MarkerOptions()
                                .position(point)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        );
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(point)
                                .zoom(17)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        break;
                    case "Polygon":
                        LatLngBounds toBounds = null;
                        // TODO: What if there are more than one coordinates arrays?
                        coordinates = coordinates.getJSONArray(0);
                        if (coordinates.length() > 0) {
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            PolygonOptions pOpts = new PolygonOptions().strokeColor(Color.GREEN);

                            for (int i = 0; i < coordinates.length(); i++) {
                                coord = coordinates.getJSONArray(i);
                                point = new LatLng(coord.getDouble(1), coord.getDouble(0));
                                builder.include(point);
                                pOpts.add(point);
                            }
                            toBounds = builder.build();
                            map.addPolygon(pOpts);
                        }

                        if (toBounds != null) {
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(toBounds, 25);
                            mMap.animateCamera(cameraUpdate);
                        }
                        break;
                    default:
                        Log.w("nfs", "UNKNOWN geoType <" + geoType + ">");
                }
            }

            directionsFragment.loadCurrentDirections(mMap);
        } catch (JSONException e) {
            Log.w("nfs", "LoadSheetMapFragment.onMapReady(): " + e.getLocalizedMessage());
        }

        activity.registerLocationListener(this);
    }

    public GoogleMap getMap() {
        return mMap;
    }

    @Override
    public void onLocationUpdated(Location location) {
//        Log.d("nfs", "LoadSheetMapFragment.onLocationUpdated()");
//        Log.d("nfs", "LOAD SHEET MAP FRAG LOCATION");
//        if (location != null) {
//            Log.d("nfs", location.toString());
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
//        }
    }
}