package com.example.mikola11.vkview2.api.entity;


import java.util.ArrayList;

public class FriendsResponse {
    private int count;
    private ArrayList<Friend> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Friend> getItems() {
        return items;
    }

    public void setItems(ArrayList<Friend> items) {
        this.items = items;
    }
}
