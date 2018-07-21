package com.agsimplified.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.agsimplified.android.util.NetworkRequestQueue;
import com.agsimplified.android.util.SharedPref;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rstueven on 2/24/18.
 * Global NFS application class
 */

public class AgSimplified extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // SharedPreferences singleton
        SharedPref.init(getApplicationContext());

        // Volley RequestQueue singleton
        NetworkRequestQueue.init(getApplicationContext());
    }

    // Server configuration
    private static String getBaseUrl() {
        String host;

        switch(BuildConfig.FLAVOR) {
            case "local":
                host = "http://10.0.2.2:3000";
                break;
            case "development":
                host = "http://test.agsimplified.com";
                break;
            case "staging":
                host = "http://www.agsimplified.com";
                break;
            default:
                throw new IllegalArgumentException("FLAVOR: " + BuildConfig.FLAVOR);
        }

        return host;
    }

    public static String getApiUrl() {
        return getBaseUrl() + "/api";
    }
}