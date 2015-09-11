package com.example.mikola11.vkview2.ui.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.SendBitmapPhotoToShareEvent;
import com.example.mikola11.vkview2.utils.PhotoUri;
import com.example.mikola11.vkview2.utils.TouchImageView;

import de.greenrobot.event.EventBus;

public class PhotoSamplePagerAdapter extends PagerAdapter {

    String[] photoUrl;
    Context mContext;
    Bitmap myResource;
    int localPosition;


    public String urlSharePhoto;
    public Bitmap theBitmap = null;
    public static final String TAG_IMAGE_VIEW = "myPhotoImage";
    private final LayoutInflater mInflater;

    public PhotoSamplePagerAdapter(String[] pages, Context context) {
        this.photoUrl = pages;
        this.mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return photoUrl.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View root = mInflater.inflate(R.layout.item_photo_frame, container, false);

        ((ViewPager) container).addView(root, 0);


        root.setTag(TAG_IMAGE_VIEW + position);

        final TouchImageView v = (TouchImageView) root.findViewById(R.id.image);
        v.setPadding(0, 0, 0, 0);
        v.setScaleType(TouchImageView.ScaleType.FIT_CENTER);
//        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        localPosition = position;
        urlSharePhoto = photoUrl[position];

        Glide.with(mContext)
                .load(urlSharePhoto)
                .asBitmap()
                .placeholder(R.drawable.vk_image_loaded)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        v.setImageBitmap(bitmap);
                    }
                });


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventBus.getDefault().post(new SendBitmapPhotoToShareEvent((new PhotoUri())
                        .getLocalBitmapUri((ImageView) view)));
                Log.d("NIKI", urlSharePhoto);
            }
        });

        return root;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((FrameLayout) object);
    }
}
