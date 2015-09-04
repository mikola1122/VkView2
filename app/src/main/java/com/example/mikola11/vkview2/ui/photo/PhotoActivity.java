package com.example.mikola11.vkview2.ui.photo;


import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.SendBitmapPhotoToShareEvent;

import de.greenrobot.event.EventBus;

public class PhotoActivity extends AppCompatActivity {

    public static final String KEY_PHOTO_ACTIVITY_POSITION = "PhotoClickPosition";
    public static final String KEY_PHOTO_ACTIVITY_URL_LIST = "PhotoListUrl";
    public static final String KEY_PHOTO_LEFT = "Left";
    public static final String KEY_PHOTO_TOP = "Top";
    public static final String KEY_PHOTO_WEIGHT = "Weight";
    public static final String KEY_PHOTO_HEIGHT = "Height";
    public static final String KEY_ORIENTATION = "Orientation";
    public static final String KEY_BITMAP = "Image Bitmap";

    private Toolbar toolbar;
    private MyShareActionProvider mShareActionProvider;
    Intent intent;
    public boolean clickCounter = false;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    private static final int ANIM_DURATION = 350;

    private BitmapDrawable mBitmapDrawable;
    private ColorMatrix colorizerMatrix = new ColorMatrix();
    ColorDrawable mBackground;
    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;

    private int positionClick;
    private String[] photoUrlArray;
    private int mOriginalOrientation;
    private int thumbnailLeft;
    private int thumbnailTop;
    private int thumbnailWidth;
    private int thumbnailHeight;

    private FrameLayout mTopLevelLayout;
    private ViewPager viewPager;

    PhotoSamplePagerAdapter photoPagerAdapter;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Bundle bundle = getIntent().getExtras();

        positionClick = bundle.getInt(KEY_PHOTO_ACTIVITY_POSITION);
        photoUrlArray = bundle.getStringArray(KEY_PHOTO_ACTIVITY_URL_LIST);
        mOriginalOrientation = bundle.getInt(KEY_ORIENTATION);
        thumbnailLeft = bundle.getInt(KEY_PHOTO_LEFT);
        thumbnailTop = bundle.getInt(KEY_PHOTO_TOP);
        thumbnailWidth = bundle.getInt(KEY_PHOTO_WEIGHT);
        thumbnailHeight = bundle.getInt(KEY_PHOTO_HEIGHT);


        mTopLevelLayout = (FrameLayout) findViewById(R.id.topLevelLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        photoPagerAdapter = new PhotoSamplePagerAdapter(photoUrlArray, this);
        viewPager.setAdapter(photoPagerAdapter);
        viewPager.setCurrentItem(positionClick);

        mBackground = new ColorDrawable(Color.BLACK);
        mTopLevelLayout.setBackground(mBackground);

        if (savedInstanceState == null) {
            ViewTreeObserver observer = viewPager.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    viewPager.getViewTreeObserver().removeOnPreDrawListener(this);

//                     Figure out where the thumbnail and full size versions are, relative
//                     to the screen and each other
                    int[] screenLocation = new int[2];
                    viewPager.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / viewPager.getWidth();
                    mHeightScale = (float) thumbnailHeight / viewPager.getHeight();
                    runEnterAnimation();
                    return true;
                }
            });
        }
        initToolbar();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // todo  setShareIntent();
//                ImageView image = (ImageView) viewPager.getFocusedChild();
                ViewGroup image = (ViewGroup) viewPager.findViewWithTag(PhotoSamplePagerAdapter
                        .TAG_IMAGE_VIEW + viewPager.getCurrentItem());
                Log.d("SuperTag", "View "+image);
//                ImageView image = (ImageView) image.getDisplay().getDisplayId(R.id.image);
//                PhotoUri myPhotoUri = new PhotoUri();
//                Uri uriPhoto = myPhotoUri.getLocalBitmapUri(image);
//                intent = new Intent();
//                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_STREAM, uriPhoto);
//                setShareIntent(intent);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Log.d("NIKI", String.valueOf(positionClick));
        Log.d("NIKI", photoUrlArray[positionClick]);

    }


    public void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        viewPager.setPivotX(0);
        viewPager.setPivotY(0);
        viewPager.setScaleX(mWidthScale);
        viewPager.setScaleY(mHeightScale);
        viewPager.setTranslationX(mLeftDelta);
        viewPager.setTranslationY(mTopDelta);

        // Animate scale and translation to go from thumbnail to full size
        viewPager.animate().setDuration(duration).
                scaleX(1).scaleY(1).
                translationX(0).translationY(0).
                setInterpolator(sDecelerator);

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        bgAnim.setDuration(duration);
        bgAnim.start();
    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbarPhoto);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_24dp);
            getSupportActionBar().hide();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_toolbar, menu);
        MenuItem itemShare = menu.findItem(R.id.share);

        mShareActionProvider = new MyShareActionProvider(PhotoActivity.this);
        MenuItemCompat.setActionProvider(itemShare, mShareActionProvider);

        itemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("superTag", "View " + viewPager.getFocusedChild());
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onEvent(SendBitmapPhotoToShareEvent event) {
        if (event.massage == null) {
            Log.e("NIKI", "bitmap photo is empty");
        } else {
            intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, event.massage);
            setShareIntent(intent);
        }
        clickCounter = !clickCounter;
        if (clickCounter) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }
}
