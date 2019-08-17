package com.fyp.nayapakistan.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.fyp.nayapakistan.Interfaces.IFCMService;
import com.fyp.nayapakistan.Interfaces.googleAPIInterface;
import com.fyp.nayapakistan.Messages.Errors;
import com.fyp.nayapakistan.Messages.Message;
import com.fyp.nayapakistan.Messages.Messages;
import com.fyp.nayapakistan.Retrofit.GoogleMapsAPI;
import com.fyp.nayapakistan.Retrofit.IFCMClient;
import com.fyp.nayapakistan.Retrofit.IGoogleAPI;
import com.fyp.nayapakistan.Retrofit.RetrofitClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.fyp.nayapakistan.Activities.OrderProduct;
//import com.example.iramml.clientapp.Database.DatabaseHelper;

public class DataBuffer {
    public static final String driver_tbl="Drivers";
    public static final String user_driver_tbl="DriversInformation";
    public static final String history_rider = "RiderHistory";
    public static final String user_rider_tbl="RidersInformation";
    public static final String pickup_request_tbl="PickupRequest";
    public static final String CHANNEL_ID_ARRIVED="ARRIVED";
    public static String token_tbl="Tokens";
    public static String product_tbl="products";
    public static String supplier_tbl="suppliers";
    public static String rate_detail_tbl="RateDetails";
    public static final int PICK_IMAGE_REQUEST = 9999;

    public static User currentUser=new User();
    public static String userID;

    public static boolean driverFound=false;
    public static String driverID="";
    public static LatLng currenLocation;

    public static final String fcmURL="https://fcm.googleapis.com/";
    public static final String googleAPIUrl="https://maps.googleapis.com";

    private static double baseFare=2.55;
    private static double timeRate=0.35;
    private static double distanceRate=1.75;

    public static final String baseURL="https://maps.googleapis.com";

    public static Double pickLat;
    public static Double pickLng;
    public static Double desLat;
    public static Double desLng;
    public static Place pickupPlace, destPlace;

    // The list of products which we will
    public static ArrayList<Product> productList;
    public static ArrayList<Product> listOfProductsFirst;
    public static boolean isFirstTime;
    public static RecyclerView recyclerView;

    // Products database reference
    public static DatabaseReference products;

    // Temporary cart made with ArrayList
    public static ArrayList<Product> productCart;

    public static double getPrice(double km, int min){
        return (baseFare+(timeRate*min)+(distanceRate*km));
    }

    public static IFCMService getFCMService(){
        return IFCMClient.getClient(fcmURL).create(IFCMService.class);
    }

    public static void sendRequestToDriver(final String driverID, final IFCMService mService, final Context context, final LatLng lastLocation) {
//    public static void sendRequestToDriver(final String driverID, final IFCMService mService, final Context context) {
        DatabaseReference tokens= FirebaseDatabase.getInstance().getReference(DataBuffer.token_tbl);
        Log.d("DEBUG", "json_pickup : ");
        tokens.orderByKey().equalTo(driverID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot:dataSnapshot.getChildren()){

                    Log.d("DEBUG", "onDataChange");

                    Token token=postSnapShot.getValue(Token.class);

                    // Create the pickup Object
                    Pickup pickup=new Pickup(pickupPlace, destPlace, userID, token, pickupPlace.getLatLng().latitude,
                            pickupPlace.getLatLng().longitude, destPlace.getLatLng().latitude, destPlace.getLatLng().longitude  );

                    Gson gson = new Gson();
                    String dataString = gson.toJson(pickup);

                    Log.d("DEBUG", "dataObject : " + dataString);

                    final String riderToken;

                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.d("DEBUG", "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    // Get new Instance ID token
                                    String token = task.getResult().getToken();

                                    // Log and toast
                                    Log.d("DEBUG", "msg token : " + token);
                                    Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
                                }
                            });

//                    Data data=new Data("Pickup", json_pickup);
                    Data data=new Data("Pickup", dataString);
                    Sender content=new Sender(token.getToken(), data, "high");
                    Log.d("DEBUG", "Sender token : " + token.getToken());

                    mService.sendMessage(content).enqueue(new Callback<FCMResponse>() {
                        @Override
                        public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {

                            Log.d("DEBUG", "Firebase Response : " + response.body().toString());

                            if (response.body().success==1) Message.message(context, Messages.REQUEST_SUCCESS);
                            else Message.messageError(context, Errors.SENT_FAILED);
                        }

                        @Override
                        public void onFailure(Call<FCMResponse> call, Throwable t) {
                            Log.d("ERROR", t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static googleAPIInterface getGoogleAPI(){


        return RetrofitClient.getClient(baseURL).create(googleAPIInterface.class);


    }
    public static IGoogleAPI getGoogleService(){
        return GoogleMapsAPI.getClient(googleAPIUrl).create(IGoogleAPI.class);
    }
}
