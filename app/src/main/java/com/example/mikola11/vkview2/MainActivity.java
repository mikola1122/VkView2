package com.example.mikola11.vkview2;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;


public class MainActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);


        Fragment frag1 = new Fragment1();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment1, frag1);
        ft.commit();


//        Uri data = getIntent().getData();
//        String scheme = data.getScheme(); // "http"
//        String host = data.getHost(); // "twitter.com"
//        List<String> params = data.getPathSegments();
//        String first = params.get(0); // "status"
//        String second = params.get(1);

    }
}
