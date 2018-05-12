package com.agsimplified.android.views.fieldactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.agsimplified.android.R;
import com.agsimplified.android.models.fieldactivity.FieldActivity;
import com.agsimplified.android.views.AgSimplifiedActivity;

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
        private static final int TAB_COUNT = 1;
        private final String tabTitles[] = new String[]{"JOB DETAILS"};
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