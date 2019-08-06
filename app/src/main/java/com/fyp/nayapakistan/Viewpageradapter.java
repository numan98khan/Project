package com.fyp.nayapakistan;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class Viewpageradapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList  = new ArrayList<>();
    private final List<String> stringList = new ArrayList<>();


    public Viewpageradapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

    public void AddFragment (Fragment fragment , String titles)
    {
        fragmentList.add(fragment);
        stringList.add(titles);
    }
}
