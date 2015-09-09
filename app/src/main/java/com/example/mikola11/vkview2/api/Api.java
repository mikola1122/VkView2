package com.example.mikola11.vkview2.api;


import com.example.mikola11.vkview2.api.entity.AlbumsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponseWrapper;
import com.example.mikola11.vkview2.api.entity.UserResponseWrapper;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface Api {

    @GET("/method/users.get")
    UserResponseWrapper getUserData(@QueryMap Map<String, String> parametersUser);

    @GET("/method/friends.get")
    FriendsResponseWrapper getFriendsData(@QueryMap Map<String, String> parametersFriends);

    @GET("/method/photos.getAlbums")
    AlbumsResponseWrapper getAlbumsData(@QueryMap Map<String, String> parametersAlbums);

    @GET("/method/photos.get")
    PhotosAlbumResponseWrapper getPhotosAlbumData(@QueryMap Map<String, String> parametersPhotosAlbum);
}