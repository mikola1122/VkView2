package com.example.mikola11.vkview2.ui.albums;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.ui.friends.Friend;

import java.util.List;

public class AlbumsAdapter extends BaseAdapter {
    List<Album> albumList;
    private Context mContext;

    public AlbumsAdapter(Context context, List<Album> albumList) {
        this.albumList = albumList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.albums_cardview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.albumName = (TextView) convertView.findViewById(R.id.vkAlbumItemName);
            viewHolder.albumIcon = (ImageView) convertView.findViewById(R.id.vkAlbumItemIcon);
            convertView.setTag(viewHolder);
        } else {
          viewHolder =(ViewHolder) convertView.getTag();
        }

        viewHolder.albumName.setText(albumList.get(position).getTitle());
        Glide.with(mContext).load(albumList.get(position).getThumb_src())
                .centerCrop()
                .into(viewHolder.albumIcon);

        return convertView;
    }

    static class ViewHolder  {
        public TextView albumName;
        public ImageView albumIcon;
    }

}
