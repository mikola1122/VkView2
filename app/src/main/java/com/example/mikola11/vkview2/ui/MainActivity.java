package com.example.mikola11.vkview2.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.GoToAlbumsFragmentEvent;
import com.example.mikola11.vkview2.event.GoToFriendsListEvent;
import com.example.mikola11.vkview2.event.GoToPhotoFragmentEvent;
import com.example.mikola11.vkview2.event.GoToPhotosAlbumFragmentEvent;
import com.example.mikola11.vkview2.ui.albums.AlbumsFragment;
import com.example.mikola11.vkview2.ui.friends.FriendsListFragment;
import com.example.mikola11.vkview2.ui.login.LoginFragment;
import com.example.mikola11.vkview2.ui.login.TokenStorage;
import com.example.mikola11.vkview2.ui.photo.PhotoActivity;
import com.example.mikola11.vkview2.ui.photos_album.PhotosAlbumFragment;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {
    public static final String LOG = "NIKI";

    public static final String DEFAULT_PREF_TOKEN = "noToken";
    public static final String KEY_PHOTO_ACTIVITY_POSITION = "PhotoClickPosition";
    public static final String KEY_PHOTO_ACTIVITY_URL_LIST = "PhotoListUrl";

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String storedToken = TokenStorage.getAccesTokenPref(getApplicationContext());


        if (storedToken.equals(DEFAULT_PREF_TOKEN)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new FriendsListFragment()).commit();
        }

        Log.d(LOG, this.getLocalClassName() + " access_token = " + storedToken);

    }

    public void onEvent(GoToFriendsListEvent event){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new FriendsListFragment())
                .commit();

    }

    public void onEvent(GoToAlbumsFragmentEvent event){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new AlbumsFragment())
                .addToBackStack(null)
                .commit();
    }

    public void onEvent(GoToPhotosAlbumFragmentEvent event){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new PhotosAlbumFragment())
                .addToBackStack(null)
                .commit();
    }

    public void onEvent(GoToPhotoFragmentEvent event){
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        intent.putExtra(KEY_PHOTO_ACTIVITY_POSITION, event.massagePosition);
        intent.putExtra(KEY_PHOTO_ACTIVITY_URL_LIST,event.massageUrl);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
