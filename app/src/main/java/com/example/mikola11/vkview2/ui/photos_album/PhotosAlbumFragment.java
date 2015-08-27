package com.example.mikola11.vkview2.ui.photos_album;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponseWrapper;
import com.example.mikola11.vkview2.event.GoToPhotoActivityEvent;
import com.example.mikola11.vkview2.event.PutPhotosAlbumDataEvent;
import com.example.mikola11.vkview2.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class PhotosAlbumFragment extends Fragment implements PhotosInterf {

    private String[] listUrl;
    private int positionClick;

    List<PhotosAlbumResponseWrapper.PhotoAlbum> photoAlbumList = new ArrayList<PhotosAlbumResponseWrapper.PhotoAlbum>();
    PhotosAlbumAdapter photoAlbumListAdapter;
    GridView gridView;
    Bitmap onPhotoClickBitmap;
    int[] screenLocation = new int[4];
    View viewClick;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    public void onEventMainThread(PutPhotosAlbumDataEvent event) {
        if (event.massage != null) {
            photoAlbumList.addAll(event.massage);
            photoAlbumListAdapter.notifyDataSetChanged();

        } else {
            Log.e("NIKI", "Empty photos albums massage1");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photos_album, null);

        ((MainActivity) getActivity()).getSupportActionBar().show();
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(this.getArguments().getString("PhotosTitle"));
        ((MainActivity) getActivity()).setSearchVisibilCompletedLoad(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("NIKI", "Main toolbar show, but search item not visible (PhotoToolbar)");

        gridView = (GridView) v.findViewById(R.id.photoAlbumGridView);
        photoAlbumListAdapter = new PhotosAlbumAdapter(getActivity(), photoAlbumList);
        gridView.setAdapter(photoAlbumListAdapter);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionClick = position;
                viewClick = view;
                view.getLocationOnScreen(screenLocation);
                Log.d("NIKI", "photo location = (" + screenLocation[0] + ";" + screenLocation[1] +
                        ").");
                Log.d("NIKI", "photo weight = " + view.getWidth() + "; photo height = " +
                        view.getHeight());
                listUrl = new String[photoAlbumListAdapter.getCount()];
                for (int i = 0; i < photoAlbumListAdapter.getCount(); i++) {
                    if (photoAlbumList.get(i).getPhoto_1280() != null) {
                        listUrl[i] = photoAlbumList.get(i).getPhoto_1280();
                    } else if (photoAlbumList.get(i).getPhoto_807() != null) {
                        listUrl[i] = photoAlbumList.get(i).getPhoto_807();
                    } else if (photoAlbumList.get(i).getPhoto_604() != null) {
                        listUrl[i] = photoAlbumList.get(i).getPhoto_604();
                    } else {
                        listUrl[i] = photoAlbumList.get(i).getPhoto_130();
                    }
                }
//                new AsyncTask<Void, Void, Void>(){
//                    @Override
//                    protected Void doInBackground(Void... params) {
//                        Looper.prepare();
//                        try {
//                            onPhotoClickBitmap = Glide
//                                    .with(getActivity())
//                                    .load(listUrl[positionClick])
//                                    .asBitmap()
//                                    .into(20,20)
//                                    .get();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                    }
//                }.execute();
//                if (onPhotoClickBitmap != null){
                    EventBus.getDefault().post(new GoToPhotoActivityEvent(positionClick, listUrl,
                            screenLocation, view.getWidth(), view.getHeight()));

//                }
            }
        });




        return v;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}