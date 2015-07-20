package com.example.mikola11.vkview2;


import android.app.Application;
import android.util.Log;

import com.example.mikola11.vkview2.api.Api;
import com.example.mikola11.vkview2.ui.friends.FResponse;
import com.example.mikola11.vkview2.ui.friends.Friend;
import com.example.mikola11.vkview2.ui.friends.FriendsResponse;
import com.example.mikola11.vkview2.ui.login.TokenStorage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

public class MyApplication extends Application {

    Api api;
    String accessToken;
    Gson gson;

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
        Log.d("NIKI", "Send request");
        accessToken = TokenStorage.getAccesTokenPref(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("order", "random");
//        parameters.put("count", "4");
        parameters.put("fields", "photo_100");
        parameters.put("v", "5.34");
        parameters.put("access_token", accessToken);
        FResponse response1 = api.getFriendsData(parameters);

        FriendsResponse response = response1.getResponse();

        List<Friend> responseItem = response.getItems();

        EventBus.getDefault().post(new PutFriendsDataEvent(responseItem));
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }
}
