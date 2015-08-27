package com.example.mikola11.vkview2.event;


import android.graphics.Bitmap;

public class GoToPhotoActivityEvent {
    public int[] massageLocation;
    public int massagePosition;
    public int massageWidth;
    public int massageHeight;
    public String[] massageUrl;
//    public Bitmap massageBitmap;

    public GoToPhotoActivityEvent(int massagePosition, String[] massageUrl, int[] massageLocation,
                                  int massageWidth, int massageHeight) {
        this.massagePosition = massagePosition;
        this.massageUrl = massageUrl;
        this.massageLocation = massageLocation;
        this.massageWidth = massageWidth;
        this.massageHeight = massageHeight;
//        this.massageBitmap = massageBitmap;
    }
}
