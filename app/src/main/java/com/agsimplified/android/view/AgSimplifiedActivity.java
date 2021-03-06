package com.agsimplified.android.view;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.agsimplified.android.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rstueven on 2/24/18.
 * Base NFS activity class.
 * Includes location tracking.
 */

public abstract class AgSimplifiedActivity extends AppCompatActivity {
    private static final String PERMISSIONS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int REQUEST_FINE_LOCATION = 1;
    private static final String IS_REQUESTING_LOCATION_UPDATES = "isRequestingLocationUpdates";

    public interface LocationListener {
        void onLocationUpdated(Location location);
    }

    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private boolean isRequestingLocationUpdates = false;
    private LocationCallback mLocationCallback;
    private final List<LocationListener> locationListeners = new ArrayList<>();

    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "AgSimplifiedActivity.onCreate()");
        super.onCreate(savedInstanceState);
        updateValuesFromBundle(savedInstanceState);

        // Force landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        checkLocationPermission();

    }

    private void checkLocationPermission() {
        Log.d("nfs", "AgSimplifiedActivity.checkLocationPermission()");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                final Activity activity = this;
                new AlertDialog.Builder(activity)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, new String[] {PERMISSIONS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {PERMISSIONS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            }
        } else {
            setupLocationClient();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.d("nfs", "AgSimplifiedActivity.onRequestPermissionsResult()");
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupLocationClient();
            }
        }
    }

    private void setupLocationClient() {
        Log.d("nfs", "AgSimplifiedActivity.setupLocationClient()");
        if (mFusedLocationClient == null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        for (Location location : locationResult.getLocations()) {
//                            Log.d("nfs", "LOCATION_RESULT: " + location.toString());
                            mCurrentLocation = location;

                            for (LocationListener listener : locationListeners) {
                                listener.onLocationUpdated(location);
                            }
                        }
                    }
                }
            };

            checkCurrentLocation();
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        Log.d("nfs", "AgSimplifiedActivity.onPause()");
        super.onPause();

        // Is this necessarily true? Or should it be controlled by the Fragments?
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        Log.d("nfs", "AgSimplifiedActivity.onResume()");
        super.onResume();
        if (isRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("nfs", "AgSimplifiedActivity.onSaveInstanceState()");
        outState.putBoolean(IS_REQUESTING_LOCATION_UPDATES, isRequestingLocationUpdates);

        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.d("nfs", "AgSimplifiedActivity.updateValuesFromBundle()");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(IS_REQUESTING_LOCATION_UPDATES)) {
                isRequestingLocationUpdates = savedInstanceState.getBoolean(IS_REQUESTING_LOCATION_UPDATES);
            }
        }
    }

    public void registerLocationListener(LocationListener listener) {
        Log.d("nfs", "AgSimplifiedActivity.registerLocationListener()");
        if (listener != null && !locationListeners.contains(listener)) {
            locationListeners.add(listener);
            isRequestingLocationUpdates = true;
            startLocationUpdates();
        }
    }

    protected void unRegisterLocationListener(LocationListener listener) {
        Log.d("nfs", "AgSimplifiedActivity.unRegisterLocationListener()");
        if (listener != null) {
            locationListeners.remove(listener);
            if (locationListeners.size() == 0) {
                isRequestingLocationUpdates = false;
                stopLocationUpdates();
            }
        }
    }

    private void startLocationUpdates() {
        Log.d("nfs", "AgSimplifiedActivity.startLocationUpdates()");
        if (mFusedLocationClient != null) {
            if (ContextCompat.checkSelfPermission(this, PERMISSIONS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            }
        }
    }

    private void stopLocationUpdates() {
        Log.d("nfs", "AgSimplifiedActivity.stopLocationUpdates()");
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void checkCurrentLocation() {
        Log.d("nfs", "AgSimplifiedActivity.checkCurrentLocation()");
        if (mCurrentLocation == null) {
            if (ContextCompat.checkSelfPermission(this, PERMISSIONS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                loadCurrentLocation();
            }
        }

//        onGotCurrentLocation();
    }

    private void loadCurrentLocation() {
        Log.d("nfs", "AgSimplifiedActivity.loadCurrentLocation()");
        if (mFusedLocationClient != null) {
            if (ContextCompat.checkSelfPermission(this, PERMISSIONS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        mCurrentLocation = location;
                        //                onGotCurrentLocation();
                    }
                });
            }
        }
    }

    public Location getCurrentLocation() {
        Log.d("nfs", "AgSimplifiedActivity.getCurrentLocation()");
        Log.d("nfs", "getCurrentLocation(" + (mCurrentLocation != null ? mCurrentLocation.toString() : "NULL") + ")");
        return mCurrentLocation;
    }
}