package com.example.myhealthguide;

public class Day {
    public String name;
    public boolean check;

    public Day(String name, boolean check) {
        this.name = name;
        this.check = check;
    }
    public Day(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public boolean isCheck() {
        return check;
    }
}
