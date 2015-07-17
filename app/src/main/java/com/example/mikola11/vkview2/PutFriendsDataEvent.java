package com.example.mikola11.vkview2;

import com.example.mikola11.vkview2.ui.friends.Friend;

import java.util.List;

public class PutFriendsDataEvent {
    public List<Friend> massage;

    public PutFriendsDataEvent(List<Friend> massage) {
        this.massage = massage;
    }
}
