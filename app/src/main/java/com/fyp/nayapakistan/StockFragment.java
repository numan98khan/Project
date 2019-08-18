package com.fyp.nayapakistan;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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
        viewpageradapter.AddFragment(new StockManager() , "All");
        viewpageradapter.AddFragment(new supplier() , "Supplier");
        viewpageradapter.AddFragment(new Food() , "Food");
        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);
        return view ;
    }
}
