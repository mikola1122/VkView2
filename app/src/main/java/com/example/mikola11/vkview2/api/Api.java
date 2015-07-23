package com.example.mikola11.vkview2.api;


import com.example.mikola11.vkview2.ui.albums.AResponse;
import com.example.mikola11.vkview2.ui.friends.FResponse;
import com.example.mikola11.vkview2.ui.photos_album.PAResponse;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface Api {

    @GET("/method/friends.get")
    FResponse getFriendsData(@QueryMap Map<String, String> parametersFriends);

    @GET("/method/photos.getAlbums")
    AResponse getAlbumsData(@QueryMap Map<String, String> parametersAlbums);

    @GET("/method/photos.get")
    PAResponse getPhotosAlbumData(@QueryMap Map<String, String> parametersPhotosAlbum);
}