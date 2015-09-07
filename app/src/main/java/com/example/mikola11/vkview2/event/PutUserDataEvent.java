package com.example.mikola11.vkview2.event;

public class PutUserDataEvent {
    public String massageUserFullName;
    public String massageUserPhotoLink;
    public String massageUserStatus;

    public PutUserDataEvent(String massageUserFullName, String massageUserPhotoLink, String massageUserStatus) {
        this.massageUserFullName = massageUserFullName;
        this.massageUserPhotoLink = massageUserPhotoLink;
        this.massageUserStatus = massageUserStatus;
    }
}
