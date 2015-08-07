package com.example.mikola11.vkview2.ui.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mikola11.vkview2.event.SendBitmapPhotoToShareEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;

public class PhotoSamplePagerAdapter extends PagerAdapter {

    String[] photoUrl;
    Context mContext;

    public String urlSharePhoto;
    public ImageView v;
    public Bitmap theBitmap;

    public PhotoSamplePagerAdapter(String[] pages, Context context) {
        this.photoUrl = pages;
        this.mContext = context;
//        this.theBitmap = theBitmap;
    }


    @Override
    public int getCount() {
        return photoUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        v = new ImageView(mContext);
        v.setPadding(0, 0, 0, 0);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        int mPosition = position;


//        v.setImageBitmap(theBitmap);

        Glide.with(mContext).load(photoUrl[position])
                .asBitmap()
                .fitCenter()
                .into(v);


        ((ViewPager) container).addView(v, 0);

        urlSharePhoto = photoUrl[position];

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new SendBitmapPhotoToShareEvent(getLocalBitmapUri((ImageView) view )));
                Log.d("NIKI", urlSharePhoto);
            }
        });

        return v;
    }



    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable)  imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".jpeg");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(ViewGroup container) {
    }


}
