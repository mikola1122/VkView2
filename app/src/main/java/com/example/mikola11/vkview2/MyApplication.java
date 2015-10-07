package com.example.mikola11.vkview2;


import android.app.Application;
import android.util.Log;

import com.example.mikola11.vkview2.api.Api;
import com.example.mikola11.vkview2.api.entity.AlbumsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.CommunitiesResponseWrapper;
import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;
import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponseWrapper;
import com.example.mikola11.vkview2.api.entity.UserResponseWrapper;
import com.example.mikola11.vkview2.event.GetUserDataEvent;
import com.example.mikola11.vkview2.event.PutAlbumsDataEvent;
import com.example.mikola11.vkview2.event.PutFriendsDataEvent;
import com.example.mikola11.vkview2.event.PutPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.event.PutUserDataEvent;
import com.example.mikola11.vkview2.event.RequestAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestCommunitiesDataEvent;
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

    public void onEventAsync(GetUserDataEvent event){
        String accessToken = TokenStorage.getAccesTokenPref(this);

        Log.d("NIKI", "Send request user data for navigation drawer");
        Map<String, String> parametersUser= new HashMap<>();
        parametersUser.put("fields","photo_100,status");
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

    public void onEventAsync(RequestCommunitiesDataEvent event){
        int userId = TokenStorage.getUserIdPref(this);

        Log.d("NIKI", "Send request communities");
        Map<String, String> parametersCommunities = new HashMap<>();
        parametersCommunities.put("user_id", String.valueOf(userId));
        parametersCommunities.put("extended", "1");
        parametersCommunities.put("v", "5.34");
        CommunitiesResponseWrapper responseC = api.getCommunitiesData(parametersCommunities);
        List<CommunitiesResponseWrapper.Communities> communities = new ArrayList<CommunitiesResponseWrapper.Communities>();
        communities.addAll(responseC.getResponse().getItems());
        List<FriendsResponseWrapper.Friend> friends = new ArrayList<FriendsResponseWrapper.Friend>();
        FriendsResponseWrapper.Friend group = new FriendsResponseWrapper.Friend();
        for (int i=0; i<responseC.getResponse().getCount()-1; i++){
            group.setId(-communities.get(i).getId());
            group.setFirst_name(null);
            group.setLast_name(communities.get(i).getName());
            group.setPhoto_100(communities.get(i).getPhoto_100());
            friends.add(i, group);
        }
        EventBus.getDefault().post(new PutFriendsDataEvent(friends));
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
