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
 * <p>distributionSale Job details and job sheets</p>
 */

public class DSDetailsFragment extends Fragment {
    DSJobSetupFragment jobSetupFragment;
    DSLoadTotalsFragment loadTotalsFragment;
    DSJobDetailsFragment jobDetailsFragment;

    public static DSDetailsFragment newInstance(DistributionSale distributionSale) {
        Log.d("nfs", "DSDetailsFragment.newInstance()");
        if (distributionSale == null) {
            throw new IllegalArgumentException("null distributionSale");
        }

        DSDetailsFragment frag = new DSDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("distributionSale", distributionSale);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "DSDetailsFragment.onCreateView()");
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.ds_details_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            DistributionSale distributionSale = (DistributionSale) args.getSerializable("distributionSale");
            if (distributionSale == null) {
                throw new IllegalStateException("null distributionSale");
            }

            FragmentManager fm = getChildFragmentManager();

            jobSetupFragment = DSJobSetupFragment.newInstance(distributionSale);

            loadTotalsFragment = DSLoadTotalsFragment.newInstance(distributionSale);

            jobDetailsFragment = DSJobDetailsFragment.newInstance(distributionSale);

            fm.beginTransaction()
                    .add(R.id.jobSetupFrame, jobSetupFragment, "jobSetup")
                    .add(R.id.loadTotalsFrame, loadTotalsFragment, "loadTotals")
                    .add(R.id.jobDetailsFrame, jobDetailsFragment, "jobDetails")
                    .commit();
        }

        return view;
    }

    public void addLoad() {
        Log.d("nfs", "DSDetailsFragment");
        Log.d("nfs", "jobDetailsFragment: " + (jobDetailsFragment == null));

        float amount = jobDetailsFragment.getAmount();
        loadTotalsFragment.addLoad(amount);
    }
}