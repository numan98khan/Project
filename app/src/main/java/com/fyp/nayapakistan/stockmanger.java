package com.fyp.nayapakistan;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fyp.nayapakistan.Model.DataBuffer;
import com.fyp.nayapakistan.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class stockmanger extends Fragment implements StockAdapter.ItemListener{

    private RecyclerView recyclerView;
//    private ArrayList<Product> productList = new ArrayList<Product>();;
    private StockAdapter adapter;
    private String TAG = "StockManager";

    public stockmanger()
    {
        // Required empty public constructor
    }

    private DatabaseReference products;


    View view ;
    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    int[] listviewImage = new int[]{
            R.drawable.ic_local_grocery, R.drawable.ic_local_grocery, R.drawable.ic_local_grocery, R.drawable.ic_local_grocery,
            R.drawable.ic_local_grocery, R.drawable.ic_local_grocery, R.drawable.ic_local_grocery, R.drawable.ic_local_grocery,
    };

    String[] listviewShortDescription = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_stockmanger, container, false);

        DataBuffer.productList = new ArrayList<Product>();

        // Implement all the functionality for child events
        products.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.d("DEBUG", "onChildAdded : " + s);

                DataBuffer.productList.add(new Product(dataSnapshot.child("name").getValue(String.class),
                        "itemShortDesc", "itemDetail",
                        "MRP", "discount", "100", "quantity",
                        "imageURL", dataSnapshot.child("name").getValue(String.class)));

                adapter.notifyDataSetChanged();

                // Removing Duplicates
//                productList = new ArrayList<>(new HashSet<>(productList));

                // Do something with the value
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // Log.d("DEBUG", "onChildAdded : " + s);

                DataBuffer.productList.remove(findAndDeleteChild(dataSnapshot.child("name").getValue(String.class)));

                //productList.add(new Product(dataSnapshot.getKey(), dataSnapshot.child("name").getValue(String.class)));

                DataBuffer.productList.add(new Product(dataSnapshot.child("name").getValue(String.class),
                        "itemShortDesc", "itemDetail",
                        "MRP", "discount", "100", "quantity",
                        "imageURL", dataSnapshot.child("name").getValue(String.class)));

                adapter.notifyDataSetChanged();

                // Removing Duplicates
//                productList = new ArrayList<>(new HashSet<>(productList));

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Log.d("DEBUG", "onChildAdded : " + s);

                DataBuffer.productList.remove(new Product(dataSnapshot.child("name").getValue(String.class),
                        "itemShortDesc", "itemDetail",
                        "MRP", "discount", "100", "quantity",
                        "imageURL", dataSnapshot.child("name").getValue(String.class)));

                adapter.notifyDataSetChanged();

                // Removing Duplicates
//                productList = new ArrayList<>(new HashSet<>(productList));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // Don't know any implementation yet might need it later
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        products = FirebaseDatabase.getInstance().getReference("products");

        // This section of code (Even though if you add a database don't change this code)
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        Log.d(TAG, DataBuffer.productList.toString());
        adapter = new StockAdapter(getContext(), DataBuffer.productList, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        // Section ends here



        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_discription", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

//        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), aList, R.layout.listview, from, to);
//        ListView androidListView =  view.findViewById(R.id.list_view);
//        androidListView.setDivider(getContext().getDrawable(R.drawable.divider));
//        androidListView.setAdapter(simpleAdapter);
        return view ;  // Inflate the layout for this fragment
    }

    public int findAndDeleteChild(String productID){
        int count = 0;
        for (Product x: DataBuffer.productList){
            if (productID == x.getProductId()){
//                productList.remove(new Product(x.getId(), x.getProduct()));
                break;
            }
            count++;
        }
        return count;
    }
    @Override
    public void onItemClick(Product item) {
        Toast.makeText(getContext(), item.getProductId() + " is clicked", Toast.LENGTH_SHORT).show();
        // Open New activity where amount and add to cart options can be added
        // Basically a new intent will be initiated here
//        Intent intent = new Intent(getApplicationContext(),
//                ProductDetails.class);
//        intent.putExtra("product", item);
//        startActivity(intent);
    }
}
