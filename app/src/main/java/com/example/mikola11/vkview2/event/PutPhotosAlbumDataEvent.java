package com.example.mikola11.vkview2.event;


import com.example.mikola11.vkview2.api.entity.PhotoAlbum;

import java.util.List;

public class PutPhotosAlbumDataEvent {
    public List<PhotoAlbum> massage;

    public PutPhotosAlbumDataEvent(List<PhotoAlbum> massage) {
        this.massage = massage;
    }
}