package com.magazineapp.models;

import java.io.Serializable;

public class Story implements Serializable {

    private int id;
    /**
     * status: 0 pending 1 approved 2 declined
     */
    private int status;
    private int paid;
    private int user_id;
    private String title;
    private String desc;
    private String photoIds;
    private String date;

    public Story() {
    }

    public Story(int id, int status, int paid, int user_id, String title, String desc, String photoIds, String date) {
        this.setId(id);
        this.setStatus(status);
        this.setPaid(paid);
        this.setUser_id(user_id);
        this.setTitle(title);
        this.setDesc(desc);
        this.setPhotoIds(photoIds);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(String photoIds) {
        this.photoIds = photoIds;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
