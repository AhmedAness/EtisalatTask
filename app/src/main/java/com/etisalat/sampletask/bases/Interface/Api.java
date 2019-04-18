package com.etisalat.sampletask.bases.Interface;

import com.etisalat.sampletask.bases.model.menu;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL ="https://api.androidhive.info/";

    @GET("pizza/?format=xml")
    Call<menu> GetFoods();

}
