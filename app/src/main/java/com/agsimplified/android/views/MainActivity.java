package com.agsimplified.android.views;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;
import com.agsimplified.android.models.fieldactivity.FieldActivity;
import com.agsimplified.android.views.distributionsale.DistributionSaleActivity;
import com.agsimplified.android.views.distributionsale.LoadSheetSearchFragment;
import com.agsimplified.android.views.fieldactivity.FieldActivityActivity;
import com.agsimplified.android.views.fieldactivity.FieldActivitySearchFragment;

import java.util.ArrayList;

public class MainActivity extends AgSimplifiedActivity
        implements LoadSheetSearchFragment.LoadSheetSearcher, FieldActivitySearchFragment.FieldActivitySearcher {
    private static ArrayList<DistributionSale> distributionSales = new ArrayList<>();

    static {
        distributionSales.add(new DistributionSale(1, 17263, null, 2018, "Beef-Solid- Open Lot-Pen Scrape", "Natural Fertilizer Products, Inc.:Crossroads Stockpile - E Pen - Solid Manure", "Tom Barry:Moms NE100a", 700.0, 660.06));
        distributionSales.add(new DistributionSale(2, 18014, null, 2018, "Beef-Solid- Open Lot-Pen Scrape", "Natural Fertilizer Products, Inc.:Koke Compost Site - Solid Manure", "Kevin Koke:Tower 144a", 0.0, 0.0));
        distributionSales.add(new DistributionSale(3, 17379, null, 2018, "Dairy-Solid-Veal calves, 250 lb.", "Natural Fertilizer Products, Inc.:Compost Yard at Kirkman Farms Dairy - Solid Manure", "Gabe Hansen:GabeEF_E30a", 0.0, 179.59));
        distributionSales.add(new DistributionSale(4, 17332, null, 2018, "Dairy-Solid-Veal calves, 250 lb.", "Natural Fertilizer Products, Inc.:Compost Yard at Kirkman Farms Dairy - Solid Manure", "Stoberl Farms Ltd:Bin Site E 169ac", 480.0, 616.21));
    }

    private static ArrayList<FieldActivity> fieldActivities = new ArrayList<>();

    static {
        fieldActivities.add(new FieldActivity(2048, 17171, null, 2018, "Fertilizing", "Shawn Jespersen", "Shawn Jespersen", "Grant", "GrantJorg228a", 173.49));
        fieldActivities.add(new FieldActivity(2304, 17293, null, 2018, "Fertilizing", "David Staben", "David Staben", "Home Place", "Home", 57.43));
        fieldActivities.add(new FieldActivity(2560, 18012, null, 2018, "Fertilizing", "Dammann Farms", "Dammann Farms", "Beh Farm", "Beh 578 E6ac", 6.31));
        fieldActivities.add(new FieldActivity(2049, 17171, null, 2018, "Fertilizing", "Shawn Jespersen", "Shawn Jespersen", "Grant", "GrantSmithS78a", 78.14));
    }

    private ListView searchResultsView;
    DistributionSaleAdapter distributionSaleAdapter;
    FieldActivityAdapter fieldActivitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        searchResultsView = findViewById(R.id.searchResultsView);
        distributionSaleAdapter = new DistributionSaleAdapter(this, distributionSales);
        fieldActivitiesAdapter = new FieldActivityAdapter(this, fieldActivities);
    }

    public void showDistributionSaleSearch(View v) {
        DialogFragment newFragment = new LoadSheetSearchFragment();
        newFragment.show(getFragmentManager(), "loadSheetSearch");
    }

    public void searchDistributionSales(String client, int year, Integer jobCode, Integer clientJobCode, String fromOperation, String toOperation, String product) {
        searchResultsView.setAdapter(distributionSaleAdapter);
        searchResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DistributionSale ds = (DistributionSale) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, DistributionSaleActivity.class);
                intent.putExtra("ds", ds);
                startActivity(intent);
            }
        });
    }

    public class DistributionSaleAdapter extends ArrayAdapter<DistributionSale> {
        DistributionSaleAdapter(Context context, ArrayList<DistributionSale> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            DistributionSale ds = getItem(position);
            if (ds == null) {
                throw new IllegalStateException("null ds");
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

            jobCodeView.setText(ds.getJobCodeString());
            clientJobCodeView.setText(ds.getClientJobCodeString());
            yearView.setText(ds.getYearString());
            productView.setText(ds.getProduct());
            fromView.setText(ds.getFromOperation());
            toView.setText(ds.getToOperation());

            return convertView;
        }
    }

    public void showFieldActivitySearch(View v) {
        DialogFragment newFragment = new FieldActivitySearchFragment();
        newFragment.show(getFragmentManager(), "fieldActivitySearch");
    }

    public void searchFieldActivities(String client, int year, Integer jobCode, Integer clientJobCode, String activityType, String operation, String farm) {
        searchResultsView.setAdapter(fieldActivitiesAdapter);
        searchResultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FieldActivity fieldActivity = (FieldActivity) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, FieldActivityActivity.class);
                intent.putExtra("fieldActivity", fieldActivity);
                startActivity(intent);
            }
        });
    }

    public class FieldActivityAdapter extends ArrayAdapter<FieldActivity> {
        FieldActivityAdapter(Context context, ArrayList<FieldActivity> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            FieldActivity fa = getItem(position);
            if (fa == null) {
                throw new IllegalStateException("null fa");
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

            jobCodeView.setText(fa.getJobCodeString());
            clientJobCodeView.setText(fa.getClientJobCodeString());
            yearView.setText(fa.getYearString());
            typeView.setText(fa.getActivityType());
            operationView.setText(fa.getOperation());
            farmView.setText(fa.getFarm());

            return convertView;
        }
    }
}