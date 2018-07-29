package com.agsimplified.android.view.distributionsale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
        clients.add(0, null);
        clientSelect = view.findViewById(R.id.clientSelect);
        ArrayAdapter<Client> clientAdapter = new ClientSpinnerAdapter(getActivity(), R.layout.spinner_item, clients);
        clientSelect.setAdapter(clientAdapter);

        List<String> years = LoadSheet.allYears();
//        years.add(0, null);
        yearSelect = view.findViewById(R.id.yearSelect);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, years);
        yearSelect.setAdapter(yearAdapter);

        List<Site> fromSites = Site.all();
        fromSites.add(0, null);
        fromSelect = view.findViewById(R.id.fromSelect);
        ArrayAdapter<Site> fromAdapter = new SiteSpinnerAdapter(getActivity(), R.layout.spinner_item, fromSites);
        fromSelect.setAdapter(fromAdapter);

        List<Site> toSites = Site.all();
        toSites.add(0, null);
        toSelect = view.findViewById(R.id.toSelect);
        ArrayAdapter<Site> toAdapter = new SiteSpinnerAdapter(getActivity(), R.layout.spinner_item, toSites);
        toSelect.setAdapter(toAdapter);

        List<Product> products = Product.all();
        products.add(0, null);
        productSelect = view.findViewById(R.id.productSelect);
        ArrayAdapter<Product> productAdapter = new ProductSpinnerAdapter(getActivity(), R.layout.spinner_item, products);
        productSelect.setAdapter(productAdapter);


        builder.setView(view)
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Client client = ((Client)clientSelect.getSelectedItem());
                        Integer clientId = (client == null) ? null : client.getId();

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

                        Site fromSite = ((Site)fromSelect.getSelectedItem());
                        Integer fromId = (fromSite == null) ? null : fromSite.getId();

                        Site toSite = ((Site)toSelect.getSelectedItem());
                        Integer toId = (toSite == null) ? null : toSite.getId();

                        Product product = ((Product)productSelect.getSelectedItem());
                        Integer productId = (product == null) ? null : product.getId();

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

    class ClientSpinnerAdapter extends ArrayAdapter<Client> {
        private Context context;
        private List<Client> items;

        ClientSpinnerAdapter(@NonNull Context context, int textViewResourceId, List<Client> items) {
            super(context, textViewResourceId, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Client getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (getItem(position) == null) {
                return new TextView(context);
            } else {
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                label.setText(items.get(position).getName());
                return label;
            }
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            if (getItem(position) == null) {
                return new TextView(context);
            } else {
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                label.setText(items.get(position).getName());
                return label;
            }
        }
    }

    class SiteSpinnerAdapter extends ArrayAdapter<Site> {
        private Context context;
        private List<Site> items;

        SiteSpinnerAdapter(@NonNull Context context, int textViewResourceId, List<Site> items) {
            super(context, textViewResourceId, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Site getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (getItem(position) == null) {
                return new TextView(context);
            } else {
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                label.setText(items.get(position).getName());
                return label;
            }
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            if (getItem(position) == null) {
                return new TextView(context);
            } else {
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                label.setText(items.get(position).getName());
                return label;
            }
        }
    }

    class ProductSpinnerAdapter extends ArrayAdapter<Product> {
        private Context context;
        private List<Product> items;

        ProductSpinnerAdapter(@NonNull Context context, int textViewResourceId, List<Product> items) {
            super(context, textViewResourceId, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Product getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (getItem(position) == null) {
                return new TextView(context);
            } else {
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                label.setText(items.get(position).getName());
                return label;
            }
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            if (getItem(position) == null) {
                return new TextView(context);
            } else {
                TextView label = (TextView) super.getView(position, convertView, parent);
                label.setTextColor(Color.BLACK);
                label.setText(items.get(position).getName());
                return label;
            }
        }
    }
}