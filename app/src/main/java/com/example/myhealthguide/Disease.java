package com.example.myhealthguide;

public class Disease {


    private String allowed, notAllowed, name;
    private int img;

    public Disease(String allowed, String notAllowed, String name, int img) {
        this.allowed = allowed;
        this.notAllowed = notAllowed;
        this.name = name;
        this.img = img;
    }

    public String getAllowed() {
        return allowed;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public String getNotAllowed() {
        return notAllowed;
    }

    public void setNotAllowed(String notAllowed) {
        this.notAllowed = notAllowed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
