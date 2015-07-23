package com.example.mikola11.vkview2.event;


public class RequestPhotosAlbumDataEvent {
    public int massageUserId;
    public int massageAlbumId;

    public RequestPhotosAlbumDataEvent(int massageUserId, int massageAlbumId) {
        this.massageUserId = massageUserId;
        this.massageAlbumId = massageAlbumId;
    }
}
