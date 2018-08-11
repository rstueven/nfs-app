package com.agsimplified.android.view.loadsheet;

import android.content.Intent;
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

/**
 * Created by rstueven on 3/20/18.
 * <p>The Load Sheet page</p>
 */

public class LoadSheetActivity extends AgSimplifiedActivity {
    LoadSheetMapFragment loadSheetMapFragment;
    LoadSheetDetailsFragment loadSheetDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nfs", "LoadSheetActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_sheet_activity);

        Intent intent = getIntent();
        LoadSheetDetail loadSheet = (LoadSheetDetail) intent.getSerializableExtra("loadSheet");
        if (loadSheet == null) {
            throw new IllegalStateException("null loadSheet");
        }

        LoadSheetFragment loadSheetFragment = LoadSheetFragment.newInstance(loadSheet);
        loadSheetMapFragment = LoadSheetMapFragment.newInstance(loadSheet);
        loadSheetDetailsFragment = LoadSheetDetailsFragment.newInstance(loadSheet);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.loadSheetFrame, loadSheetFragment, "loadSheet")
                .commit();

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new LoadSheetPagerAdapter(getSupportFragmentManager(), loadSheet);
        mPager.setAdapter(mPagerAdapter);
    }

    public void addLoad(float amount) {
        Log.d("nfs", "LoadSheetActivity");
        loadSheetDetailsFragment.addLoad(amount);
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