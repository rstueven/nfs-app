package com.agsimplified.android.views.distributionsale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;

public class JobSetupFragment extends Fragment {
    public static JobSetupFragment newInstance(DistributionSale ds) {
        if (ds == null) {
            throw new IllegalArgumentException("null ds");
        }

        JobSetupFragment fragment = new JobSetupFragment();
        Bundle args = new Bundle();
        args.putSerializable("ds", ds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_job_setup, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }
            DistributionSale ds = (DistributionSale) args.getSerializable("ds");
            if (ds == null) {
                throw new IllegalStateException("null ds");
            }

        }

        return view;
    }
}
