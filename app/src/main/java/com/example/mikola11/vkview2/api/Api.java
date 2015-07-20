package com.example.mikola11.vkview2.api;


import com.example.mikola11.vkview2.ui.friends.FResponse;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface Api {

    @GET("/method/friends.get")
    FResponse getFriendsData(@QueryMap Map<String, String> parameters);
}