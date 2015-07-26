package com.example.mikola11.vkview2.event;

import com.example.mikola11.vkview2.api.entity.Friend;

import java.util.List;

public class PutFriendsDataEvent {
    public List<Friend> massage;

    public PutFriendsDataEvent(List<Friend> massage) {
        this.massage = massage;
    }
}
