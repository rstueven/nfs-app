package com.agsimplified.android.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agsimplified.android.R;

public class DirectionsFragment extends Fragment {
    public static DirectionsFragment newInstance(String directions) {
        DirectionsFragment frag = new DirectionsFragment();
        Bundle args = new Bundle();
        args.putString("directions", directions);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.directions_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            String directions = args.getString("directions");

            TextView directionsView = view.findViewById(R.id.directions);
            directionsView.setText(Html.fromHtml(directions));
        }

        return view;
    }
}