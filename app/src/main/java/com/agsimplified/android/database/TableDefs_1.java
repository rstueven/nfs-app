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
    TableDefs_1() {
        super();
    }

    @Override
    protected void initTableDefs() {
        Log.d("nfs", "TableDefs_1.initTableDefs()");
        createStatements.put(Client.TABLE_NAME,
                "CREATE TABLE IF NOT EXISTS "
                        + Client.TABLE_NAME + " ("
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
    }

    @Override
    public void loadData(SQLiteDatabase db) {
        Log.d("nfs", "TableDefs_1.loadData()");
        new LoadAsync(db).execute();
//        new Client.LoadAsync(db).execute();
//        new JobPlan.LoadAsync(db).execute();
//        new DistributionSale.LoadAsync(db).execute();
//        new Site.LoadAsync(db).execute();
//        new Product.LoadAsync(db).execute();
    }

    private static class LoadAsync extends AsyncTask<Void, Void, Void> {
        final SQLiteDatabase mDb;

        LoadAsync(SQLiteDatabase db) {
            Log.d("nfs", "TableDefs_1.LoadAsync()");
            mDb = db;
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
                                Client[] clients = Client.createClients(response.getJSONArray(Client.TABLE_NAME));
                                new Client.PopulateAsync(mDb).execute(clients);
                                JobPlan[] jobPlans = JobPlan.createJobPlans(response.getJSONArray(JobPlan.TABLE_NAME));
                                new JobPlan.PopulateAsync(mDb).execute(jobPlans);
                                DistributionSale[] distributionSales = DistributionSale.createDistributionSales(response.getJSONArray(DistributionSale.TABLE_NAME));
                                new DistributionSale.PopulateAsync(mDb).execute(distributionSales);
                                Site[] sites = Site.createSites(response.getJSONArray(Site.TABLE_NAME));
                                new Site.PopulateAsync(mDb).execute(sites);
                                Product[] products = Product.createProducts(response.getJSONArray(Product.TABLE_NAME));
                                new Product.PopulateAsync(mDb).execute(products);
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