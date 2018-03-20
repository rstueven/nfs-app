package com.agsimplified.android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.agsimplified.android.R;
import com.agsimplified.android.models.DistributionSale;

public class DistributionSaleActivity extends AgSimplifiedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "DistributionSaleActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution_sale);

        Intent intent = getIntent();
        DistributionSale ds = (DistributionSale) intent.getSerializableExtra("ds");
        if (ds == null) {
            throw new IllegalStateException("null ds");
        }

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
                    return DistributionSaleMapFragment.newInstance(ds);
                case 1:
                    return DistributionSaleDetailsFragment.newInstance(ds);
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