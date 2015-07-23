package com.example.mikola11.vkview2.ui.photos_album;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mikola11.vkview2.R;

import java.util.List;

public class PhotosAlbumAdapter extends BaseAdapter {
    List<PhotoAlbum> photoAlbumList;
    private Context mContext;

    public PhotosAlbumAdapter(Context context, List<PhotoAlbum> photoAlbumList) {
        this.photoAlbumList = photoAlbumList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return photoAlbumList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoAlbumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_photos_cardview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.PhotoAlbumImage = (ImageView) convertView.findViewById(R.id.vkPhotoAlbumItemImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(photoAlbumList.get(position).getPhoto_604())
                .centerCrop()
                .into(viewHolder.PhotoAlbumImage);

        return convertView;
    }

    static class ViewHolder {
        public ImageView PhotoAlbumImage;
    }
}
