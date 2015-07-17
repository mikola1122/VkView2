package com.example.mikola11.vkview2.api;


import com.example.mikola11.vkview2.ui.friends.Friend;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface Api {

    @GET("/method/friends.get?order=random&fields=photo_100&v=5.34&access_token={accesToken}")
    List<Friend> getFriendsData(@Path("accessToken") String token);
}