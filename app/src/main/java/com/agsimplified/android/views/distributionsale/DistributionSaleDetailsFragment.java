package com.agsimplified.android.views.distributionsale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;

/**
 * Created by rstueven on 3/13/18.
 * <p>DS Job details and job sheets</p>
 */

public class DistributionSaleDetailsFragment extends Fragment {
    public static DistributionSaleDetailsFragment newInstance(DistributionSale ds) {
        Log.d("nfs", "DistributionSaleDetailsFragment.newInstance()");
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "DistributionSaleDetailsFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_distribution_sale_details, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            DistributionSale ds = (DistributionSale) args.getSerializable("ds");
            if (ds == null) {
                throw new IllegalStateException("null ds");
            }

            FragmentManager fm = getChildFragmentManager();

            LoadSheetFragment loadSheetFragment = LoadSheetFragment.newInstance(ds);

            JobSetupFragment jobSetupFragment = JobSetupFragment.newInstance(ds);

            LoadTotalsFragment loadTotalsFragment = LoadTotalsFragment.newInstance(ds);

            JobDetailsFragment jobDetailsFragment = JobDetailsFragment.newInstance(ds);

            fm.beginTransaction()
                    .add(R.id.jobSetupFrame, jobSetupFragment, "jobSetup")
                    .add(R.id.loadTotalsFrame, loadTotalsFragment, "loadTotals")
                    .add(R.id.jobDetailsFrame, jobDetailsFragment, "jobDetails")
                    .commit();
        }

        return rootView;
    }
}