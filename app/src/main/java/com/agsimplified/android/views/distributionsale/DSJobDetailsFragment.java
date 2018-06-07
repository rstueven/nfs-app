package com.agsimplified.android.views.distributionsale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;

public class DSJobDetailsFragment extends Fragment {
    EditText timeView;
    EditText amountView;
    EditText grossView;
    EditText tareView;
    EditText scaleTicketView;

    public static DSJobDetailsFragment newInstance(DistributionSale distributionSale) {
        if (distributionSale == null) {
            throw new IllegalArgumentException("null distributionSale");
        }

        DSJobDetailsFragment fragment = new DSJobDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("distributionSale", distributionSale);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.ds_job_details_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            DistributionSale distributionSale = (DistributionSale) args.getSerializable("distributionSale");
            if (distributionSale == null) {
                throw new IllegalStateException("null distributionSale");
            }

        }

        timeView = view.findViewById(R.id.time);
        amountView = view.findViewById(R.id.amount);
        grossView = view.findViewById(R.id.gross);
        tareView = view.findViewById(R.id.tare);
        scaleTicketView = view.findViewById(R.id.scaleTicket);

        return view;
    }

    public int getTime() { // TODO: int for now, DateTime later
        return Integer.parseInt(timeView.getText().toString());
    }

    public float getAmount() {
        return Float.parseFloat(amountView.getText().toString());
    }

    public float getGross() {
        return Float.parseFloat(grossView.getText().toString());
    }

    public float getTare() {
        return Float.parseFloat(tareView.getText().toString());
    }

    public String getScaleTicket() {
        return scaleTicketView.getText().toString();
    }
}