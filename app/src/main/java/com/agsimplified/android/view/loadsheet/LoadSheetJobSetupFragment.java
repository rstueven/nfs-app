package com.agsimplified.android.view.loadsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.agsimplified.android.R;
import com.agsimplified.android.model.LoadSheet;

public class LoadSheetJobSetupFragment extends Fragment {
    public static LoadSheetJobSetupFragment newInstance(LoadSheet loadSheet) {
        if (loadSheet == null) {
            throw new IllegalArgumentException("null loadSheet");
        }

        LoadSheetJobSetupFragment fragment = new LoadSheetJobSetupFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheet", loadSheet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.load_sheet_job_setup_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            LoadSheet loadSheet = (LoadSheet) args.getSerializable("loadSheet");
            if (loadSheet == null) {
                throw new IllegalStateException("null loadSheet");
            }

            String[] loaderOperators = new String[] {"Natural Fertilizer Products Inc.", "Performance Grading"};
            Spinner loaderOperatorSelect = view.findViewById(R.id.loaderOperator);
            ArrayAdapter<String> loaderOperatorAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, loaderOperators);
            loaderOperatorSelect.setAdapter(loaderOperatorAdapter);

            String[] loadingCompanys = new String[] {"Natural Fertilizer Products Inc.", "Performance Grading"};
            Spinner loadingCompanySelect = view.findViewById(R.id.loadingCompany);
            ArrayAdapter<String> loadingCompanyAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, loadingCompanys);
            loadingCompanySelect.setAdapter(loadingCompanyAdapter);

            String[] truckingCompanys = new String[] {"Natural Fertilizer Products Inc.", "Performance Grading"};
            Spinner truckingCompanySelect = view.findViewById(R.id.truckingCompany);
            ArrayAdapter<String> truckingCompanyAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, truckingCompanys);
            truckingCompanySelect.setAdapter(truckingCompanyAdapter);

            String[] fromInventorys = new String[] {"Natural Fertilizer Products, Inc. > Pisgah Pig - Solid Manure", "Natural Fertilizer Products, Inc. > Compost Site at Gary Hall  - Solid Manure", "Natural Fertilizer Products, Inc. > Compost Site at Calf Ranch - Solid Manure", "Natural Fertilizer Products, Inc. > Koke Compost Site - Solid Manure", "Natural Fertilizer Products, Inc. > Compost Site_Barry Farms - Solid Manure", "Natural Fertilizer Products, Inc. > Crossroads Stockpile - E Pen - Solid Manure", "Natural Fertilizer Products, Inc. > Crossroads Stockpile - T Pen - Solid Manure"};
            Spinner fromInventorySelect = view.findViewById(R.id.fromStorageInventory);
            ArrayAdapter<String> fromInventoryAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, fromInventorys);
            fromInventorySelect.setAdapter(fromInventoryAdapter);
        }

        return view;
    }
}