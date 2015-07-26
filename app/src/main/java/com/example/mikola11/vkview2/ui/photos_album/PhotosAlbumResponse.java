package com.example.mikola11.vkview2.ui.photos_album;

import com.example.mikola11.vkview2.api.entity.PhotoAlbum;

import java.util.ArrayList;

public class PhotosAlbumResponse {
    private int count;
    private ArrayList<PhotoAlbum> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<PhotoAlbum> getItems() {
        return items;
    }

    public void setItems(ArrayList<PhotoAlbum> items) {
        this.items = items;
    }
}
