package com.fyp.nayapakistan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


import android.support.v7.widget.RecyclerView;


import com.fyp.nayapakistan.Model.Product;
import com.fyp.nayapakistan.R;
import com.fyp.nayapakistan.Util.StoreFilter;
import java.util.ArrayList;

//This is the class used for recyclerView for grid view products
//1. Product.java
//2. AutoFitGridLayoutManager.java
//3. HomeAdapter.java

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> implements Filterable {

    private ArrayList<Product> mValues;
    private Context mContext;
    protected ItemListener mListener;

    StoreFilter filter;

    public StockAdapter(Context context, ArrayList<Product> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    // This our holder class for data
    // Any UI changes to the product list will be implemented here
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;
        private LinearLayout linearLayout;
        private Product item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.listview_item_title);
            imageView = (ImageView) v.findViewById(R.id.listview_image);
            linearLayout = (LinearLayout) v.findViewById(R.id.android_list_view_tutorial_with_example);
        }

        public void setData(Product item) {
            this.item = item;
            textView.setText(item.getProductId());
            imageView.setImageResource(R.drawable.cargo);
            linearLayout.setBackgroundColor(Color.parseColor("#FFFF0000"));
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    public ArrayList<Product> getmValues(){
        return mValues;
    }

    public void setmValues(ArrayList<Product> mValues){
        this.mValues = mValues;
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter = new StoreFilter(mValues,this);
        }

        return filter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setData(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(Product item);
    }
}