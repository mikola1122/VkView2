package com.example.mikola11.vkview2.ui;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.TokenStorage;
import com.example.mikola11.vkview2.event.GoToAlbumsFragmentEvent;
import com.example.mikola11.vkview2.event.GoToFriendsListEvent;
import com.example.mikola11.vkview2.event.GoToPhotoFragmentEvent;
import com.example.mikola11.vkview2.event.GoToPhotosAlbumFragmentEvent;
import com.example.mikola11.vkview2.event.SendSearchFriendEvent;
import com.example.mikola11.vkview2.ui.albums.AlbumsFragment;
import com.example.mikola11.vkview2.ui.albums.AlbumInterf;
import com.example.mikola11.vkview2.ui.friends.FriendsListFragment;
import com.example.mikola11.vkview2.ui.friends.SearchInterf;
import com.example.mikola11.vkview2.ui.login.LoginFragment;
import com.example.mikola11.vkview2.ui.login.LoginInterf;
import com.example.mikola11.vkview2.ui.photo.PhotoActivity;
import com.example.mikola11.vkview2.ui.photos_album.PhotosAlbumFragment;
import com.example.mikola11.vkview2.ui.photos_album.PhotosInterf;

import java.io.ByteArrayOutputStream;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {
    public static final String LOG = "NIKI";

    public static final String DEFAULT_PREF_TOKEN = "noToken";
    public static final String KEY_PHOTO_ACTIVITY_POSITION = "PhotoClickPosition";
    public static final String KEY_PHOTO_ACTIVITY_URL_LIST = "PhotoListUrl";
    public static final String KEY_PHOTO_LEFT = "Left";
    public static final String KEY_PHOTO_TOP = "Top";
    public static final String KEY_PHOTO_WEIGHT = "Weight";
    public static final String KEY_PHOTO_HEIGHT = "Height";
    public static final String KEY_ORIENTATION = "Orientation";
    public static final String KEY_BITMAP = "Image Bitmap";

    public Toolbar toolbar;
    public MenuItem searchMenuItem;
    private String photosTitle;
    private Boolean z;

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        chooseStartFragment();

    }

    private void chooseStartFragment() {
        String storedToken = TokenStorage.getAccesTokenPref(getApplicationContext());


        if (storedToken.equals(DEFAULT_PREF_TOKEN)) {
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new FriendsListFragment()).commit();
        }

        Log.d(LOG, this.getLocalClassName() + " access_token = " + storedToken);
    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EventBus.getDefault().post(new SendSearchFriendEvent(newText));
                Log.d("NIKI", newText);
                return true;
            }
        });
        searchMenuItem.setVisible(z);

//        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.fragment);
//        if ((currentFragment instanceof AlbumInterf) && currentFragment.isVisible()) {
//            getSupportActionBar().show();
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
//            getSupportActionBar().setTitle(albumTitle);
//            searchMenuItem.setVisible(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            Log.d("NIKI", "Main toolbar show, but search item not visible");
//        } else if ((currentFragment instanceof PhotosInterf) && currentFragment.isVisible()) {
//            getSupportActionBar().show();
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
//            getSupportActionBar().setTitle(photosTitle);
//            searchMenuItem.setVisible(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            Log.d("NIKI", "Main toolbar show, but search item not visible (PhotoToolbar)");
//        } else if ((currentFragment instanceof LoginInterf) && currentFragment.isVisible()) {
//            getSupportActionBar().hide();
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            searchMenuItem.setVisible(false);
//            Log.d("NIKI", "Main toolbar not show");
//        } else if ((currentFragment instanceof SearchInterf) && currentFragment.isVisible()) {
//            getSupportActionBar().show();
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            searchMenuItem.setVisible(true);
//            Log.d("NIKI", "Main toolbar show and visible search");
//        }

        return true;
    }

    public void setSearchVisibility(Boolean zz) {
        z = zz;
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
            return;
        }
        invalidateOptionsMenu();
        super.onBackPressed();

    }


    public void onEvent(GoToFriendsListEvent event) {
        invalidateOptionsMenu();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new FriendsListFragment())
                .commit();

    }

    public void onEvent(GoToAlbumsFragmentEvent event) {
        invalidateOptionsMenu();
        Bundle bundle = new Bundle();
        bundle.putString("AlbumTitle", event.massage);
        AlbumsFragment albumsFragment = new AlbumsFragment();
        albumsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, albumsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onEvent(GoToPhotosAlbumFragmentEvent event) {
        photosTitle = event.massage;
        invalidateOptionsMenu();
        Bundle bundle = new Bundle();
        bundle.putString("PhotosTitle", event.massage);
        PhotosAlbumFragment photosFragment = new PhotosAlbumFragment();
        photosFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, photosFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onEvent(GoToPhotoFragmentEvent event) {
        invalidateOptionsMenu();
        int orientation = getResources().getConfiguration().orientation;
//        ByteArrayOutputStream bs = new ByteArrayOutputStream();
//        event.massageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bs);
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        intent.putExtra(KEY_PHOTO_ACTIVITY_POSITION, event.massagePosition)
                .putExtra(KEY_PHOTO_ACTIVITY_URL_LIST, event.massageUrl)
                .putExtra(KEY_ORIENTATION, orientation)
                .putExtra(KEY_PHOTO_LEFT, event.massageLocation[0])
                .putExtra(KEY_PHOTO_TOP, event.massageLocation[1])
                .putExtra(KEY_PHOTO_WEIGHT, event.massageWidth)
                .putExtra(KEY_PHOTO_HEIGHT, event.massageHeight);
//                .putExtra(KEY_BITMAP, bs.toByteArray());
        startActivity(intent);
        overridePendingTransition(0, 0);
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
}
