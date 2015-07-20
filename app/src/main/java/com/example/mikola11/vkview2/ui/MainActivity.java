package com.example.mikola11.vkview2.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mikola11.vkview2.ui.friends.FriendsListFragment;
import com.example.mikola11.vkview2.GoToFriendsListEvent;
import com.example.mikola11.vkview2.ui.login.LoginFragment;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.ui.login.TokenStorage;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {
    public static final String NAME_PREF_TOKEN = "AccessToken";
    public static final String NAME_PREF_TIME = "ExpiresIn";
    public static final String NAME_PREF_ID = "UserId";
    public static final String LOG = "NIKI";

    public static final String DEFAULT_PREF_TOKEN = "noToken";
    public static final String DEFAULT_PREF_ID = "userId";

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        String storedToken = TokenStorage.getAccesTokenPref(getApplicationContext());


        if (storedToken.equals(DEFAULT_PREF_TOKEN)) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new FriendsListFragment()).commit();
        }

        Log.d(LOG, this.getLocalClassName() + " access_token = " + storedToken);

    }

    public void onEvent(GoToFriendsListEvent event){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FriendsListFragment()).commit();

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