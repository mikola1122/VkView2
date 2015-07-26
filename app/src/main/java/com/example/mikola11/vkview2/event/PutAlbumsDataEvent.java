package com.example.mikola11.vkview2.event;


import com.example.mikola11.vkview2.api.entity.Album;

import java.util.List;

public class PutAlbumsDataEvent {
    public List<Album> massage;

    public PutAlbumsDataEvent(List<Album> massage) {
        this.massage = massage;
    }
}
