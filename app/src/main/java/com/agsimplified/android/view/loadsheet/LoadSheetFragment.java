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
import com.agsimplified.android.database.Field;
import com.agsimplified.android.database.StorageInventory;
import com.agsimplified.android.model.LoadSheetDetail;

import java.util.Locale;

public class LoadSheetFragment extends Fragment {
    public static LoadSheetFragment newInstance(LoadSheetDetail loadSheetDetail) {
        if (loadSheetDetail == null) {
            throw new IllegalArgumentException("null loadSheetDetail");
        }

        LoadSheetFragment frag = new LoadSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("loadSheetDetail", loadSheetDetail);
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

            LoadSheetDetail loadSheetDetail = (LoadSheetDetail) args.getSerializable("loadSheetDetail");
            if (loadSheetDetail == null) {
                throw new IllegalStateException("null loadSheetDetail");
            }

            TextView jobCodeView = view.findViewById(R.id.jobCode);
            Integer jobCode = loadSheetDetail.getJobCode();
            jobCodeView.setText(String.format(Locale.getDefault(), "%d", jobCode));

            TextView clientJobCodeView = view.findViewById(R.id.clientJobCode);
            Integer clientJobCode = loadSheetDetail.getClientJobCode();
            clientJobCodeView.setText(String.format(Locale.getDefault(), "%d", clientJobCode));

            TextView yearView = view.findViewById(R.id.year);
            yearView.setText(loadSheetDetail.getYearString());

            TextView productView = view.findViewById(R.id.product);
            productView.setText(loadSheetDetail.getProductName());

            TextView fromView = view.findViewById(R.id.fromOperation);
            Field fromField = loadSheetDetail.getFromField();
            StorageInventory fromStorageInventory = loadSheetDetail.getFromStorageInventory();
            if (fromField != null) {
                fromView.setText(fromField.siteFarmField());
            } else if (fromStorageInventory != null) {
                fromView.setText(fromStorageInventory.siteStorageName());
            } else {
                fromView.setText("");
            }

            TextView toView = view.findViewById(R.id.toOperation);
            Field toField = loadSheetDetail.getToField();
            StorageInventory toStorageInventory = loadSheetDetail.getToStorageInventory();
            if (toField != null) {
                toView.setText(toField.siteFarmField());
            } else if (toStorageInventory != null) {
                toView.setText(toStorageInventory.siteStorageName());
            } else {
                toView.setText("");
            }


            TextView plannedView = view.findViewById(R.id.plannedAmount);
            Double plannedAmount = loadSheetDetail.getAmount();
            plannedView.setText(String.format(Locale.getDefault(), "%.2f", plannedAmount));

//            TextView hauledView = view.findViewById(R.id.hauledAmount);
//            Double hauledAmount = loadSheetDetail.getHauledAmount();
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