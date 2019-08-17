package com.fyp.nayapakistan.Util;

import android.widget.Filter;

import com.fyp.nayapakistan.Model.Product;
import com.fyp.nayapakistan.StockAdapter;

import java.util.ArrayList;

public class StoreFilter extends Filter {

    StockAdapter adapter;
    ArrayList<Product> filterList;

    public StoreFilter(ArrayList<Product> filterList,StockAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Product> filteredPlayers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getProductId().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.setmValues((ArrayList<Product>) results.values);

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}