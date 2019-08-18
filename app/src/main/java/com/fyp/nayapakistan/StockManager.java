package com.fyp.nayapakistan;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fyp.nayapakistan.Model.DataBuffer;
import com.fyp.nayapakistan.Model.Product;
import com.google.firebase.FirebaseApp;
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
public class StockManager extends Fragment implements StockAdapter.ItemListener{

    private RecyclerView recyclerView;
//    private ArrayList<Product> productList = new ArrayList<Product>();;
    private StockAdapter adapter;
    private String TAG = "StockManager";

    public StockManager()
    {
        // Required empty public constructor
    }

    private DatabaseReference products;


    View view ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        view = inflater.inflate(R.layout.fragment_stockmanger, container, false);

        DataBuffer.productList = new ArrayList<Product>();

        FirebaseApp.initializeApp(getContext());
        products = FirebaseDatabase.getInstance().getReference(DataBuffer.product_tbl);

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
