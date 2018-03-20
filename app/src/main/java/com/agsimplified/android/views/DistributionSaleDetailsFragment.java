package com.agsimplified.android.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.models.DistributionSale;

/**
 * Created by rstueven on 3/13/18.
 * <p>DS Job details and job sheets</p>
 */

public class DistributionSaleDetailsFragment extends Fragment {
    public static DistributionSaleDetailsFragment newInstance(DistributionSale ds) {
        if (ds == null) {
            throw new IllegalArgumentException("null ds");
        }

        DistributionSaleDetailsFragment frag = new DistributionSaleDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("ds", ds);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalStateException("null args");
        }
        DistributionSale ds = (DistributionSale) args.getSerializable("ds");
        if (ds == null) {
            throw new IllegalStateException("null ds");
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_distribution_sale_details, container, false);

        return rootView;
    }
}