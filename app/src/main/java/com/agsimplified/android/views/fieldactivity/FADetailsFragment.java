package com.agsimplified.android.views.fieldactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.models.fieldactivity.FieldActivity;

/**
 * Created by rstueven on 5/13/18.
 * <p>FA Job details and job sheets</p>
 */

public class FADetailsFragment extends Fragment {
    public static FADetailsFragment newInstance(FieldActivity fieldActivity) {
        if (fieldActivity == null) {
            throw new IllegalArgumentException("null fieldActivity");
        }

        FADetailsFragment frag = new FADetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("fieldActivity", fieldActivity);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fa_details_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            FieldActivity fieldActivity = (FieldActivity) args.getSerializable("fieldActivity");
            if (fieldActivity == null) {
                throw new IllegalStateException("null fieldActivity");
            }

            FragmentManager fm = getChildFragmentManager();

            FAJobSetupFragment jobSetupFragment = FAJobSetupFragment.newInstance(fieldActivity);

            fm.beginTransaction()
                    .add(R.id.jobSetupFrame, jobSetupFragment, "jobSetup")
                    .commit();
        }

        return view;
    }
}