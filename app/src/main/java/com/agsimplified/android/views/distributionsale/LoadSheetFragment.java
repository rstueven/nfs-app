package com.agsimplified.android.views.distributionsale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;

import java.util.Locale;

public class LoadSheetFragment extends Fragment {
    public static LoadSheetFragment newInstance(DistributionSale ds) {
        if (ds == null) {
            throw new IllegalArgumentException("null ds");
        }

        LoadSheetFragment frag = new LoadSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("ds", ds);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_load_sheet, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }
            DistributionSale ds = (DistributionSale) args.getSerializable("ds");
            if (ds == null) {
                throw new IllegalStateException("null ds");
            }

            TextView jobCodeView = view.findViewById(R.id.jobCode);
            Integer jobCode = ds.getJobCode();
            if (jobCode != null) {
                jobCodeView.setText(String.format(Locale.getDefault(), "%d", jobCode));
            }

            TextView clientJobCodeView = view.findViewById(R.id.clientJobCode);
            Integer clientJobCode = ds.getClientJobCode();
            if (clientJobCode != null) {
                clientJobCodeView.setText(String.format(Locale.getDefault(), "%d", ds.getClientJobCode()));
            }

            TextView yearView = view.findViewById(R.id.year);
            yearView.setText(ds.getYearString());

            TextView productView = view.findViewById(R.id.product);
            productView.setText(ds.getProduct());

            TextView fromView = view.findViewById(R.id.fromOperation);
            fromView.setText(ds.getFromOperation());

            TextView toView = view.findViewById(R.id.toOperation);
            toView.setText(ds.getToOperation());

            TextView plannedView = view.findViewById(R.id.plannedAmount);
            Double plannedAmount = ds.getPlannedAmount();
            if (plannedAmount != null) {
                plannedView.setText(String.format(Locale.getDefault(), "%.2f", ds.getPlannedAmount()));
            }

            TextView hauledView = view.findViewById(R.id.hauledAmount);
            Double hauledAmount = ds.getHauledAmount();
            if (hauledAmount != null) {
                hauledView.setText(String.format(Locale.getDefault(), "%.2f", hauledAmount));
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