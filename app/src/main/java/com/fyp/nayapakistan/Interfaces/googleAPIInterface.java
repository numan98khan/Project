package com.fyp.nayapakistan.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface googleAPIInterface {
//    Log.d("DEBUG", "GPI");
    @GET
    Call<String> getPath(@Url String url);
}
