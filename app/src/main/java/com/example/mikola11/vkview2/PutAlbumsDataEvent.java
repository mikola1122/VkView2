package com.example.mikola11.vkview2;


import com.example.mikola11.vkview2.ui.albums.Album;

import java.util.List;

public class PutAlbumsDataEvent {
    public List<Album> massage;

    public PutAlbumsDataEvent(List<Album> massage) {
        this.massage = massage;
    }
}
