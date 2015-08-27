package com.example.mikola11.vkview2.event;

import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;

import java.util.List;

public class PutFriendsDataEvent {
    public List<FriendsResponseWrapper.Friend> massage;

    public PutFriendsDataEvent(List<FriendsResponseWrapper.Friend> massage) {
        this.massage = massage;
    }
}
