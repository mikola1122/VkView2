package com.example.mikola11.vkview2.event;


import com.example.mikola11.vkview2.api.entity.PhotosAlbumResponseWrapper;

import java.util.List;

public class PutPhotosAlbumDataEvent {
    public List<PhotosAlbumResponseWrapper.PhotoAlbum> massage;

    public PutPhotosAlbumDataEvent(List<PhotosAlbumResponseWrapper.PhotoAlbum> massage) {
        this.massage = massage;
    }
}
