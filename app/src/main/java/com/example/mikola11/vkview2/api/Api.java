package com.example.mikola11.vkview2.api;


import com.example.mikola11.vkview2.ui.albums.AResponse;
import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.AlbumsResponseWrapper;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface Api {

    @GET("/method/friends.get")
    FriendsResponseWrapper getFriendsData(@QueryMap Map<String, String> parametersFriends);

    @GET("/method/photos.getAlbums")
    AResponse getAlbumsData(@QueryMap Map<String, String> parametersAlbums);

    @GET("/method/photos.get")
    AlbumsResponseWrapper getPhotosAlbumData(@QueryMap Map<String, String> parametersPhotosAlbum);
}