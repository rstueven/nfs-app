package com.agsimplified.android.view;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.agsimplified.android.R;
import com.google.android.gms.maps.model.LatLng;

public class DirectionsFragment extends Fragment {
    public interface Directionable {
        LatLng getDestinationLocation();
    }

    public static DirectionsFragment newInstance(String directions) {
        DirectionsFragment frag = new DirectionsFragment();
        Bundle args = new Bundle();
        args.putString("directions", directions);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.directions_fragment, container, false);

        AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            String directions = args.getString("directions");

            TextView directionsView = view.findViewById(R.id.directions);
            directionsView.setText(Html.fromHtml(directions));

            loadCurrentDirections();

        }

        return view;
    }

    private void loadCurrentDirections() {
        AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();
        if (activity != null) {
            Location location = activity.getCurrentLocation();
            if (location != null) {
                LatLng fromLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d("nfs", "CURRENT LOCATION: " + location.toString());
                Log.d("nfs", "FROM LATLNG: " + fromLatLng.toString());
                Directionable directionable = (Directionable) activity;
                LatLng toLatLng = directionable.getDestinationLocation();
                Log.d("nfs", "TO LATLNG: " + toLatLng.toString());

                String url = "https://maps.googleapis.com/maps/api/directions/json?";
                url += "origin=" + fromLatLng.latitude + "," + fromLatLng.longitude;
                url += "&destination=" + toLatLng.latitude + "," + toLatLng.longitude;

                Log.d("nfs", "URL: " + url);
            } else {
                Log.w("nfs", "DirectionsFragment.loadCurrentDirections(): null location");
//                Toast.makeToast(activity, "I'm not sure where we are.", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("nfs", "DirectionsFragment.loadCurrentDirections(): null activity");
//                Toast.makeToast(activity, "Internal error.", Toast.LENGTH_LONG).show();
        }
    }
}