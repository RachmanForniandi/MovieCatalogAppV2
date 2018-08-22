package com.example.user.moviecatalogappv2.API;

import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.SearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICall {
    @GET("movie/popular?")
    Call<SearchModel>getPopularMovie(@Query("page")int page);
}
