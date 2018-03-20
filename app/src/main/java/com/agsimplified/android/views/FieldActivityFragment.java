package com.agsimplified.android.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agsimplified.android.R;
import com.agsimplified.android.models.FieldActivity;

public class FieldActivityFragment extends Fragment {
    public static FieldActivityFragment newInstance(FieldActivity fa) {
        if (fa == null) {
            throw new IllegalArgumentException("null fa");
        }

        FieldActivityFragment frag = new FieldActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable("fa", fa);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_field_activity, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }
            FieldActivity fa = (FieldActivity) args.getSerializable("fa");
            if (fa == null) {
                throw new IllegalStateException("null fa");
            }

        }

        return view;
    }
}