package com.agsimplified.android.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.agsimplified.android.R;
import com.agsimplified.android.database.DbOpenHelper;

import java.util.HashMap;
import java.util.Map;

public class LoadDatabaseActivity extends AgSimplifiedActivity implements DbOpenHelper.LoadListener {
    private Map<String, ProgressBar> progressBarMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_database);

        progressBarMap.put("clients", (ProgressBar) findViewById(R.id.clients_progress));
        progressBarMap.put("distribution_sales", (ProgressBar) findViewById(R.id.distribution_sales_progress));
        progressBarMap.put("farms", (ProgressBar) findViewById(R.id.farms_progress));
        progressBarMap.put("fields", (ProgressBar) findViewById(R.id.fields_progress));
        progressBarMap.put("job_plans", (ProgressBar) findViewById(R.id.job_plans_progress));
        progressBarMap.put("loads", (ProgressBar) findViewById(R.id.loads_progress));
        progressBarMap.put("load_sheets", (ProgressBar) findViewById(R.id.load_sheets_progress));
        progressBarMap.put("products", (ProgressBar) findViewById(R.id.products_progress));
        progressBarMap.put("sites", (ProgressBar) findViewById(R.id.sites_progress));
        progressBarMap.put("storages", (ProgressBar) findViewById(R.id.storages_progress));
        progressBarMap.put("storage_inventories", (ProgressBar) findViewById(R.id.storage_inventories_progress));

        DbOpenHelper.registerLoadListener(this);
        SQLiteDatabase mDb = DbOpenHelper.getInstance().getWritableDatabase();
        // Force data load
        mDb.rawQuery("SELECT 1", null).close();
    }

    @Override
    public void onTableLoadStart(String tableName, int recordCount) {
//        Log.d("nfs", "onTableLoadStart(" + tableName + ", " + recordCount + ")");
        ProgressBar progressBar = progressBarMap.get(tableName);
        if (progressBar != null) {
            progressBar.setMax(recordCount);
            progressBar.setProgress(0);
        }
    }

    @Override
    public void onTableLoadProgress(String tableName, int recordCount) {
//        Log.d("nfs", "onTableLoadProgress(" + tableName + ", " + recordCount + ")");
        ProgressBar progressBar = progressBarMap.get(tableName);
        if (progressBar != null) {
            progressBar.setProgress(recordCount);
        }
    }

    @Override
    public void onTableLoadEnd(String tableName) {
        Log.d("nfs", "onTableLoadEnd(" + tableName + ")");
        progressBarMap.remove(tableName);
        if (progressBarMap.size() == 0) {
            onDatabaseLoaded();
        }
    }

    @Override
    public void onDatabaseLoaded() {
        Log.d("nfs", "LoadDatabaseActivity.onDatabaseLoaded()");
        DbOpenHelper.unregisterLoadListener(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
