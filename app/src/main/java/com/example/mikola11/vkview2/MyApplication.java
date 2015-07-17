package com.example.mikola11.vkview2;


import android.app.Application;
import android.util.Log;

import com.example.mikola11.vkview2.api.Api;
import com.example.mikola11.vkview2.ui.friends.Friend;
import com.example.mikola11.vkview2.ui.login.ReturningToken;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class MyApplication extends Application {

    Api api;
    String accessToken;

    @Override
    public void onCreate() {
        RestAdapter friendsDataRestAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.vk.com")
                .build();
        api = friendsDataRestAdapter.create(Api.class);
        super.onCreate();
    }


    public void onEventAsync(RequestFriendsDataEvent event){
        Log.d("NIKI", "Send request");
        accessToken = ReturningToken.getAccesTokenPref(this);
        List<Friend> response = api.getFriendsData(accessToken);
        EventBus.getDefault().post(new PutFriendsDataEvent(response));
    }


}
