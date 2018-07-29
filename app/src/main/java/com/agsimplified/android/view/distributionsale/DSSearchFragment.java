package com.agsimplified.android.view.distributionsale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.agsimplified.android.R;
import com.agsimplified.android.database.Client;
import com.agsimplified.android.database.Product;
import com.agsimplified.android.database.Site;
import com.agsimplified.android.model.LoadSheet;

import java.util.List;

/**
 * Created by rstueven on 2/27/18.
 *
 * Set search parameters.
 */

public class DSSearchFragment extends DialogFragment {
    private Spinner clientSelect;
    private Spinner yearSelect;
    private EditText jobCodeView;
    private EditText clientJobCodeView;
    private Spinner fromSelect;
    private Spinner toSelect;
    private Spinner productSelect;

    public interface LoadSheetSearcher {
        void searchDistributionSales(Integer client, Integer year, Integer jobCode, Integer clientJobCode, Integer fromId, Integer toId, Integer productId);
    }

    public DSSearchFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LoadSheetSearcher searcher = (LoadSheetSearcher) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.ds_search_fragment, null);

        jobCodeView = view.findViewById(R.id.jobCode);
        clientJobCodeView = view.findViewById(R.id.clientJobCode);

        List<Client> clients = Client.all();
        clientSelect = view.findViewById(R.id.clientSelect);
        ArrayAdapter<Client> clientAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, clients);
        clientSelect.setAdapter(clientAdapter);

        List<String> years = LoadSheet.allYears();
        yearSelect = view.findViewById(R.id.yearSelect);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, years);
        yearSelect.setAdapter(yearAdapter);

        List<Site> fromSites = Site.all();
        fromSelect = view.findViewById(R.id.fromSelect);
        ArrayAdapter<Site> fromAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, fromSites);
        fromSelect.setAdapter(fromAdapter);

        List<Site> toSites = Site.all();
        toSelect = view.findViewById(R.id.toSelect);
        ArrayAdapter<Site> toAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, toSites);
        toSelect.setAdapter(toAdapter);

        List<Product> products = Product.all();
        productSelect = view.findViewById(R.id.productSelect);
        ArrayAdapter<Product> productAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, products);
        productSelect.setAdapter(productAdapter);


        builder.setView(view)
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int clientId = ((Client)clientSelect.getSelectedItem()).getId();

                        Integer year;
                        try {
                            year = Integer.parseInt(yearSelect.getSelectedItem().toString());
                        } catch (NumberFormatException e) {
                            year = null;
                        }

                        Integer jobCode;
                        try {
                            jobCode = Integer.parseInt(jobCodeView.getText().toString());
                        } catch (NumberFormatException e) {
                            jobCode = null;
                        }

                        Integer clientJobCode;
                        try {
                            clientJobCode = Integer.parseInt(clientJobCodeView.getText().toString());
                        } catch (NumberFormatException e) {
                            clientJobCode = null;
                        }

                        Integer fromId;
                        try {
                            fromId = Integer.parseInt(fromSelect.getSelectedItem().toString());
                        } catch (NumberFormatException e) {
                            fromId = null;
                        }

                        Integer toId;
                        try {
                            toId = Integer.parseInt(toSelect.getSelectedItem().toString());
                        } catch (NumberFormatException e) {
                            toId = null;
                        }

                        Integer productId;
                        try {
                            productId = Integer.parseInt(productSelect.getSelectedItem().toString());
                        } catch (NumberFormatException e) {
                            productId = null;
                        }

                        searcher.searchDistributionSales(clientId, year, jobCode, clientJobCode, fromId, toId, productId);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DSSearchFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}