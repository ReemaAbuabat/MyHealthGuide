package com.example.myhealthguide;

public class Row {

    String name;
    int thumbnail;
    int numOfallowdance;

    public Row(String name, int thumbnail, int numOfallowdance) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.numOfallowdance = numOfallowdance;
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

    public int getNumOfallowdance() {
        return numOfallowdance;
    }

    public void setNumOfallowdance(int numOfallowdance) {
        this.numOfallowdance = numOfallowdance;
    }
}

