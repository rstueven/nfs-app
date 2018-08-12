package com.agsimplified.android.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agsimplified.android.AgSimplified;
import com.agsimplified.android.R;
import com.agsimplified.android.database.DbOpenHelper;
import com.agsimplified.android.database.Field;
import com.agsimplified.android.database.StorageInventory;
import com.agsimplified.android.model.LoadSheetDetail;
import com.agsimplified.android.model.fieldactivity.FieldActivity;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.agsimplified.android.util.SharedPref;
import com.agsimplified.android.view.loadsheet.LoadSheetActivity;
import com.agsimplified.android.view.loadsheet.LoadSheetSearchFragment;
import com.agsimplified.android.view.fieldactivity.FAActivity;
import com.agsimplified.android.view.fieldactivity.FASearchFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AgSimplifiedActivity
        implements LoadSheetSearchFragment.LoadSheetSearcher, FASearchFragment.FieldActivitySearcher {
    private static final ArrayList<FieldActivity> fieldActivities = new ArrayList<>();

    static {
        fieldActivities.add(new FieldActivity(2048, 17171, null, 2018, "Fertilizing", "Shawn Jespersen", "Shawn Jespersen", "Grant", "GrantJorg228a", 173.49, 10.1, 5.0));
        fieldActivities.add(new FieldActivity(2304, 17293, null, 2018, "Fertilizing", "David Staben", "David Staben", "Home Place", "Home", 57.43, 11.2, 6.1));
        fieldActivities.add(new FieldActivity(2560, 18012, null, 2018, "Fertilizing", "Dammann Farms", "Dammann Farms", "Beh Farm", "Beh 578 E6ac", 6.31, 12.3, 7.2));
        fieldActivities.add(new FieldActivity(2049, 17171, null, 2018, "Fertilizing", "Shawn Jespersen", "Shawn Jespersen", "Grant", "GrantSmithS78a", 78.14, null, null));
    }

    private ListView searchResultsView;
    private FieldActivityAdapter fieldActivitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("nfs", "MainActivity.onCreate()");
        setContentView(R.layout.main_activity);

        searchResultsView = findViewById(R.id.searchResultsView);
        fieldActivitiesAdapter = new FieldActivityAdapter(this, fieldActivities);
    }

    public void showLoadSheetSearch(View v) {
        DialogFragment newFragment = new LoadSheetSearchFragment();
        newFragment.show(getFragmentManager(), "loadSheetSearch");
    }

    public void searchLoadSheets(Integer client, Integer year, Integer jobCode, Integer clientJobCode, Integer fromId, Integer toId, Integer productId) {
        Log.d("nfs", "searchLoadSheets(" + client + ", " + year + ", " + jobCode + ", " + clientJobCode + ", " + fromId + ", " + toId + ", " + productId + ")");
        List<LoadSheetDetail> loadSheetDetails = LoadSheetDetail.search(client, year, jobCode, clientJobCode, fromId, toId, productId);
        if (loadSheetDetails.size() > 0) {
            LoadSheetAdapter loadSheetAdapter = new LoadSheetAdapter(this, loadSheetDetails);
            searchResultsView.setAdapter(loadSheetAdapter);
            searchResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    LoadSheetDetail loadSheetDetail = (LoadSheetDetail) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(MainActivity.this, LoadSheetActivity.class);
                    intent.putExtra("loadSheetDetail", loadSheetDetail);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(this, "No load sheets found.", Toast.LENGTH_LONG).show();
        }
    }

    class LoadSheetAdapter extends ArrayAdapter<LoadSheetDetail> {
        LoadSheetAdapter(Context context, List<LoadSheetDetail> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LoadSheetDetail loadSheetDetail = getItem(position);
            if (loadSheetDetail == null) {
                throw new IllegalStateException("null loadSheetDetail");
            }

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.load_sheet_item, parent, false);
            }

            TextView jobCodeView = convertView.findViewById(R.id.jobCode);
            TextView clientJobCodeView = convertView.findViewById(R.id.clientJobCode);
            TextView yearView = convertView.findViewById(R.id.year);
            TextView productView = convertView.findViewById(R.id.product);
            TextView fromView = convertView.findViewById(R.id.fromOperation);
            TextView toView = convertView.findViewById(R.id.toOperation);

            jobCodeView.setText(loadSheetDetail.getJobCodeString());
            clientJobCodeView.setText(loadSheetDetail.getClientJobCodeString());
            yearView.setText(loadSheetDetail.getYearString());
            productView.setText(loadSheetDetail.getProductName());
            Field fromField = loadSheetDetail.getFromField();
            StorageInventory fromStorageInventory = loadSheetDetail.getFromStorageInventory();
            if (fromField != null) {
                fromView.setText(fromField.siteFarmField());
            } else if (fromStorageInventory != null) {
                fromView.setText(fromStorageInventory.siteStorageName());
            } else {
                fromView.setText("");
            }
            Field toField = loadSheetDetail.getToField();
            StorageInventory toStorageInventory = loadSheetDetail.getToStorageInventory();
            if (toField != null) {
                toView.setText(toField.siteFarmField());
            } else if (toStorageInventory != null) {
                toView.setText(toStorageInventory.siteStorageName());
            } else {
                toView.setText("");
            }

            convertView.setBackgroundColor(Color.parseColor(position % 2 == 0 ? "#ffffff" : "#e0e0e0"));

            return convertView;
        }
    }

    public void showFieldActivitySearch(View v) {
        DialogFragment newFragment = new FASearchFragment();
        newFragment.show(getFragmentManager(), "fieldActivitySearch");
    }

    public void searchFieldActivities(String client, int year, Integer jobCode, Integer clientJobCode, String activityType, String operation, String farm) {
        searchResultsView.setAdapter(fieldActivitiesAdapter);
        searchResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FieldActivity fieldActivity = (FieldActivity) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, FAActivity.class);
                intent.putExtra("fieldActivity", fieldActivity);
                startActivity(intent);
            }
        });
    }

    class FieldActivityAdapter extends ArrayAdapter<FieldActivity> {
        FieldActivityAdapter(Context context, ArrayList<FieldActivity> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            FieldActivity fieldActivity = getItem(position);
            if (fieldActivity == null) {
                throw new IllegalStateException("null fieldActivity");
            }

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fa_item, parent, false);
            }

            TextView jobCodeView = convertView.findViewById(R.id.jobCode);
            TextView clientJobCodeView = convertView.findViewById(R.id.clientJobCode);
            TextView yearView = convertView.findViewById(R.id.year);
            TextView typeView = convertView.findViewById(R.id.activityType);
            TextView operationView = convertView.findViewById(R.id.operation);
            TextView farmView = convertView.findViewById(R.id.farm);

            jobCodeView.setText(fieldActivity.getJobCodeString());
            clientJobCodeView.setText(fieldActivity.getClientJobCodeString());
            yearView.setText(fieldActivity.getYearString());
            typeView.setText(fieldActivity.getActivityType());
            operationView.setText(fieldActivity.getOperation());
            farmView.setText(fieldActivity.getFarm());

            convertView.setBackgroundColor(Color.parseColor(position % 2 == 0 ? "#ffffff" : "#e0e0e0"));

            return convertView;
        }
    }

    public void logout(View v) {
        Log.d("nfs", "MainActivity.logout()");
        RequestQueue queue = NetworkRequestQueue.getRequestQueue();
        final String url = AgSimplified.getApiUrl() + "/sessions?auth_token=" + SharedPref.read(SharedPref.Pref.AUTH_TOKEN, null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("nfs", "MainActivity.logout(" + url + "): response");
                        Log.i("nfs", response.toString());
                        doLogout();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nfs", "MainActivity.logout(" + url + "): ERROR");
                Log.e("nfs", error.toString());
                Toast.makeText(MainActivity.this, "Logout failed", Toast.LENGTH_LONG).show();

                // Logout anyway
                doLogout();
            }
        });

        queue.add(request);
    }

    private void doLogout() {
        SharedPref.write(SharedPref.Pref.AUTH_TOKEN, null);

        DbOpenHelper.getInstance().close();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}