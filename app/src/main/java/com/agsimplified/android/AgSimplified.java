package com.agsimplified.android;

import android.app.Application;

/**
 * Created by rstueven on 2/24/18.
 * Global NFS application class
 */

public class AgSimplified extends Application {
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