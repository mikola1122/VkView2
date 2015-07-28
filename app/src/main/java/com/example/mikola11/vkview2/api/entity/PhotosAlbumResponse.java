package com.example.mikola11.vkview2.api.entity;

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
