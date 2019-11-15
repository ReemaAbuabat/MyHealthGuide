package com.example.myhealthguide;


public class Row {

    String name;
    String dName;
    int thumbnail;
    String numOfallowdance, details;

    public Row(String name, String numOfallowdance, int thumbnail, String details, String dName) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.numOfallowdance = numOfallowdance;
        this.details = details;
        this.dName = dName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNumOfallowdance() {
        return numOfallowdance;
    }

    public void setNumOfallowdance(String numOfallowdance) {
        this.numOfallowdance = numOfallowdance;
    }
}

