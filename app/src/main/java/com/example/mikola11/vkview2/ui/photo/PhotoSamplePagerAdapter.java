package com.example.mikola11.vkview2.ui.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.PhotoClickEvent;

import de.greenrobot.event.EventBus;

public class PhotoSamplePagerAdapter extends PagerAdapter {

    String[] photoUrl;
    Context mContext;

    public String urlSharePhoto;
    public boolean clickCounter = false;
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
//        v.setId(R.id.);
        v = new ImageView(mContext);
        v.setPadding(0, 0, 0, 0);
        v.setScaleType(ImageView.ScaleType.FIT_CENTER);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        int mPosition = position;


//        v.setImageBitmap(theBitmap);

        Glide.with(mContext).load(photoUrl[position])
                .asBitmap()
//                .listener(new RequestListener<String, Bitmap>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
////                        EventBus.getDefault().post(new SendBitmapPhotoToShareEvent(resource));
//
//                        return false;
//                    }
//                })
                .fitCenter()
                .into(v);


        ((ViewPager) container).addView(v, 0);

        urlSharePhoto = photoUrl[position];

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCounter = !clickCounter;
                EventBus.getDefault().post(new PhotoClickEvent(clickCounter));

                Log.d("NIKI", urlSharePhoto);
            }
        });

        return v;
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
