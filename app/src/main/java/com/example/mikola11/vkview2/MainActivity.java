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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Fragment frag;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


        if (this.getPreferences(Context.MODE_PRIVATE).getString(NAME_PREF_TOKEN, "accessToken").equals(LoginFragment.accessToken) && this.getPreferences(Context.MODE_PRIVATE).getString(NAME_PREF_ID, "userId").equals(LoginFragment.userId)) {

            frag = new LoginFragment();
            ft.add(R.id.fragment, frag);


        } else {

            frag = new FriendsListFragment();
            ft.add(R.id.fragment, frag);


            Log.d(LOG, this.getLocalClassName() + " access_token = " + LoginFragment.accessToken);
            Log.d(LOG, this.getLocalClassName() + " user_id = " + LoginFragment.userId);

        }

        ft.commit();


    }

}
