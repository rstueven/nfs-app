package com.agsimplified.android.view.loadsheet;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.agsimplified.android.R;
import com.agsimplified.android.model.LoadSheetDetail;
import com.agsimplified.android.view.AgSimplifiedActivity;
import com.agsimplified.android.view.DirectionsFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rstueven on 3/20/18.
 * <p>The Load Sheet page</p>
 */

public class LoadSheetActivity extends AgSimplifiedActivity implements DirectionsFragment.Directionable {
    LoadSheetMapFragment loadSheetMapFragment;
    LoadSheetDetailsFragment loadSheetDetailsFragment;
    LoadSheetDetail loadSheetDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "LoadSheetActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_sheet_activity);

        Intent intent = getIntent();
        loadSheetDetail = (LoadSheetDetail) intent.getSerializableExtra("loadSheetDetail");
        if (loadSheetDetail == null) {
            throw new IllegalStateException("null loadSheetDetail");
        }

        LoadSheetFragment loadSheetFragment = LoadSheetFragment.newInstance(loadSheetDetail);
        loadSheetMapFragment = LoadSheetMapFragment.newInstance(loadSheetDetail);
        loadSheetDetailsFragment = LoadSheetDetailsFragment.newInstance(loadSheetDetail);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.loadSheetFrame, loadSheetFragment, "loadSheetDetail")
                .commit();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new LoadSheetPagerAdapter(getSupportFragmentManager(), loadSheetDetail);
        mPager.setAdapter(mPagerAdapter);
    }

    public void addLoad(float amount) {
        Log.d("nfs", "LoadSheetActivity");
        loadSheetDetailsFragment.addLoad(amount);
    }

    @Override
    public LatLng getDestinationLocation() {
        return loadSheetDetail.toLatLng();
    }

    private class LoadSheetPagerAdapter extends FragmentPagerAdapter {
        private final String tabTitles[] = new String[]{"MAP AND DIRECTIONS", "JOB DETAILS"};
        private LoadSheetDetail loadSheet;

        LoadSheetPagerAdapter(FragmentManager fm, LoadSheetDetail loadSheet) {
            super(fm);

            if (loadSheet == null) {
                throw new IllegalStateException("null loadSheet");
            }

            this.loadSheet = loadSheet;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LoadSheetMapFragment.newInstance(loadSheet);
                case 1:
                    return LoadSheetDetailsFragment.newInstance(loadSheet);
                default:
                    throw new IllegalStateException("unknown tab #" + position);
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}