package com.agsimplified.android.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agsimplified.android.R;

public class DirectionsFragment extends Fragment {
    public static DirectionsFragment newInstance(String directions) {
        DirectionsFragment frag = new DirectionsFragment();
        Bundle args = new Bundle();
        args.putString("directions", directions);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.directions_fragment, container, false);

        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args == null) {
                throw new IllegalStateException("null args");
            }
//            String directions = args.getString("directions");
            String directions = "Total Distance: 52.4 mi<br/>Total Duration: 1 hour 12 mins<br/><br/><br/>Distance: 2.5 mi, time: 6 mins<br/>Head <b>south</b> on <b>Falcon Ave</b> toward <b>170th St</b><br/><br/>Distance: 3.0 mi, time: 6 mins<br/>Turn <b>left</b> onto <b>190th St</b><br/><br/>Distance: 17.4 mi, time: 20 mins<br/>Turn <b>right</b> onto <b>US-71 S</b><br/><br/>Distance: 2.7 mi, time: 3 mins<br/>Turn <b>right</b> to merge onto <b>I-80 W</b><br/><br/>Distance: 0.3 mi, time: 1 min<br/>Take exit <b>57</b> toward <b>Atlantic/Cass County N16</b><br/><br/>Distance: 6.6 mi, time: 8 mins<br/>Turn <b>left</b> onto <b>620th St/Olive St</b><div style='font-size:0.9em'>Continue to follow Olive St</div><br/>Distance: 2.1 mi, time: 4 mins<br/>Turn <b>right</b> onto <b>E 7th St/White Pole Rd</b><div style='font-size:0.9em'>Continue to follow E 7th St</div><br/>Distance: 13.8 mi, time: 14 mins<br/>Continue onto <b>US-6 W/White Pole Rd</b><div style='font-size:0.9em'>Continue to follow US-6 W</div><br/>Distance: 2.0 mi, time: 4 mins<br/>Turn <b>left</b> onto <b>500th St</b><br/><br/>Distance: 1.5 mi, time: 4 mins<br/>Turn <b>left</b> onto <b>Hackberry Rd</b><br/><br/>Distance: 0.3 mi, time: 1 min<br/>Turn <b>right</b> onto <b>515th St</b><br/><br/>Distance: 0.1 mi, time: 1 min<br/>Turn <b>right</b> onto <b>Emmett Ln</b><br/><br/>";

            TextView directionsView = view.findViewById(R.id.directions);
            directionsView.setText(Html.fromHtml(directions));
        }

        return view;
    }
}