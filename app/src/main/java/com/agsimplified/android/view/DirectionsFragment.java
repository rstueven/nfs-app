package com.agsimplified.android.view;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DirectionsFragment extends Fragment {
    TextView directionsView;

    public interface Directionable {
        LatLng getDestinationLocation();
        GoogleMap getMap();
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

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            String directions = args.getString("directions");

            directionsView = view.findViewById(R.id.directions);
            directionsView.setText(Html.fromHtml(directions, Html.FROM_HTML_MODE_LEGACY));

            ImageButton navBtn = view.findViewById(R.id.navigationButton);
            navBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startNavigation();
                }
            });

            ImageButton refreshBtn = view.findViewById(R.id.refreshButton);
            refreshBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshDirections();
                }
            });
        }

        return view;
    }

    // THIS SHOULDN'T BE NECESSARY!
    public void loadCurrentDirections() {
        loadCurrentDirections(null);
    }

    public void loadCurrentDirections(final GoogleMap map) {
        AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();
        if (activity != null) {
            Location location = activity.getCurrentLocation();
            if (location != null) {
                LatLng fromLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                Log.d("nfs", "CURRENT LOCATION: " + location.toString());
//                Log.d("nfs", "FROM LATLNG: " + fromLatLng.toString());
                final Directionable directionable = (Directionable) activity;
                LatLng toLatLng = directionable.getDestinationLocation();
//                Log.d("nfs", "TO LATLNG: " + toLatLng.toString());

                // TODO: Get an API key.
                final String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + fromLatLng.latitude + "," + fromLatLng.longitude + "&destination=" + toLatLng.latitude + "," + toLatLng.longitude;
//                Log.d("nfs", "URL: " + url);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("nfs", "DirectionsFragment.loadCurrentDirections().onResponse(" + url + ")");
                                StringBuilder directions = new StringBuilder();
                                try {
//                                Log.d("nfs", response.toString(2));
                                    JSONArray routes = response.getJSONArray("routes");
                                    // TODO: Multiple route options
                                    if (routes.length() > 0) {
                                        JSONObject route = routes.getJSONObject(0);
                                        JSONArray legs = route.getJSONArray("legs");
                                        if (legs.length() > 0) {
                                            JSONObject leg = legs.getJSONObject(0);
                                            String distance = leg.getJSONObject("distance").getString("text");
                                            String duration = leg.getJSONObject("duration").getString("text");
                                            directions.append("Distance: ").append(distance).append("<br>");
                                            directions.append("Duration: ").append(duration).append("<br><br>");

                                            JSONArray steps = leg.getJSONArray("steps");
                                            JSONObject step;
                                            for (int i = 0; i < steps.length(); i++) {
                                                step = steps.getJSONObject(i);
                                                String stepDistance = step.getJSONObject("distance").getString("text");
                                                String stepDuration = step.getJSONObject("duration").getString("text");
                                                directions.append(step.getString("html_instructions")).append("<br>");
                                                directions.append(stepDistance).append("&nbsp;&mdash;&nbsp;").append(stepDuration).append("<br><br>");
                                            }

                                            if (map != null) {
                                                JSONObject b = route.getJSONObject("bounds");
                                                JSONObject ne = b.getJSONObject("northeast");
                                                double neLat = ne.getDouble("lat");
                                                double neLng = ne.getDouble("lng");
                                                JSONObject sw = b.getJSONObject("southwest");
                                                double swLat = sw.getDouble("lat");
                                                double swLng = sw.getDouble("lng");

                                                LatLngBounds bounds = new LatLngBounds(new LatLng(swLat, swLng), new LatLng(neLat, neLng));
                                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 64);
                                                map.animateCamera(cameraUpdate);

                                                String polyline = route.getJSONObject("overview_polyline").getString("points");
                                                List<LatLng> points = PolyUtil.decode(polyline);
                                                PolylineOptions pathOpts = new PolylineOptions()
                                                        .width(5)
                                                        .color(Color.WHITE);
                                                Polyline path = map.addPolyline(pathOpts);
                                                path.setPoints(points);

                                            } else {
                                                Log.w("nfs", "DirectionsFragment.loadCurrentDirections(): null map");
                                            }
                                        } else {
                                            directions.append("<b>No legs found in the route.</b>");
                                        }
                                    } else {
                                        directions.append("<b>No routes found.</b>");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                directionsView.setText(Html.fromHtml(directions.toString(), Html.FROM_HTML_MODE_LEGACY));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("nfs", "DirectionsFragment.loadCurrentDirections().onErrorResponse()");
                                Log.e("nfs", error.toString());
                            }
                        }
                );

                NetworkRequestQueue.addToRequestQueue(request);
            } else {
                Log.w("nfs", "DirectionsFragment.loadCurrentDirections(): null location");
//                Toast.makeToast(activity, "I'm not sure where we are.", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("nfs", "DirectionsFragment.loadCurrentDirections(): null activity");
//                Toast.makeToast(activity, "Internal error.", Toast.LENGTH_LONG).show();
        }
    }

    public void startNavigation() {
        Log.d("nfs", "START NAVIGATION");
        AgSimplifiedActivity activity = (AgSimplifiedActivity) getActivity();
        if (activity != null) {
            final Directionable directionable = (Directionable) activity;
            LatLng toLatLng = directionable.getDestinationLocation();
            String uri = "google.navigation:q=" + toLatLng.latitude + "," + toLatLng.longitude;
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    public void refreshDirections() {
        Log.d("nfs", "REFRESH DIRECTIONS");
    }
}