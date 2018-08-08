package com.agsimplified.android.view.loadsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.model.LoadSheetDETAIL;

import java.util.Locale;

public class LoadSheetLoadTotalsFragment extends Fragment {
    TextView yourLoadsView;
    TextView yourAmountView;
    TextView totalLoadsView;
    TextView totalAmountView;

    public static LoadSheetLoadTotalsFragment newInstance(LoadSheetDETAIL loadSheet) {
        if (loadSheet == null) {
            throw new IllegalArgumentException("null loadSheet");
        }

        LoadSheetLoadTotalsFragment fragment = new LoadSheetLoadTotalsFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheet", loadSheet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.load_sheet_load_totals_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            LoadSheetDETAIL loadSheet = (LoadSheetDETAIL) args.getSerializable("loadSheet");
            if (loadSheet == null) {
                throw new IllegalStateException("null loadSheet");
            }

            TextView plannedAmount = view.findViewById(R.id.plannedAmount);
            plannedAmount.setText(String.format(Locale.getDefault(), "%.2f", loadSheet.getAmount()));

            yourLoadsView = view.findViewById(R.id.yourLoads);
            yourAmountView = view.findViewById(R.id.yourAmount);
            totalLoadsView = view.findViewById(R.id.totalLoads);
            totalAmountView = view.findViewById(R.id.totalAmount);
        }

        return view;
    }

    public void addLoad(float amount) {
        Log.d("nfs", "LoadSheetDetailsFragment(" + amount + ")");
        int yourLoads = Integer.parseInt(yourLoadsView.getText().toString());
        yourLoads += 1;
        yourLoadsView.setText(String.format(Locale.getDefault(), "%d", yourLoads));

        float yourAmount = Float.parseFloat(yourAmountView.getText().toString());
        yourAmount += amount;
        yourAmountView.setText(String.format(Locale.getDefault(), "%.2f", yourAmount));
        
        int totalLoads = Integer.parseInt(totalLoadsView.getText().toString());
        totalLoads += 1;
        totalLoadsView.setText(String.format(Locale.getDefault(), "%d", totalLoads));

        float totalAmount = Float.parseFloat(totalAmountView.getText().toString());
        totalAmount += amount;
        totalAmountView.setText(String.format(Locale.getDefault(), "%.2f", totalAmount));
    }
}
