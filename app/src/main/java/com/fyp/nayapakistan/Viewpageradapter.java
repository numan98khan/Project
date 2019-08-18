package com.fyp.nayapakistan;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
