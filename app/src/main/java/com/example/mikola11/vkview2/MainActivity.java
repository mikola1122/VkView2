package com.example.mikola11.vkview2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class MainActivity extends FragmentActivity {
    public static final String NAME_PREF_TOKEN = "AccessToken";
    public static final String NAME_PREF_TIME = "ExpiresIn";
    public static final String NAME_PREF_ID = "UserId";
    public static final String LOG = "NIKI";

    public static final String DEFAULT_PREF_TOKEN = "accessToken";
    public static final String DEFAULT_PREF_ID = "userId";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Fragment frag;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        String storedToken = this.getPreferences(Context.MODE_PRIVATE).getString(NAME_PREF_TOKEN, DEFAULT_PREF_TOKEN);
        String storedUserId = this.getPreferences(Context.MODE_PRIVATE).getString(NAME_PREF_ID, DEFAULT_PREF_ID);

        if (storedToken.equals(DEFAULT_PREF_TOKEN) && storedUserId.equals(DEFAULT_PREF_ID)) {

            frag = new LoginFragment();
            ft.add(R.id.fragment, frag);

        } else {

            frag = new FriendsListFragment();
            ft.add(R.id.fragment, frag);

        }

        ft.commit();

        Log.d(LOG, this.getLocalClassName() + " access_token = " + storedToken);
        Log.d(LOG, this.getLocalClassName() + " user_id = " + storedUserId);


    }

}
