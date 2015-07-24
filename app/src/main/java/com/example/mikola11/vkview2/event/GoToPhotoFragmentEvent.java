package com.example.mikola11.vkview2.event;


public class GoToPhotoFragmentEvent {
    public int massagePosition;
    public String[] massageUrl;

    public GoToPhotoFragmentEvent(int massagePosition, String[] massageUrl) {
        this.massagePosition = massagePosition;
        this.massageUrl = massageUrl;
    }
}
