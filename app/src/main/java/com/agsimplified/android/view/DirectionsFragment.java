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

import com.agsimplified.android.R;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DirectionsFragment extends Fragment {
    TextView directionsView;

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

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            String directions = args.getString("directions");

            directionsView = view.findViewById(R.id.directions);
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
//                Log.d("nfs", "CURRENT LOCATION: " + location.toString());
//                Log.d("nfs", "FROM LATLNG: " + fromLatLng.toString());
                Directionable directionable = (Directionable) activity;
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
                                        } else {
                                            directions.append("<b>No legs found in the route.</b>");
                                        }
                                    } else {
                                        directions.append("<b>No routes found.</b>");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                directionsView.setText(Html.fromHtml(directions.toString()));
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
}