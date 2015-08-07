package com.example.mikola11.vkview2;


import android.app.Application;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.mikola11.vkview2.api.Api;
import com.example.mikola11.vkview2.api.entity.Album;
import com.example.mikola11.vkview2.api.entity.AlbumsResponse;
import com.example.mikola11.vkview2.api.entity.AlbumsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.Friend;
import com.example.mikola11.vkview2.api.entity.FriendsResponse;
import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.PhotoAlbum;
import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponse;
import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponseWrapper;
import com.example.mikola11.vkview2.event.PutAlbumsDataEvent;
import com.example.mikola11.vkview2.event.PutFriendsDataEvent;
import com.example.mikola11.vkview2.event.PutPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.event.RequestAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestFriendsDataEvent;
import com.example.mikola11.vkview2.event.RequestPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.utils.CheckConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyApplication extends Application {

    private Api api;

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
            RestAdapter friendsDataRestAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.vk.com")
//                    .setErrorHandler(new MyErrorHandler())
                    .build();
            api = friendsDataRestAdapter.create(Api.class);
        super.onCreate();
    }


    public void onEventAsync(RequestFriendsDataEvent event) {
        Log.d("NIKI", "Send request friends");
        String accessToken = TokenStorage.getAccesTokenPref(this);
        Map<String, String> parametersFriends = new HashMap<>();
        parametersFriends.put("order", "random");
        parametersFriends.put("fields", "photo_100");
        parametersFriends.put("v", "5.34");
        parametersFriends.put("access_token", accessToken);
        FriendsResponseWrapper responseF = api.getFriendsData(parametersFriends);

        FriendsResponse response = responseF.getResponse();

        List<Friend> responseItem = response.getItems();

        EventBus.getDefault().post(new PutFriendsDataEvent(responseItem));
    }

    public void onEventAsync(RequestAlbumsDataEvent event) {
        Log.d("NIKI", "Send request albums");
        Map<String, String> parametersAlbums = new HashMap<>();
        parametersAlbums.put("owner_id", String.valueOf(event.massage));
        parametersAlbums.put("need_system", "1");
        parametersAlbums.put("need_covers", "1");
        parametersAlbums.put("v", "5.34");
        AlbumsResponseWrapper responseA = api.getAlbumsData(parametersAlbums);

        AlbumsResponse response = responseA.getResponse();

        List<Album> responseItem = response.getItems();

        EventBus.getDefault().post(new PutAlbumsDataEvent(responseItem));
    }

    public void onEventAsync(RequestPhotosAlbumDataEvent event) {
        Log.d("NIKI", "Send request photos");
        String accessToken = TokenStorage.getAccesTokenPref(this);
        Map<String, String> parametersPhotosAlbum = new HashMap<>();
        parametersPhotosAlbum.put("owner_id", String.valueOf(event.massageUserId));
        parametersPhotosAlbum.put("album_id", String.valueOf(event.massageAlbumId));
        parametersPhotosAlbum.put("rev", "0");
        parametersPhotosAlbum.put("v", "5.34");
        parametersPhotosAlbum.put("access_token", accessToken);
        PhotosAlbumResponseWrapper responsePA = api.getPhotosAlbumData(parametersPhotosAlbum);

        PhotosAlbumResponse response = responsePA.getResponse();

        List<PhotoAlbum> responseItem = response.getItems();

        EventBus.getDefault().post(new PutPhotosAlbumDataEvent(responseItem));
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }

//    class MyErrorHandler implements ErrorHandler {
//        @Override public Throwable handleError(RetrofitError cause) {
////            Response r = cause.getResponse();
////            if (r != null && r.getStatus() == 401) {
////                return new UnauthorizedException(cause);
////            }
//            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//            builder.setTitle("Error !")
//                    .setMessage("Attach plantain :-)")
//                    .setIcon(R.drawable.vk_request_eror)
//                    .setCancelable(false)
//                    .setNegativeButton("VK, no pain.",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//            AlertDialog alert = builder.create();
//            alert.show();
//            return cause;
//        }
//    }


}
