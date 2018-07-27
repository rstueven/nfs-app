package com.agsimplified.android.view.fieldactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.agsimplified.android.R;
import com.agsimplified.android.model.fieldactivity.FieldActivity;
import com.agsimplified.android.view.AgSimplifiedActivity;

/**
 * Created by rstueven on 3/20/18.
 * <p>The Field Activity page</p>
 */

public class FAActivity extends AgSimplifiedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "FAActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fa_activity);

        Intent intent = getIntent();
        FieldActivity fieldActivity = (FieldActivity) intent.getSerializableExtra("fieldActivity");
        if (fieldActivity == null) {
            throw new IllegalStateException("null fieldActivity");
        }

        FAFragment fieldActivityFragment = FAFragment.newInstance(fieldActivity);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.fieldActivityFrame, fieldActivityFragment, "fieldActivity")
                .commit();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new FAActivity.FAPagerAdapter(getSupportFragmentManager(), fieldActivity);
        mPager.setAdapter(mPagerAdapter);
    }

    private class FAPagerAdapter extends FragmentPagerAdapter {
        private final String tabTitles[] = new String[]{"JOB SETUP", "APPLICATION MAP"};
        private FieldActivity fieldActivity;

        FAPagerAdapter(FragmentManager fm, FieldActivity fieldActivity) {
            super(fm);

            if (fieldActivity == null) {
                throw new IllegalStateException("null fieldActivity");
            }

            this.fieldActivity = fieldActivity;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FADetailsFragment.newInstance(fieldActivity);
                case 1:
                    return FAMapFragment.newInstance(fieldActivity);
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