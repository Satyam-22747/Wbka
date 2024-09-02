package com.satdroid.wkap;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonDataHolder {

    @GET("users")
    Call<List<UsersModal>> getUsers();

}
