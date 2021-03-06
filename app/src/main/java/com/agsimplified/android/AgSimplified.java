package com.agsimplified.android;

import android.app.Application;

import com.agsimplified.android.database.DbOpenHelper;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.agsimplified.android.util.SharedPref;

/**
 * Created by rstueven on 2/24/18.
 * Global NFS application class
 */

public class AgSimplified extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Database singleton
        DbOpenHelper.init(getApplicationContext());

        // SharedPreferences singleton
        SharedPref.init(getApplicationContext());

        // Volley RequestQueue singleton
        NetworkRequestQueue.init(getApplicationContext());
    }

    // Server configuration
    private static String getBaseUrl() {
        String host;

        switch(BuildConfig.FLAVOR) {
            case "emulator":
                host = "http://10.0.2.2:3000";
                break;
            case "local":
                host = "http://192.168.0.11:3000";
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