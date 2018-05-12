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

public class DSDetailsFragment extends Fragment {
    public static DSDetailsFragment newInstance(DistributionSale ds) {
        Log.d("nfs", "DSDetailsFragment.newInstance()");
        if (ds == null) {
            throw new IllegalArgumentException("null ds");
        }

        DSDetailsFragment frag = new DSDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("ds", ds);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "DSDetailsFragment.onCreateView()");
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ds_details_fragment, container, false);

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

            DSLoadSheetFragment loadSheetFragment = DSLoadSheetFragment.newInstance(ds);

            DSJobSetupFragment jobSetupFragment = DSJobSetupFragment.newInstance(ds);

            DSLoadTotalsFragment loadTotalsFragment = DSLoadTotalsFragment.newInstance(ds);

            DSJobDetailsFragment jobDetailsFragment = DSJobDetailsFragment.newInstance(ds);

            fm.beginTransaction()
                    .add(R.id.jobSetupFrame, jobSetupFragment, "jobSetup")
                    .add(R.id.loadTotalsFrame, loadTotalsFragment, "loadTotals")
                    .add(R.id.jobDetailsFrame, jobDetailsFragment, "jobDetails")
                    .commit();
        }

        return rootView;
    }
}