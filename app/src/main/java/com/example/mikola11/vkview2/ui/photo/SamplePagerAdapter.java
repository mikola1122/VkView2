package com.example.mikola11.vkview2.ui.photo;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SamplePagerAdapter extends PagerAdapter {

    String[] photoUrl;
    Context mContext;


    public SamplePagerAdapter(String[] pages, Context context){
        this.photoUrl = pages;
        this.mContext = context;
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
        ImageView v = new ImageView(mContext);
        v.setPadding(0, 0, 0, 0);
        v.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(photoUrl[position])
                .override(v.getMaxWidth(), v.getMaxHeight())
                .into(v);
        ((ViewPager) container).addView(v, 0);
        return v;
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
