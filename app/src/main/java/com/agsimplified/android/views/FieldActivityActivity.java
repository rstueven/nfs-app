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
import com.agsimplified.android.models.FieldActivity;

/**
 * Created by rstueven on 3/20/18.
 * <p>The Field Activity page</p>
 */

public class FieldActivityActivity extends AgSimplifiedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "FieldActivityActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_activity);

        Intent intent = getIntent();
        FieldActivity fa = (FieldActivity) intent.getSerializableExtra("fa");
        if (fa == null) {
            throw new IllegalStateException("null fa");
        }

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new FieldActivityActivity.FAPagerAdapter(getSupportFragmentManager(), fa);
        mPager.setAdapter(mPagerAdapter);
    }

    private class FAPagerAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;
        private final String tabTitles[] = new String[]{"MAP AND DIRECTIONS", "JOB DETAILS"};
        private FieldActivity fa;

        FAPagerAdapter(FragmentManager fm, FieldActivity fa) {
            super(fm);

            if (fa == null) {
                throw new IllegalStateException("null fa");
            }
            this.fa = fa;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FieldActivityMapFragment.newInstance(fa);
                case 1:
                    return FieldActivityMapFragment.newInstance(fa);
//                    return FieldActivityDetailsFragment.newInstance(fa);
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