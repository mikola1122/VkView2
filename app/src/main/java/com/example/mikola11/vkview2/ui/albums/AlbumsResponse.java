package com.example.mikola11.vkview2.ui.albums;


import java.util.ArrayList;

public class AlbumsResponse {
    private int count;
    private ArrayList<Album> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Album> getItems() {
        return items;
    }

    public void setItems(ArrayList<Album> items) {
        this.items = items;
    }
}
