/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vimal.dailyfeed.fragments.WallPaperCategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerWallPaperAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public Fragment fragment = null;
    List<String> tabs = new ArrayList<>();

    public Fragment getCurrentFragment() {
        return fragment;
    }

    public ViewPagerWallPaperAdapter(FragmentManager fm, List<String> tabs) {
        super(fm);
        this.tabs = tabs;
        this.mNumOfTabs = tabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        fragment = WallPaperCategoryFragment.newInstance(position);
        return fragment;

    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

}
