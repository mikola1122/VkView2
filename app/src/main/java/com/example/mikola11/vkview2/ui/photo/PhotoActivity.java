package com.example.mikola11.vkview2.ui.photo;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.mikola11.vkview2.R;

public class PhotoActivity extends Activity {

    public static final String KEY_PHOTO_ACTIVITY_POSITION = "PhotoClickPosition";
    public static final String KEY_PHOTO_ACTIVITY_URL_LIST = "PhotoListUrl";


    private  int positionClick;
    private String[] photoUrlArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        positionClick = getIntent().getExtras().getInt(KEY_PHOTO_ACTIVITY_POSITION);
        photoUrlArray = getIntent().getExtras().getStringArray(KEY_PHOTO_ACTIVITY_URL_LIST);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        SamplePagerAdapter samplePagerAdapter = new SamplePagerAdapter(photoUrlArray, this);
        viewPager.setAdapter(samplePagerAdapter);
        viewPager.setCurrentItem(positionClick);

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Log.d("NIKI", String.valueOf(positionClick));
        Log.d("NIKI", photoUrlArray[positionClick]);


    }

}
