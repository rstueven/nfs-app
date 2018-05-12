package com.agsimplified.android.views.fieldactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.models.fieldactivity.FieldActivity;

import java.util.Locale;

public class FieldActivityFragment extends Fragment {
    public static FieldActivityFragment newInstance(FieldActivity fieldActivity) {
        if (fieldActivity == null) {
            throw new IllegalArgumentException("null fieldActivity");
        }

        FieldActivityFragment frag = new FieldActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable("fieldActivity", fieldActivity);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_field_activity, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }
            FieldActivity fieldActivity = (FieldActivity) args.getSerializable("fieldActivity");
            if (fieldActivity == null) {
                throw new IllegalStateException("null fieldActivity");
            }

            TextView jobCodeView = view.findViewById(R.id.jobCode);
            Integer jobCode = fieldActivity.getJobCode();
            if (jobCode != null) {
                jobCodeView.setText(String.format(Locale.getDefault(), "%d", jobCode));
            }

            TextView clientJobCodeView = view.findViewById(R.id.clientJobCode);
            TextView clientJobCodeLabel = view.findViewById(R.id.clientJobCodeLabel);
            Integer clientJobCode = fieldActivity.getClientJobCode();
            if (clientJobCode != null) {
                clientJobCodeLabel.setVisibility(View.VISIBLE);
                clientJobCodeView.setVisibility(View.VISIBLE);
                clientJobCodeView.setText(String.format(Locale.getDefault(), "%d", fieldActivity.getClientJobCode()));
            } else {
                clientJobCodeLabel.setVisibility(View.GONE);
                clientJobCodeView.setVisibility(View.GONE);
            }

            TextView yearView = view.findViewById(R.id.year);
            yearView.setText(fieldActivity.getYearString());

            TextView activityTypeView = view.findViewById(R.id.activityType);
            String activityType = fieldActivity.getActivityType();
            if (activityType != null) {
                activityTypeView.setText(activityType);
            }

            TextView clientView = view.findViewById(R.id.client);
            String client = fieldActivity.getClient();
            if (client != null) {
                clientView.setText(client);
            }

            TextView farmView = view.findViewById(R.id.farm);
            String farm = fieldActivity.getFarm();
            if (farm != null) {
                farmView.setText(farm);
            }

            TextView fieldView = view.findViewById(R.id.field);
            String field = fieldActivity.getField();
            if (field != null) {
                fieldView.setText(field);
            }

            TextView plannedView = view.findViewById(R.id.acresPlanned);
            Double acresPlanned = fieldActivity.getAcresPlanned();
            if (acresPlanned != null) {
                plannedView.setText(String.format(Locale.getDefault(), "%.2f", acresPlanned));
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