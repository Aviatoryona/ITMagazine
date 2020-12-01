package com.magazineapp.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String category;
    private String pwd;
    private String date;

    public User() {
    }

    /**
     *
     * @param id id
     * @param name name
     * @param email email
     * @param category 0 Marketing 1 Editor 2 processing 3 Accounting 4 Advertiser 5 Journalist 6 photographer
     * @param pwd password
     * @param date date
     */
    public User(int id, String name, String email, String category, String pwd, String date) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setCategory(category);
        this.setPwd(pwd);
        this.setDate(date);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
