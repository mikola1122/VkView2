package com.example.mikola11.vkview2.api.entity;


import java.util.ArrayList;

public class CommunitiesResponseWrapper {
    private CommunitiesResponse response;

    public CommunitiesResponse getResponse() {
        return response;
    }

    public void setResponse(CommunitiesResponse response) {
        this.response = response;
    }

    public class CommunitiesResponse{
        private int count;
        private ArrayList<Communities> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ArrayList<Communities> getItems() {
            return items;
        }

        public void setItems(ArrayList<Communities> items) {
            this.items = items;
        }
    }

    public class Communities{
        private int id;
        private String name;
        private String screen_name;
        private String is_closed;
        private String type;
        private String is_member;
        private String photo_50;
        private String photo_100;
        private String photo_200;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getIs_closed() {
            return is_closed;
        }

        public void setIs_closed(String is_closed) {
            this.is_closed = is_closed;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_member() {
            return is_member;
        }

        public void setIs_member(String is_member) {
            this.is_member = is_member;
        }

        public String getPhoto_50() {
            return photo_50;
        }

        public void setPhoto_50(String photo_50) {
            this.photo_50 = photo_50;
        }

        public String getPhoto_100() {
            return photo_100;
        }

        public void setPhoto_100(String photo_100) {
            this.photo_100 = photo_100;
        }

        public String getPhoto_200() {
            return photo_200;
        }

        public void setPhoto_200(String photo_200) {
            this.photo_200 = photo_200;
        }
    }

    private String is_admin;
}
