package com.agsimplified.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.agsimplified.android.AgSimplified;
import com.agsimplified.android.util.SharedPref;

abstract class LoadTableAsync extends AsyncTask<Void, Void, Void> {
    final SQLiteDatabase mDb;

    LoadTableAsync(SQLiteDatabase db) {
        Log.d("nfs", "LoadTableAsync()");
        mDb = db;
    }

    static String setUrl(final String table) {
        Log.d("nfs", "LoadTableAsync.setUrl(" + table + ")");
        String authToken = SharedPref.read(SharedPref.Pref.AUTH_TOKEN, null);
        String url = AgSimplified.getApiUrl() + "/database/load?auth_token=" + authToken;

//        url += "&table=" + table;

        return url;
    }
}