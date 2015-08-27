package com.example.mikola11.vkview2.event;


import com.example.mikola11.vkview2.api.entity.AlbumsResponseWrapper;

import java.util.List;

public class PutAlbumsDataEvent {
    public List<AlbumsResponseWrapper.Album> massage;

    public PutAlbumsDataEvent(List<AlbumsResponseWrapper.Album> massage) {
        this.massage = massage;
    }
}
