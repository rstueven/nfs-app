package com.agsimplified.android.views;

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
import com.agsimplified.android.models.distributionsale.DistributionSale;
import com.agsimplified.android.models.fieldactivity.FieldActivity;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.agsimplified.android.util.SharedPref;
import com.agsimplified.android.views.distributionsale.DSActivity;
import com.agsimplified.android.views.distributionsale.DSSearchFragment;
import com.agsimplified.android.views.fieldactivity.FAActivity;
import com.agsimplified.android.views.fieldactivity.FASearchFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AgSimplifiedActivity
        implements DSSearchFragment.LoadSheetSearcher, FASearchFragment.FieldActivitySearcher {
    private static final ArrayList<DistributionSale> distributionSales = new ArrayList<>();

    static {
        distributionSales.add(new DistributionSale(1, 17263, null, 2018, "Beef-Solid- Open Lot-Pen Scrape", "Natural Fertilizer Products, Inc.:Crossroads Stockpile - E Pen - Solid Manure", "Tom Barry:Moms NE100a", 700.0, 660.06));
        distributionSales.add(new DistributionSale(2, 18014, null, 2018, "Beef-Solid- Open Lot-Pen Scrape", "Natural Fertilizer Products, Inc.:Koke Compost Site - Solid Manure", "Kevin Koke:Tower 144a", 0.0, 0.0));
        distributionSales.add(new DistributionSale(3, 17379, null, 2018, "Dairy-Solid-Veal calves, 250 lb.", "Natural Fertilizer Products, Inc.:Compost Yard at Kirkman Farms Dairy - Solid Manure", "Gabe Hansen:GabeEF_E30a", 0.0, 179.59));
        distributionSales.add(new DistributionSale(4, 17332, null, 2018, "Dairy-Solid-Veal calves, 250 lb.", "Natural Fertilizer Products, Inc.:Compost Yard at Kirkman Farms Dairy - Solid Manure", "Stoberl Farms Ltd:Bin Site E 169ac", 480.0, 616.21));
    }

    private static final ArrayList<FieldActivity> fieldActivities = new ArrayList<>();

    static {
        fieldActivities.add(new FieldActivity(2048, 17171, null, 2018, "Fertilizing", "Shawn Jespersen", "Shawn Jespersen", "Grant", "GrantJorg228a", 173.49, 10.1, 5.0));
        fieldActivities.add(new FieldActivity(2304, 17293, null, 2018, "Fertilizing", "David Staben", "David Staben", "Home Place", "Home", 57.43, 11.2, 6.1));
        fieldActivities.add(new FieldActivity(2560, 18012, null, 2018, "Fertilizing", "Dammann Farms", "Dammann Farms", "Beh Farm", "Beh 578 E6ac", 6.31, 12.3, 7.2));
        fieldActivities.add(new FieldActivity(2049, 17171, null, 2018, "Fertilizing", "Shawn Jespersen", "Shawn Jespersen", "Grant", "GrantSmithS78a", 78.14, null, null));
    }

    private ListView searchResultsView;
    private DistributionSaleAdapter distributionSaleAdapter;
    private FieldActivityAdapter fieldActivitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.d("nfs", "getWritableDatabase()");
        DbOpenHelper.getInstance().getWritableDatabase();

        searchResultsView = findViewById(R.id.searchResultsView);
        distributionSaleAdapter = new DistributionSaleAdapter(this, distributionSales);
        fieldActivitiesAdapter = new FieldActivityAdapter(this, fieldActivities);
    }

    public void showDistributionSaleSearch(View v) {
        DialogFragment newFragment = new DSSearchFragment();
        newFragment.show(getFragmentManager(), "loadSheetSearch");
    }

    public void searchDistributionSales(String client, int year, Integer jobCode, Integer clientJobCode, String fromOperation, String toOperation, String product) {
        searchResultsView.setAdapter(distributionSaleAdapter);
        searchResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DistributionSale distributionSale = (DistributionSale) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, DSActivity.class);
                intent.putExtra("distributionSale", distributionSale);
                startActivity(intent);
            }
        });
    }

    class DistributionSaleAdapter extends ArrayAdapter<DistributionSale> {
        DistributionSaleAdapter(Context context, ArrayList<DistributionSale> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            DistributionSale distributionSale = getItem(position);
            if (distributionSale == null) {
                throw new IllegalStateException("null distributionSale");
            }

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ds_item, parent, false);
            }

            TextView jobCodeView = convertView.findViewById(R.id.jobCode);
            TextView clientJobCodeView = convertView.findViewById(R.id.clientJobCode);
            TextView yearView = convertView.findViewById(R.id.year);
            TextView productView = convertView.findViewById(R.id.product);
            TextView fromView = convertView.findViewById(R.id.fromOperation);
            TextView toView = convertView.findViewById(R.id.toOperation);

            jobCodeView.setText(distributionSale.getJobCodeString());
            clientJobCodeView.setText(distributionSale.getClientJobCodeString());
            yearView.setText(distributionSale.getYearString());
            productView.setText(distributionSale.getProduct());
            fromView.setText(distributionSale.getFromOperation());
            toView.setText(distributionSale.getToOperation());

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
        String url = AgSimplified.getApiUrl() + "/sessions?auth_token=" + SharedPref.read(SharedPref.Pref.AUTH_TOKEN, null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("nfs", "RESPONSE");
                        Log.i("nfs", response.toString());
                        SharedPref.write(SharedPref.Pref.AUTH_TOKEN, null);

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("nfs", "ERROR");
                Log.e("nfs", error.toString());
                Toast.makeText(MainActivity.this, "Logout failed", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);
    }
}