package com.magazineapp.models;

import java.io.Serializable;

public class Photo implements Serializable {

    private int id;
    private int status;
    private int user_id;
    private String desc;
    private String url;
    private String date;


    public Photo() {
    }

    public Photo(int id, int status, int user_id, String desc, String url, String date) {
        this.setId(id);
        this.setStatus(status);
        this.setUser_id(user_id);
        this.setDesc(desc);
        this.setUrl(url);
        this.setDate(date);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
