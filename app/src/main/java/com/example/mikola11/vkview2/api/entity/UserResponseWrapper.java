package com.example.mikola11.vkview2.api.entity;

import java.util.ArrayList;

public class UserResponseWrapper {
    private ArrayList<User> response;

    public ArrayList<User> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<User> response) {
        this.response = response;
    }

    public class User{
        private int id;
        private String first_name;
        private String last_name;
        private String photo_100;
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getFullName() {
            return first_name + " " + last_name;
        }
    }
}
