package com.agsimplified.android.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface Destinationable {
    String getName();
    String getGeoJson();
    LatLng getLocation();
}