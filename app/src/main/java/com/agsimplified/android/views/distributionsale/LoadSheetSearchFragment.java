package com.agsimplified.android.views.distributionsale;

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

/**
 * Created by rstueven on 2/27/18.
 *
 * Set search parameters.
 */

public class LoadSheetSearchFragment extends DialogFragment {
    Spinner clientSelect;
    Spinner yearSelect;
    EditText jobCodeView;
    EditText clientJobCodeView;
    Spinner fromSelect;
    Spinner toSelect;
    Spinner productSelect;

    public interface LoadSheetSearcher {
        void searchDistributionSales(String client, int year, Integer jobCode, Integer clientJobCode, String fromOperation, String toOperation, String product);
    }

    public LoadSheetSearchFragment() {
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

        String[] clients = new String[] {"Barry Farms", "Bary Kienast", "Bedrock Gravel", "Ben Andersen"};
        clientSelect = view.findViewById(R.id.clientSelect);
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, clients);
        clientSelect.setAdapter(clientAdapter);

        String[] years = new String[] {"2018", "2017", "2016", "2015"};
        yearSelect = view.findViewById(R.id.yearSelect);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, years);
        yearSelect.setAdapter(yearAdapter);

        String[] fromClients = new String[] {"#5 Easy", "Aaron Vorthman", "ABC Testing", "Adam Soyer"};
        fromSelect = view.findViewById(R.id.fromSelect);
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, fromClients);
        fromSelect.setAdapter(fromAdapter);

        String[] toClients = new String[] {"Willson Trucking", "Wilson Island State Recreation Area", "Witt Farms", "Zack Kennedy"};
        toSelect = view.findViewById(R.id.toSelect);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, toClients);
        toSelect.setAdapter(toAdapter);

        String[] products = new String[] {"Swine-Liquid-Nursery, 25lb.", "Swine-Liquid-Grow-finish, 150 lb. (Wet/Dry)", "Swine-Liquid-Grow-finish, 150 lb. (Dry Feed)", "Swine-Liquid-Grow-finish, 150 lb. (Earthen)"};
        productSelect = view.findViewById(R.id.productSelect);
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, products);
        productSelect.setAdapter(productAdapter);


        builder.setView(view)
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String client = clientSelect.getSelectedItem().toString();

                        int year;
                        try {
                            year = Integer.parseInt(yearSelect.getSelectedItem().toString());
                        } catch (NumberFormatException e) {
                            year = -1; // This probably isn't right.
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

                        String fromOperation = fromSelect.getSelectedItem().toString();
                        String toOperation = toSelect.getSelectedItem().toString();
                        String product = productSelect.getSelectedItem().toString();

                        searcher.searchDistributionSales(client, year, jobCode, clientJobCode, fromOperation, toOperation, product);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoadSheetSearchFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}