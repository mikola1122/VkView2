package com.example.mikola11.vkview2.event;


import android.net.Uri;

public class SendBitmapPhotoToShareEvent {
    public Uri massage;

    public SendBitmapPhotoToShareEvent(Uri massage) {
        this.massage = massage;
    }
}
