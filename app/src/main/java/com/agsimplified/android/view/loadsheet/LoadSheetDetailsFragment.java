package com.agsimplified.android.view.loadsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.model.LoadSheet;

/**
 * Created by rstueven on 3/13/18.
 * <p>distributionSale Job details and job sheets</p>
 */

public class LoadSheetDetailsFragment extends Fragment {
    LoadSheetJobSetupFragment jobSetupFragment;
    LoadSheetLoadTotalsFragment loadTotalsFragment;
    LoadSheetJobDetailsFragment jobDetailsFragment;

    public static LoadSheetDetailsFragment newInstance(LoadSheet loadSheet) {
        Log.d("nfs", "LoadSheetDetailsFragment.newInstance()");
        if (loadSheet == null) {
            throw new IllegalArgumentException("null loadSheet");
        }

        LoadSheetDetailsFragment frag = new LoadSheetDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheet", loadSheet);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("nfs", "LoadSheetDetailsFragment.onCreateView()");
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.load_sheet_details_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            LoadSheet loadSheet = (LoadSheet) args.getSerializable("loadSheet");
            if (loadSheet == null) {
                throw new IllegalStateException("null loadSheet");
            }

            FragmentManager fm = getChildFragmentManager();

            jobSetupFragment = LoadSheetJobSetupFragment.newInstance(loadSheet);

            loadTotalsFragment = LoadSheetLoadTotalsFragment.newInstance(loadSheet);

            jobDetailsFragment = LoadSheetJobDetailsFragment.newInstance(loadSheet);

            fm.beginTransaction()
                    .add(R.id.jobSetupFrame, jobSetupFragment, "jobSetup")
                    .add(R.id.loadTotalsFrame, loadTotalsFragment, "loadTotals")
                    .add(R.id.jobDetailsFrame, jobDetailsFragment, "jobDetails")
                    .commit();
        }

        return view;
    }

    public void addLoad(float amount) {
        loadTotalsFragment.addLoad(amount);
    }
}