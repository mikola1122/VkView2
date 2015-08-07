package com.example.mikola11.vkview2.event;


import android.graphics.Bitmap;

public class SendBitmapPhotoToShareEvent {
    public Bitmap massage;

    public SendBitmapPhotoToShareEvent(Bitmap massage) {
        this.massage = massage;
    }
}
