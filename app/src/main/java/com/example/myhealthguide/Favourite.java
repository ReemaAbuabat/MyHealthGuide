package com.example.myhealthguide;

public class Favourite {
    public String id;
    public  String favName;

    public Favourite(String id, String favName) {
        this.id = id;
        this.favName = favName;
    }
    public Favourite(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavName() {
        return favName;
    }

    public void setFavName(String favName) {
        this.favName = favName;
    }
}
