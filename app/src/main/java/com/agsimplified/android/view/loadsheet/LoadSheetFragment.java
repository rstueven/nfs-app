package com.agsimplified.android.view.loadsheet;

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
import com.agsimplified.android.model.LoadSheet;

import java.util.Locale;

public class LoadSheetFragment extends Fragment {
    public static LoadSheetFragment newInstance(LoadSheet loadSheet) {
        if (loadSheet == null) {
            throw new IllegalArgumentException("null loadSheet");
        }

        LoadSheetFragment frag = new LoadSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheet", loadSheet);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.load_sheet_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }


            LoadSheet loadSheet = (LoadSheet) args.getSerializable("loadSheet");
            if (loadSheet == null) {
                throw new IllegalStateException("null loadSheet");
            }

            TextView jobCodeView = view.findViewById(R.id.jobCode);
            Integer jobCode = loadSheet.getJobCode();
//            if (jobCode != null) {
                jobCodeView.setText(String.format(Locale.getDefault(), "%d", jobCode));
//            }

            TextView clientJobCodeView = view.findViewById(R.id.clientJobCode);
            Integer clientJobCode = loadSheet.getClientJobCode();
//            if (clientJobCode != null) {
                clientJobCodeView.setText(String.format(Locale.getDefault(), "%d", loadSheet.getClientJobCode()));
//            }

            TextView yearView = view.findViewById(R.id.year);
            yearView.setText(loadSheet.getYearString());

            TextView productView = view.findViewById(R.id.product);
            productView.setText(loadSheet.getProductName());

            TextView fromView = view.findViewById(R.id.fromOperation);
            fromView.setText(loadSheet.getFromSite());

            TextView toView = view.findViewById(R.id.toOperation);
            toView.setText(loadSheet.getToSite());

            TextView plannedView = view.findViewById(R.id.plannedAmount);
            Double plannedAmount = loadSheet.getAmount();
//            if (plannedAmount != null) {
                plannedView.setText(String.format(Locale.getDefault(), "%.2f", loadSheet.getAmount()));
//            }

//            TextView hauledView = view.findViewById(R.id.hauledAmount);
//            Double hauledAmount = loadSheet.getHauledAmount();
//            if (hauledAmount != null) {
//                hauledView.setText(String.format(Locale.getDefault(), "%.2f", hauledAmount));
//            }

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