package com.example.mikola11.vkview2.api.entity;


import java.util.ArrayList;

public class FriendsResponseWrapper {
    private FriendsResponse response;

    public FriendsResponse getResponse() {
        return response;
    }
    public void setResponse(FriendsResponse response) {
        this.response = response;
    }

    public static class FriendsResponse {
        private int count;
        private ArrayList<Friend> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ArrayList<Friend> getItems() {
            return items;
        }

        public void setItems(ArrayList<Friend> items) {
            this.items = items;
        }
    }

    public static class Friend {
        private int id;
        private String first_name;
        private String last_name;
        private String photo_100;
        private String deactivated;

        public String getDeactivated() {
            return deactivated;
        }

        public void setDeactivated(String deactivated) {
            this.deactivated = deactivated;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPhoto_100() {
            return photo_100;
        }

        public void setPhoto_100(String photo_100) {
            this.photo_100 = photo_100;
        }

        public String getFullName() {
            return first_name + " " + last_name;
        }
    }
}
