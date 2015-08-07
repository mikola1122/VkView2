package com.example.mikola11.vkview2.api.entity;

public class Album {
    private int id;
    private int thumb_id;
    private int owner_id;
    private int size;
    private String title;
    private String description;
    private String thumb_src;

    public String getThumb_src() {
        return thumb_src;
    }

    public void setThumb_src(String thumb_src) {
        this.thumb_src = thumb_src;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThumb_id() {
        return thumb_id;
    }

    public void setThumb_id(int thumb_id) {
        this.thumb_id = thumb_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
