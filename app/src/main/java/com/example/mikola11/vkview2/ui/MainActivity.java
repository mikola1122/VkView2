package com.example.mikola11.vkview2.ui;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.GetUserData;
import com.example.mikola11.vkview2.event.GoToAlbumsFragmentEvent;
import com.example.mikola11.vkview2.event.GoToFriendsListEvent;
import com.example.mikola11.vkview2.event.GoToPhotoActivityEvent;
import com.example.mikola11.vkview2.event.GoToPhotosAlbumFragmentEvent;
import com.example.mikola11.vkview2.event.PutUserDataEvent;
import com.example.mikola11.vkview2.event.SendSearchFriendEvent;
import com.example.mikola11.vkview2.ui.albums.AlbumsFragment;
import com.example.mikola11.vkview2.ui.friends.FriendsListFragment;
import com.example.mikola11.vkview2.ui.login.LoginFragment;
import com.example.mikola11.vkview2.ui.photo.PhotoActivity;
import com.example.mikola11.vkview2.ui.photos_album.PhotosAlbumFragment;
import com.example.mikola11.vkview2.utils.TokenStorage;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

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
    private String userFullName = null;
    private String userPhotoLink = null;
    private String userStatus = null;
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


        chooseStartFragment();

        FrameLayout fragment2 = (FrameLayout) findViewById(R.id.fragment2);
        isTablet = (fragment2 != null);
    }

    private void initNavigationDrawer() {
        // Инициализируем Toolbar
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AccountHeader headerResult = initAccountHeader();

        // Инициализируем Navigation Drawer
        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_my_albums)).withIcon(GoogleMaterial.Icon.gmd_photo_album).withIdentifier(2),
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_friends)).withIcon(GoogleMaterial.Icon.gmd_person_outline).withIdentifier(2),
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_communities)).withIcon(GoogleMaterial.Icon.gmd_people_outline).withIdentifier(3),
                        new SectionDrawerItem().withName(getString(R.string.drawer_item_setting)),
                        new SecondaryDrawerItem().withName(getString(R.string.drawer_item_starting_page)).withIcon(GoogleMaterial.Icon.gmd_smartphone),
                        new SecondaryDrawerItem().withName(getString(R.string.drawer_item_download)).withIcon(GoogleMaterial.Icon.gmd_file_download),
                        new SecondaryDrawerItem().withName(getString(R.string.drawer_item_languages)).withIcon(GoogleMaterial.Icon.gmd_language),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_help)).withIcon(GoogleMaterial.Icon.gmd_help).withIdentifier(4),
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_feedback)).withIcon(GoogleMaterial.Icon.gmd_feedback).withIdentifier(5),
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_log_out)).withIcon(GoogleMaterial.Icon.gmd_power_settings_new).withIdentifier(6)
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
                })
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
//                        if (drawerResult.setSelection(1)){
//
//                        }
//                        return true;
//                    }
//                })
                .build();
    }

    private AccountHeader initAccountHeader() {
        // Инициализируем Account Header

        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder)
                        .into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });

        IProfile loginUser = new ProfileDrawerItem()
                .withName(userFullName)
                .withIcon(userPhotoLink);

        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.indeema)
                .addProfiles(loginUser)
                .build();


    }

    private void chooseStartFragment() {
        String storedToken = TokenStorage.getAccesTokenPref(getApplicationContext());


        if (storedToken.equals(DEFAULT_PREF_TOKEN)) {
            invalidateOptionsMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment1, new LoginFragment()).commit();
        } else {
            EventBus.getDefault().post(new GetUserData());
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

    public void setSearchVisibilityIncompleteLoad(Boolean visiblBool) {
        searchMenuItem.setVisible(visiblBool);
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onBackPressed() {

        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
//            if (getFragmentManager().getBackStackEntryCount() > 0) {
//                getFragmentManager().popBackStackImmediate();
//                return;
//            }
//            invalidateOptionsMenu();
            super.onBackPressed();
        }
    }

    public void onEvent(PutUserDataEvent event){
        userFullName = event.massageUserFullName;
        userStatus = event.massageUserStatus;
        userPhotoLink = event.massageUserPhotoLink;

        initNavigationDrawer();
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

        // Скрываем клавиатуру при открытии Navigation Drawer
        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
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
