package com.agsimplified.android;

import android.app.Application;

/**
 * Created by rstueven on 2/24/18.
 * Global NFS application class
 */

public class AgSimplified extends Application {
    // Server configuration
    public static String getBaseUrl() {
        String scheme = "http";
        String host;

        switch(BuildConfig.FLAVOR) {
            case "local":
                host = "127.0.0.1:3000";
                break;
            case "development":
                host = "test.agsimplified.com";
                break;
            case "staging":
                host = "app.agsimplified.com";
                break;
            default:
                throw new IllegalArgumentException("FLAVOR: " + BuildConfig.FLAVOR);
        }

        return String.format("%s://%s", scheme, host);
    }

    public static String getApiUrl() {
        return getBaseUrl() + "/api";
    }
}