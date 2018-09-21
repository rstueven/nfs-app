package com.agsimplified.android.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.agsimplified.android.AgSimplified;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.agsimplified.android.util.SharedPref;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class TableDefs_1 extends TableDefs {
    private DbOpenHelper dbHelper;

    TableDefs_1(DbOpenHelper dbHelper) {
        super();

        if (dbHelper != null) {
            this.dbHelper = dbHelper;
        } else {
            throw new IllegalArgumentException("null dbHelper");
        }
    }

    @Override
    protected void initTableDefs() {
        Log.d("nfs", "TableDefs_1.initTableDefs()");

        createStatements.put(Client.getTableName(),
                "CREATE TABLE IF NOT EXISTS "
                        + Client.getTableName() + " ("
                        + TextUtils.join(",", Client.COLUMNS)
                        + ")");
        createStatements.put(JobPlan.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + JobPlan.TABLE_NAME + " ("
                        + TextUtils.join(",", JobPlan.COLUMNS)
                        + ")");
        createStatements.put(DistributionSale.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + DistributionSale.TABLE_NAME + " ("
                        + TextUtils.join(",", DistributionSale.COLUMNS)
                        + ")");
        createStatements.put(Site.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Site.TABLE_NAME + " ("
                        + TextUtils.join(",", Site.COLUMNS)
                        + ")");
        createStatements.put(Product.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Product.TABLE_NAME + " ("
                        + TextUtils.join(",", Product.COLUMNS)
                        + ")");
        createStatements.put(Farm.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Farm.TABLE_NAME + " ("
                        + TextUtils.join(",", Farm.COLUMNS)
                        + ")");
        createStatements.put(Field.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Field.TABLE_NAME + " ("
                        + TextUtils.join(",", Field.COLUMNS)
                        + ")");
        createStatements.put(LoadSheet.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + LoadSheet.TABLE_NAME + " ("
                        + TextUtils.join(",", LoadSheet.COLUMNS)
                        + ")");
        createStatements.put(Load.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Load.TABLE_NAME + " ("
                        + TextUtils.join(",", Load.COLUMNS)
                        + ")");
        createStatements.put(StorageInventory.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + StorageInventory.TABLE_NAME + " ("
                        + TextUtils.join(",", StorageInventory.COLUMNS)
                        + ")");
        createStatements.put(Storage.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Storage.TABLE_NAME + " ("
                        + TextUtils.join(",", Storage.COLUMNS)
                        + ")");
    }

    @Override
    public void loadData(SQLiteDatabase db) {
        Log.d("nfs", "TableDefs_1.loadData()");
        new LoadAsync(dbHelper, db).execute();
    }

    private static class LoadAsync extends AsyncTask<Void, Void, Void> {
        final DbOpenHelper dbHelper;
        final SQLiteDatabase mDb;

        LoadAsync(DbOpenHelper dbHelper, SQLiteDatabase db) {
            Log.d("nfs", "TableDefs_1.LoadAsync()");
            this.dbHelper = dbHelper;
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("nfs", "TableDefs_1.LoadAsync.doInBackground()");
            final String url = setUrl();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("nfs", "TableDefs_1.LoadAsync.onResponse(" + url + ")");
                            try {
//                                Log.d("nfs", response.toString(2));
                                new Client.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Client.getTableName()));
                                new DistributionSale.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(DistributionSale.TABLE_NAME));
                                new Farm.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Farm.TABLE_NAME));
                                new Field.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Field.TABLE_NAME));
                                new JobPlan.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(JobPlan.TABLE_NAME));
                                new Load.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Load.TABLE_NAME));
                                new LoadSheet.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(LoadSheet.TABLE_NAME));
                                new Product.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Product.TABLE_NAME));
                                new Site.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Site.TABLE_NAME));
                                new Storage.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(Storage.TABLE_NAME));
                                new StorageInventory.PopulateAsync(dbHelper, mDb).execute(response.getJSONArray(StorageInventory.TABLE_NAME));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("nfs", "PopulateDbAsync.onErrorResponse()");
                            Log.e("nfs", error.toString());
                        }
                    }
            );

            NetworkRequestQueue.addToRequestQueue(request);

            return null;
        }

        static String setUrl() {
            Log.d("nfs", "TableDefs_1.LoadAsync.setUrl()");
            String authToken = SharedPref.read(SharedPref.Pref.AUTH_TOKEN, null);
            return AgSimplified.getApiUrl() + "/database/load?auth_token=" + authToken;
        }
    }
}