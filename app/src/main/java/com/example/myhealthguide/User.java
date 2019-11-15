package com.example.myhealthguide;

import java.util.ArrayList;

public class User {

    public String userEmail;
    public String name;
    public ArrayList<Medication> medicationList = new ArrayList<>();
    public ArrayList<Favourite> favouriteArrayList = new ArrayList<>();

    public User(String userEmail, String name, ArrayList<Medication> medicationList, ArrayList<Favourite> favouriteArrayList) {
        this.userEmail = userEmail;
        this.name = name;
        this.medicationList = medicationList;
        this.favouriteArrayList = favouriteArrayList;
    }


}//End class
