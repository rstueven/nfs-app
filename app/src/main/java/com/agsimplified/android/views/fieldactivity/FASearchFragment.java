package com.agsimplified.android.views.fieldactivity;

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
 * Created by rstueven on 2/28/18.
 *
 * Set search parameters.
 */

public class FASearchFragment extends DialogFragment {
    Spinner clientSelect;
    Spinner yearSelect;
    EditText jobCodeView;
    EditText clientJobCodeView;
    Spinner typeSelect;
    Spinner operationSelect;
    Spinner farmSelect;

    public interface FieldActivitySearcher {
        void searchFieldActivities(String client, int year, Integer jobCode, Integer clientJobCode, String jobType, String operation, String farm);
    }

    public FASearchFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FieldActivitySearcher searcher = (FieldActivitySearcher) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fa_search_fragment, null);

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

        String[] jobTypes = new String[] {"Tillage", "Plant", "Spray", "Fertilizing", "Irrigate", "Other"};
        typeSelect = view.findViewById(R.id.typeSelect);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, jobTypes);
        typeSelect.setAdapter(typeAdapter);

        String[] operations = new String[] {"Tom Kelley", "Wendl Feedlot, Inc.", "Eagle Acres Inc.", "Barry Farms"};
        operationSelect = view.findViewById(R.id.operationSelect);
        ArrayAdapter<String> operationAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, operations);
        operationSelect.setAdapter(operationAdapter);

        String[] farms = new String[] {"Hubbard", "Linda"};
        farmSelect = view.findViewById(R.id.farmSelect);
        ArrayAdapter<String> farmAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.spinner_item, farms);
        farmSelect.setAdapter(farmAdapter);

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

                        String jobType = typeSelect.getSelectedItem().toString();
                        String operation = operationSelect.getSelectedItem().toString();
                        String farm = farmSelect.getSelectedItem().toString();

                        searcher.searchFieldActivities(client, year, jobCode, clientJobCode, jobType, operation, farm);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FASearchFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}