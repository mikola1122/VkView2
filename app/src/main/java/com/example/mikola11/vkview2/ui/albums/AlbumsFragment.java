package com.example.mikola11.vkview2.ui.albums;

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
import com.example.mikola11.vkview2.api.entity.Album;
import com.example.mikola11.vkview2.event.GoToPhotosAlbumFragmentEvent;
import com.example.mikola11.vkview2.event.PutAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestPhotosAlbumDataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class AlbumsFragment extends Fragment {
    List<Album> albumList = new ArrayList<Album>();
    AlbumsAdapter albumListAdapter;
    GridView gridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }
    public void onEventMainThread(PutAlbumsDataEvent event){
        if (event.massage!=null){
            albumList.addAll(event.massage);

//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
                    albumListAdapter.notifyDataSetChanged();
//                }
//            });

        } else {
            Log.e("NIKI", "Empty albums massage1");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_albums, null);

        gridView = (GridView) v.findViewById(R.id.albumsGridView);
        albumListAdapter = new AlbumsAdapter(getActivity(), albumList);
        gridView.setAdapter(albumListAdapter);
        adjustGridView();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new GoToPhotosAlbumFragmentEvent());
                EventBus.getDefault().post(new RequestPhotosAlbumDataEvent(albumList.get(position).getOwner_id(),
                        albumList.get(position).getId()));
            }
        });
        return v;

    }


    private void adjustGridView() {
//        Configuration configuration = getResources().getConfiguration();
//        if (configuration.orientation==Configuration.ORIENTATION_PORTRAIT){
            gridView.setNumColumns(GridView.AUTO_FIT);
//        }else if (configuration.orientation==Configuration.ORIENTATION_LANDSCAPE){
//            gridView.setNumColumns(4);
//        }
//        gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}