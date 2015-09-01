package com.example.mikola11.vkview2.ui.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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
import com.example.mikola11.vkview2.utils.TouchImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;

public class PhotoSamplePagerAdapter extends PagerAdapter {

    String[] photoUrl;
    Context mContext;
    Bitmap myResource;
    int localPosition;


    public String urlSharePhoto;
    public TouchImageView v;
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

        container.addView(root, 0);


        root.setTag(TAG_IMAGE_VIEW + position);

        v = (TouchImageView) root.findViewById(R.id.image);
        v.setPadding(0, 0, 0, 0);
        v.setScaleType(TouchImageView.ScaleType.FIT_CENTER);
//        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        localPosition = position;
        urlSharePhoto = photoUrl[position];

        Glide.with(mContext)
                .load(urlSharePhoto)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        v.setImageBitmap(bitmap);
                    }
                });


//        Glide.with(mContext).load(photoUrl[position])
//                .asBitmap()
//                .fitCenter()
//                .into(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PhotoUri myPhotoUri = new PhotoUri();
//                Uri uriPhoto = myPhotoUri.getLocalBitmapUri((ImageView) view);
                EventBus.getDefault().post(new SendBitmapPhotoToShareEvent(getLocalBitmapUri((ImageView) view)));
                Log.d("NIKI", urlSharePhoto);
            }
        });

        return root;
    }


    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((FrameLayout) object);
    }
}
