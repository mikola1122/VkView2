package com.example.mikola11.vkview2.ui;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.GoToAlbumsFragmentEvent;
import com.example.mikola11.vkview2.event.GoToFriendsListEvent;
import com.example.mikola11.vkview2.event.GoToPhotoActivityEvent;
import com.example.mikola11.vkview2.event.GoToPhotosAlbumFragmentEvent;
import com.example.mikola11.vkview2.event.SendSearchFriendEvent;
import com.example.mikola11.vkview2.ui.albums.AlbumsFragment;
import com.example.mikola11.vkview2.ui.friends.FriendsListFragment;
import com.example.mikola11.vkview2.ui.login.LoginFragment;
import com.example.mikola11.vkview2.ui.photo.PhotoActivity;
import com.example.mikola11.vkview2.ui.photos_album.PhotosAlbumFragment;
import com.example.mikola11.vkview2.utils.TokenStorage;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

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

    public static final String KEY_TITLE = "Title";
    public static final String KEY_TABLET = "isTablet";

    public Toolbar toolbar;
    public MenuItem searchMenuItem;
    private Drawer drawerResult = null;
    private String photosTitle;
    private Boolean visibilityBull;
    private Boolean isTablet;

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

        initNavigationDrawer();

        chooseStartFragment();

        FrameLayout fragment2 = (FrameLayout) findViewById(R.id.fragment2);
        isTablet = (fragment2 != null);
    }

    private void initNavigationDrawer() {
        // Инициализируем Toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNavigationDrawer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Инициализируем Navigation Drawer
        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_my_albums)).withIcon(R.drawable.ic_share_white_24dp).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName("free_play").withIcon(R.drawable.ic_share_white_24dp),
                        new PrimaryDrawerItem().withName("custom").withIcon(R.drawable.ic_share_white_24dp).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName("settings"),
                        new SecondaryDrawerItem().withName("help").withIcon(R.drawable.ic_share_white_24dp),
                        new SecondaryDrawerItem().withName("open_source").withIcon(R.drawable.ic_share_white_24dp),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("contact").withIcon(R.drawable.ic_share_white_24dp).withBadge("12+").withIdentifier(1)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Скрываем клавиатуру при открытии Navigation Drawer
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {
                    }
                }).build();
    }

    private void chooseStartFragment() {
        String storedToken = TokenStorage.getAccesTokenPref(getApplicationContext());


        if (storedToken.equals(DEFAULT_PREF_TOKEN)) {
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment1, new LoginFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment1, new FriendsListFragment()).commit();
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
        searchMenuItem.setVisible(visibilityBull);
        return true;
    }

    public void setSearchVisibilCompletedLoad(Boolean visiblBool) {
        visibilityBull = visiblBool;
        return;
    }

    public void setSearchVisibilityincompleteLoad(Boolean visiblBool) {
        searchMenuItem.setVisible(visiblBool);
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

        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
                return;
            }
            invalidateOptionsMenu();
            super.onBackPressed();
        }
    }


    public void onEvent(GoToFriendsListEvent event) {
        invalidateOptionsMenu();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment1, new FriendsListFragment())
                .commit();

    }

    public void onEvent(GoToAlbumsFragmentEvent event) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, event.massage);
        bundle.putBoolean(KEY_TABLET, isTablet);
        AlbumsFragment albumsFragment = new AlbumsFragment();
        albumsFragment.setArguments(bundle);

        if (isTablet) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, albumsFragment)
                    .commit();
            Log.d("NIKI", "THIS IS TABLEEEET !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        } else {
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment1, albumsFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    public void onEvent(GoToPhotosAlbumFragmentEvent event) {
        photosTitle = event.massage;
        Bundle bundle = new Bundle();
        bundle.putString("PhotosTitle", event.massage);
        PhotosAlbumFragment photosFragment = new PhotosAlbumFragment();
        photosFragment.setArguments(bundle);

        if (isTablet) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, photosFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment1, photosFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }

    public void onEvent(GoToPhotoActivityEvent event) {
        invalidateOptionsMenu();
        int orientation = getResources().getConfiguration().orientation;

        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        intent.putExtra(KEY_PHOTO_ACTIVITY_POSITION, event.massagePosition)
                .putExtra(KEY_PHOTO_ACTIVITY_URL_LIST, event.massageUrl)
                .putExtra(KEY_ORIENTATION, orientation)
                .putExtra(KEY_PHOTO_LEFT, event.massageLocation[0])
                .putExtra(KEY_PHOTO_TOP, event.massageLocation[1])
                .putExtra(KEY_PHOTO_WEIGHT, event.massageWidth)
                .putExtra(KEY_PHOTO_HEIGHT, event.massageHeight);
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
