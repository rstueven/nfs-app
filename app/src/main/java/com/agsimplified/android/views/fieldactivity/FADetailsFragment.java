package com.agsimplified.android.views.fieldactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.agsimplified.android.R;
import com.agsimplified.android.models.fieldactivity.FieldActivity;

import java.util.Locale;

/**
 * Created by rstueven on 5/13/18.
 * <p>fieldActivity Job details and job sheets</p>
 */

public class FADetailsFragment extends Fragment {
    public static FADetailsFragment newInstance(FieldActivity fieldActivity) {
        if (fieldActivity == null) {
            throw new IllegalArgumentException("null fieldActivity");
        }

        FADetailsFragment frag = new FADetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("fieldActivity", fieldActivity);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fa_details_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }

            FieldActivity fieldActivity = (FieldActivity) args.getSerializable("fieldActivity");
            if (fieldActivity == null) {
                throw new IllegalStateException("null fieldActivity");
            }

            TextView rate = view.findViewById(R.id.rate);
            rate.setText(String.format(Locale.getDefault(), "%.2f", fieldActivity.getRatePerAcre()));

            TextView depth = view.findViewById(R.id.depth);
            depth.setText(String.format(Locale.getDefault(), "%.2f", fieldActivity.getDepth()));

            String[] plannedMethods = new String[] {"Injected Manure", "Broadcast (Incoporation < 24 hours)", "Broadcast (Incoporation > 24 hours)", "Broadcast Liquid Manure no Incorporation", "Broadcast Solid Manure no Incorporation", "Irrigate Manure no Incorporation", "Injected Fertilizer", "Broadcast Dry Fertilizer"};
            Spinner plannedMethodSelect = view.findViewById(R.id.plannedMethod);
            ArrayAdapter<String> plannedMethodAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, plannedMethods);
            plannedMethodSelect.setAdapter(plannedMethodAdapter);

            String[] productTypes = new String[] {"Chemical", "Fertilizer", "Carrier", "Tillage", "Seed", "Ag Lime"};
            Spinner productTypeSelect = view.findViewById(R.id.productType);
            ArrayAdapter<String> productTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, productTypes);
            productTypeSelect.setAdapter(productTypeAdapter);

            String[] fertilizerNames = new String[] {"Swine-Liquid-Nursery, 25lb.", "Swine-Liquid-Grow-finish, 150 lb. (Wet/Dry)", "Swine-Liquid-Grow-finish, 150 lb. (Dry Feed)", "Swine-Liquid-Grow-finish, 150 lb. (Earthen)", "Swine-Liquid-Gestation, 400 lb."};
            Spinner fertilizerNameSelect = view.findViewById(R.id.fertilizerName);
            ArrayAdapter<String> fertilizerNameAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, fertilizerNames);
            fertilizerNameSelect.setAdapter(fertilizerNameAdapter);

            String[] fertilizerStorages = new String[] {"Shawn Jesperson > Marvin-Lyman250a - Solid Manure (Temporary)", "Shawn Jesperson > Dave Lyman S63 - Solid Manure (Temporary)", "Shawn Jesperson > Dave Lyman N35 - Solid Manure (Temporary)", "Shawn Jesperson > Shane - Livestock (Temporary)", "Shawn Jesperson > Brooks - Livestock (Temporary)"};
            Spinner fertilizerStorageSelect = view.findViewById(R.id.fertilizerStorage);
            ArrayAdapter<String> fertilizerStorageAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, fertilizerStorages);
            fertilizerStorageSelect.setAdapter(fertilizerStorageAdapter);

            String[] vehicleOwners = new String[] {"Eagle Acres Inc."};
            Spinner vehicleOwnerSelect = view.findViewById(R.id.vehicleOwner);
            ArrayAdapter<String> vehicleOwnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, vehicleOwners);
            vehicleOwnerSelect.setAdapter(vehicleOwnerAdapter);

            String[] vehicles = new String[] {"Airbus 380"};
            Spinner vehicleSelect = view.findViewById(R.id.vehicle);
            ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, vehicles);
            vehicleSelect.setAdapter(vehicleAdapter);

            String[] implementOwners = new String[] {"Eagle Acres Inc."};
            Spinner implementOwnerSelect = view.findViewById(R.id.implementOwner);
            ArrayAdapter<String> implementOwnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, implementOwners);
            implementOwnerSelect.setAdapter(implementOwnerAdapter);

            String[] implementList = new String[] {"Airbus 380"};
            Spinner implementSelect = view.findViewById(R.id.implement);
            ArrayAdapter<String> implementAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, implementList);
            implementSelect.setAdapter(implementAdapter);
        }

        return view;
    }
}