package com.satdroid.wkap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPostHolder {
    @GET("posts")
    Call<List<UserPostModal>> getUsersPost();
}
