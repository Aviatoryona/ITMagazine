package com.magazineapp.models;

import java.io.Serializable;

public class Advert implements Serializable {
    private int id;
    private int user_id;
    /**
     * status: 0 pending 1 processing 2 approved 3 declined
     */
    private int status;
    private String title;
    private String description;
    private String image;
    private String date;
    private int paid;
    private String position;

    public Advert() {
    }

    /**
     *
     * @param id
     * @param user_id
     * @param status
     * @param title
     * @param description
     * @param image
     * @param date
     * @param paid
     * @param position
     */
    public Advert(int id, int user_id, int status, String title, String description, String image, String date, int paid, String position) {
        this.setId(id);
        this.setUser_id(user_id);
        this.setStatus(status);
        this.setTitle(title);
        this.setDescription(description);
        this.setImage(image);
        this.setDate(date);
        this.setPaid(paid);
        this.setPosition(position);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
