package com.example.myhealthguide;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String userEmail;
    public String name;
    public ArrayList<Medication> medicationList = new ArrayList<>();


    public User(String userEmail, String name, ArrayList<Medication> medicationList) {
        this.userEmail = userEmail;
        this.name = name;
        this.medicationList = medicationList;
    }

    //    public User(String userEmail, String name, ArrayList) {
//        this.userEmail = userEmail;
//        this.name = name;
//
//
//    }//End constructor
//    public void addMedication(Medication med){
//        medicationList.add(med);
//    }

}//End class
