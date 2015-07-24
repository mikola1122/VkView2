package com.example.mikola11.vkview2.ui.photo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mikola11.vkview2.R;

public class PhotoActivity extends Activity {

    public static final String KEY_PHOTO_ACTIVITY_POSITION = "PhotoClickPosition";
    public static final String KEY_PHOTO_ACTIVITY_URL_LIST = "PhotoListUrl";

    private  int positionClick;
    private String[] photoUrlArray;
    ImageView photoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        positionClick = getIntent().getExtras().getInt(KEY_PHOTO_ACTIVITY_POSITION);
        photoUrlArray = getIntent().getExtras().getStringArray(KEY_PHOTO_ACTIVITY_URL_LIST);

        Log.d("NIKI", String.valueOf(positionClick));
        Log.d("NIKI", photoUrlArray[positionClick]);

        photoImage = (ImageView) this.findViewById(R.id.photoImageView);
        Glide.with(this).load(photoUrlArray[positionClick])
                .centerCrop()
                .into(photoImage);

    }
}
