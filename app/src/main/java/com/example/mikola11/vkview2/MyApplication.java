package com.example.mikola11.vkview2;


import android.app.Application;
import android.util.Log;

import com.example.mikola11.vkview2.api.Api;
import com.example.mikola11.vkview2.event.PutAlbumsDataEvent;
import com.example.mikola11.vkview2.event.PutFriendsDataEvent;
import com.example.mikola11.vkview2.event.PutPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.event.RequestAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestFriendsDataEvent;
import com.example.mikola11.vkview2.event.RequestPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.ui.albums.AResponse;
import com.example.mikola11.vkview2.ui.albums.Album;
import com.example.mikola11.vkview2.ui.albums.AlbumsResponse;
import com.example.mikola11.vkview2.ui.friends.FResponse;
import com.example.mikola11.vkview2.ui.friends.Friend;
import com.example.mikola11.vkview2.ui.friends.FriendsResponse;
import com.example.mikola11.vkview2.ui.login.TokenStorage;
import com.example.mikola11.vkview2.ui.photos_album.PAResponse;
import com.example.mikola11.vkview2.ui.photos_album.PhotoAlbum;
import com.example.mikola11.vkview2.ui.photos_album.PhotosAlbumResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class MyApplication extends Application {

    Api api;
    String accessToken;

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        RestAdapter friendsDataRestAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.vk.com")
                .build();
        api = friendsDataRestAdapter.create(Api.class);
        super.onCreate();
    }


    public void onEventAsync(RequestFriendsDataEvent event) {
        Log.d("NIKI", "Send request friends");
        accessToken = TokenStorage.getAccesTokenPref(this);
        Map<String, String> parametersFriends = new HashMap<>();
        parametersFriends.put("order", "random");
        parametersFriends.put("fields", "photo_100");
        parametersFriends.put("v", "5.34");
        parametersFriends.put("access_token", accessToken);
        FResponse responseF = api.getFriendsData(parametersFriends);

        FriendsResponse response = responseF.getResponse();

        List<Friend> responseItem = response.getItems();

        EventBus.getDefault().post(new PutFriendsDataEvent(responseItem));
    }

    public void onEventAsync(RequestAlbumsDataEvent event){
        Log.d("NIKI", "Send request albums");
        Map<String, String> parametersAlbums = new HashMap<>();
        parametersAlbums.put("owner_id", String.valueOf(event.massage));
        parametersAlbums.put("need_covers", String.valueOf(1));
        parametersAlbums.put("v", "5.34");
        AResponse responseA = api.getAlbumsData(parametersAlbums);

        AlbumsResponse response = responseA.getResponse();

        List<Album> responseItem = response.getItems();

        EventBus.getDefault().post(new PutAlbumsDataEvent(responseItem));
    }
    public void onEventAsync(RequestPhotosAlbumDataEvent event){
        Log.d("NIKI", "Send request photos");
        accessToken = TokenStorage.getAccesTokenPref(this);
        Map<String, String> parametersPhotosAlbum = new HashMap<>();
        parametersPhotosAlbum.put("owner_id", String.valueOf(event.massageUserId));
        parametersPhotosAlbum.put("album_id", String.valueOf(event.massageAlbumId));
        parametersPhotosAlbum.put("rev", String.valueOf(0));
        parametersPhotosAlbum.put("v", "5.34");
        parametersPhotosAlbum.put("access_token", accessToken);
        PAResponse responsePA = api.getPhotosAlbumData(parametersPhotosAlbum);

        PhotosAlbumResponse response = responsePA.getResponse();

        List<PhotoAlbum> responseItem = response.getItems();

        EventBus.getDefault().post(new PutPhotosAlbumDataEvent(responseItem));
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }
}
