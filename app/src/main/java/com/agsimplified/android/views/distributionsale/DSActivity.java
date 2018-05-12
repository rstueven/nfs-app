package com.agsimplified.android.views.distributionsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;
import com.agsimplified.android.views.AgSimplifiedActivity;

public class DSActivity extends AgSimplifiedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "DSActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_activity);

        Intent intent = getIntent();
        DistributionSale ds = (DistributionSale) intent.getSerializableExtra("ds");
        if (ds == null) {
            throw new IllegalStateException("null ds");
        }

        DSLoadSheetFragment loadSheetFragment = DSLoadSheetFragment.newInstance(ds);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.loadSheetFrame, loadSheetFragment, "loadSheet")
                .commit();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new DSPagerAdapter(getSupportFragmentManager(), ds);
        mPager.setAdapter(mPagerAdapter);
    }

    private class DSPagerAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;
        private final String tabTitles[] = new String[]{"MAP AND DIRECTIONS", "JOB DETAILS"};
        private DistributionSale ds;

        DSPagerAdapter(FragmentManager fm, DistributionSale ds) {
            super(fm);

            if (ds == null) {
                throw new IllegalStateException("null ds");
            }

            this.ds = ds;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DSMapFragment.newInstance(ds);
                case 1:
                    return DSDetailsFragment.newInstance(ds);
                default:
                    throw new IllegalStateException("unknown tab #" + position);
            }
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}