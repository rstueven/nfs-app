package com.agsimplified.android.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface GeoLocatable {
    String getName();
    String getFullName();
    String getGeoJson();
    LatLng getLocation();
}