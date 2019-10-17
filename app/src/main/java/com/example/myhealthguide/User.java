package com.example.myhealthguide;

import java.util.ArrayList;

public class User {

    public String userEmail;
    public String name;
    public ArrayList<Medication> medicationList;


    public User(String userEmail, String name) {
        this.userEmail = userEmail;
        this.name = name;


    }//End constructor
    public void addMedication(Medication med){
        medicationList.add(med);
    }

}//End class
