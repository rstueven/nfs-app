package com.agsimplified.android.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.models.FieldActivity;

import java.util.Locale;

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

            TextView jobCodeView = view.findViewById(R.id.jobCode);
            Integer jobCode = fa.getJobCode();
            if (jobCode != null) {
                jobCodeView.setText(String.format(Locale.getDefault(), "%d", jobCode));
            }

            TextView clientJobCodeView = view.findViewById(R.id.clientJobCode);
            Integer clientJobCode = fa.getClientJobCode();
            if (clientJobCode != null) {
                clientJobCodeView.setText(String.format(Locale.getDefault(), "%d", fa.getClientJobCode()));
            }

            TextView yearView = view.findViewById(R.id.year);
            yearView.setText(fa.getYearString());

            TextView locationView = view.findViewById(R.id.location);
            locationView.setText(fa.getLocation());

            TextView fromView = view.findViewById(R.id.jobType);
            fromView.setText(fa.getActivityType());

            TextView plannedView = view.findViewById(R.id.acresPlanned);
            Double acresPlanned = fa.getAcresPlanned();
            if (acresPlanned != null) {
                plannedView.setText(String.format(Locale.getDefault(), "%.2f", acresPlanned));
            }
            
            TextView appliedView = view.findViewById(R.id.acresApplied);
            Double acresApplied = fa.getAcresApplied();
            if (acresApplied != null) {
                appliedView.setText(String.format(Locale.getDefault(), "%.2f", acresApplied));
            }
            
            Button closeButton = view.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

        }

        return view;
    }
}