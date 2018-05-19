package com.agsimplified.android.views.fieldactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.models.fieldactivity.FieldActivity;

import java.util.Locale;

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

            TextView rate = view.findViewById(R.id.rate);
            rate.setText(String.format(Locale.getDefault(), "%.2f", fieldActivity.getRatePerAcre()));

            TextView depth = view.findViewById(R.id.depth);
            depth.setText(String.format(Locale.getDefault(), "%.2f", fieldActivity.getDepth()));
        }

        return view;
    }
}