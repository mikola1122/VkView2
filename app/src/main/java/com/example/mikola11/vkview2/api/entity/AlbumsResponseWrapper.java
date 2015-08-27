package com.example.mikola11.vkview2.api.entity;

import java.util.ArrayList;

public class AlbumsResponseWrapper {
    private AlbumsResponse response;

    public AlbumsResponse getResponse() {
        return response;
    }

    public void setResponse(AlbumsResponse response) {
        this.response = response;
    }

    public static class AlbumsResponse {
        private int count;
        private ArrayList<Album> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ArrayList<Album> getItems() {
            return items;
        }

        public void setItems(ArrayList<Album> items) {
            this.items = items;
        }
    }

    public static class Album {
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
}
