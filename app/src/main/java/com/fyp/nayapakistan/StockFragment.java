package com.fyp.nayapakistan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class StockFragment extends Fragment {

    View view ;

    public StockFragment() {
        // Required empty public constructor
    }
    private TabLayout tabLayout ;
    private ViewPager viewPager ;
    private Viewpageradapter viewpageradapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_stock, container, false);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        viewpageradapter = new Viewpageradapter(getFragmentManager());
        viewpageradapter.AddFragment(new stockmanger() , "All");
        viewpageradapter.AddFragment(new supplier() , "Supplier");
        viewpageradapter.AddFragment(new Food() , "Food");
        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);
        return view ;
    }
}
