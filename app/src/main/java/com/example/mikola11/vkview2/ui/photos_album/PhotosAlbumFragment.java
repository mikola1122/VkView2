package com.example.mikola11.vkview2.ui.photos_album;


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
import com.example.mikola11.vkview2.api.entity.PhotoAlbum;
import com.example.mikola11.vkview2.event.GoToPhotoFragmentEvent;
import com.example.mikola11.vkview2.event.PutPhotosAlbumDataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class PhotosAlbumFragment extends Fragment {
    List<PhotoAlbum> photoAlbumList = new ArrayList<PhotoAlbum>();
    PhotosAlbumAdapter photoAlbumListAdapter;
    GridView gridView;

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
        gridView = (GridView) v.findViewById(R.id.photoAlbumGridView);
        photoAlbumListAdapter = new PhotosAlbumAdapter(getActivity(), photoAlbumList);
        gridView.setAdapter(photoAlbumListAdapter);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] listUrl = new String[photoAlbumListAdapter.getCount()];
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
                EventBus.getDefault().post(new GoToPhotoFragmentEvent(position, listUrl));
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
