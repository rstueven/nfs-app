package com.agsimplified.android.views.distributionsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.agsimplified.android.R;
import com.agsimplified.android.models.distributionsale.DistributionSale;
import com.agsimplified.android.views.AgSimplifiedActivity;

/**
 * Created by rstueven on 3/20/18.
 * <p>The Load Sheet page</p>
 */

public class DSActivity extends AgSimplifiedActivity {
    DSMapFragment dsMapFragment;
    DSDetailsFragment dsDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "DSActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_activity);

        Intent intent = getIntent();
        DistributionSale distributionSale = (DistributionSale) intent.getSerializableExtra("distributionSale");
        if (distributionSale == null) {
            throw new IllegalStateException("null distributionSale");
        }

        DSLoadSheetFragment loadSheetFragment = DSLoadSheetFragment.newInstance(distributionSale);
        dsMapFragment = DSMapFragment.newInstance(distributionSale);
        dsDetailsFragment = DSDetailsFragment.newInstance(distributionSale);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.loadSheetFrame, loadSheetFragment, "loadSheet")
                .commit();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new DSPagerAdapter(getSupportFragmentManager(), distributionSale);
        mPager.setAdapter(mPagerAdapter);
    }

    public void addLoad(float amount) {
        Log.d("nfs", "DSActivity");
        dsDetailsFragment.addLoad(amount);
    }

    private class DSPagerAdapter extends FragmentPagerAdapter {
        private final String tabTitles[] = new String[]{"MAP AND DIRECTIONS", "JOB DETAILS"};
        private DistributionSale distributionSale;

        DSPagerAdapter(FragmentManager fm, DistributionSale distributionSale) {
            super(fm);

            if (distributionSale == null) {
                throw new IllegalStateException("null distributionSale");
            }

            this.distributionSale = distributionSale;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DSMapFragment.newInstance(distributionSale);
                case 1:
                    return DSDetailsFragment.newInstance(distributionSale);
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