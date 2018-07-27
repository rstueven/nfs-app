package com.agsimplified.android.view.distributionsale;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.model.distributionsale.DistributionSale;

import java.util.Locale;

public class DSLoadSheetFragment extends Fragment {
    public static DSLoadSheetFragment newInstance(DistributionSale distributionSale) {
        if (distributionSale == null) {
            throw new IllegalArgumentException("null distributionSale");
        }

        DSLoadSheetFragment frag = new DSLoadSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("distributionSale", distributionSale);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.ds_load_sheet_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }
            DistributionSale distributionSale = (DistributionSale) args.getSerializable("distributionSale");
            if (distributionSale == null) {
                throw new IllegalStateException("null distributionSale");
            }

            TextView jobCodeView = view.findViewById(R.id.jobCode);
            Integer jobCode = distributionSale.getJobCode();
            if (jobCode != null) {
                jobCodeView.setText(String.format(Locale.getDefault(), "%d", jobCode));
            }

            TextView clientJobCodeView = view.findViewById(R.id.clientJobCode);
            Integer clientJobCode = distributionSale.getClientJobCode();
            if (clientJobCode != null) {
                clientJobCodeView.setText(String.format(Locale.getDefault(), "%d", distributionSale.getClientJobCode()));
            }

            TextView yearView = view.findViewById(R.id.year);
            yearView.setText(distributionSale.getYearString());

            TextView productView = view.findViewById(R.id.product);
            productView.setText(distributionSale.getProduct());

            TextView fromView = view.findViewById(R.id.fromOperation);
            fromView.setText(distributionSale.getFromOperation());

            TextView toView = view.findViewById(R.id.toOperation);
            toView.setText(distributionSale.getToOperation());

            TextView plannedView = view.findViewById(R.id.plannedAmount);
            Double plannedAmount = distributionSale.getPlannedAmount();
            if (plannedAmount != null) {
                plannedView.setText(String.format(Locale.getDefault(), "%.2f", distributionSale.getPlannedAmount()));
            }

            TextView hauledView = view.findViewById(R.id.hauledAmount);
            Double hauledAmount = distributionSale.getHauledAmount();
            if (hauledAmount != null) {
                hauledView.setText(String.format(Locale.getDefault(), "%.2f", hauledAmount));
            }

            Button closeButton = view.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        activity.finish();
                    }
                }
            });
        }

        return view;
    }
}