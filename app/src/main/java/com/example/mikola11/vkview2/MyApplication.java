package com.example.mikola11.vkview2;


import android.app.Application;
import android.util.Log;

import com.example.mikola11.vkview2.api.Api;
import com.example.mikola11.vkview2.api.entity.AlbumsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponseWrapper;
import com.example.mikola11.vkview2.api.entity.UserResponseWrapper;
import com.example.mikola11.vkview2.event.GetUserData;
import com.example.mikola11.vkview2.event.PutAlbumsDataEvent;
import com.example.mikola11.vkview2.event.PutFriendsDataEvent;
import com.example.mikola11.vkview2.event.PutPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.event.PutUserDataEvent;
import com.example.mikola11.vkview2.event.RequestAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestFriendsDataEvent;
import com.example.mikola11.vkview2.event.RequestPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.utils.TokenStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class MyApplication extends Application {

    private Api api;

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
            RestAdapter friendsDataRestAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.vk.com")
                    .build();
            api = friendsDataRestAdapter.create(Api.class);
        super.onCreate();
    }

    public void onEventAsync(GetUserData event){
        String accessToken = TokenStorage.getAccesTokenPref(this);

        Log.d("NIKI", "Send request user data for navigation drawer");
        Map<String, String> parametersUser= new HashMap<>();
        parametersUser.put("fields","photo_100");
        parametersUser.put("access_token", accessToken);
        parametersUser.put("v", "5.34");
        UserResponseWrapper responseU = api.getUserData(parametersUser);

        EventBus.getDefault().post(new PutUserDataEvent(
                responseU.getResponse().get(0).getFullName(),
                responseU.getResponse().get(0).getPhoto_100(),
                responseU.getResponse().get(0).getStatus()));
    }

    public void onEventAsync(RequestFriendsDataEvent event) {
        String accessToken = TokenStorage.getAccesTokenPref(this);

        Log.d("NIKI", "Send request friends");
        Map<String, String> parametersFriends = new HashMap<>();
        parametersFriends.put("order", "random");
        parametersFriends.put("fields", "photo_100");
        parametersFriends.put("v", "5.34");
        parametersFriends.put("access_token", accessToken);
        FriendsResponseWrapper responseF = api.getFriendsData(parametersFriends);


        EventBus.getDefault().post(new PutFriendsDataEvent(responseF.getResponse().getItems()));
    }

    public void onEventAsync(RequestAlbumsDataEvent event) {
        Log.d("NIKI", "Send request albums");
        Map<String, String> parametersAlbums = new HashMap<>();
        parametersAlbums.put("owner_id", String.valueOf(event.massage));
        parametersAlbums.put("need_system", "1");
        parametersAlbums.put("need_covers", "1");
        parametersAlbums.put("v", "5.34");
        AlbumsResponseWrapper responseA = api.getAlbumsData(parametersAlbums);

        EventBus.getDefault().post(new PutAlbumsDataEvent(responseA.getResponse().getItems()));
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

        EventBus.getDefault().post(new PutPhotosAlbumDataEvent(responsePA.getResponse().getItems()));
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
