package com.agsimplified.android.view.distributionsale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.agsimplified.android.R;
import com.agsimplified.android.model.distributionsale.DistributionSale;

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

        Button addLoadButton = view.findViewById(R.id.addLoadButton);
        addLoadButton.setOnClickListener(addLoadListener);

        return view;
    }

    private View.OnClickListener addLoadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DSDetailsFragment detailsFragment = (DSDetailsFragment)getParentFragment();
            float amount = Float.parseFloat(amountView.getText().toString());
            detailsFragment.addLoad(amount);
        }
    };
}