package com.example.myhealthguide;

import java.util.ArrayList;

public class Medication {

    public String id;
    public String medName;
    public String medInstruction;
    public String medImg;
    public int dose;
    public ArrayList<Day> days = new ArrayList<>();
    public ArrayList<Integer> hr = new ArrayList<>();
    public ArrayList<Integer> min = new ArrayList<>();

    public Medication(String id, String medName, String medInstruction, String medImg, int dose, ArrayList<Integer> hr, ArrayList<Integer> min, ArrayList<Day> days) {
        this.id = id;
        this.medName = medName;
        this.medInstruction = medInstruction;
        this.medImg = medImg;
        this.dose = dose;
        this.hr = hr;
        this.min = min;
        this.days = days;
    }

    public Medication() {

    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public void setMedInstruction(String medInstruction) {
        this.medInstruction = medInstruction;
    }

    public void setMedImg(String medImg) {
        this.medImg = medImg;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public void setHr(ArrayList<Integer> hr) {
        this.hr = hr;
    }

    public void setMin(ArrayList<Integer> min) {
        this.min = min;
    }

    public Medication(String medName) {
        this.medName = medName;

    }

    public String getId() {
        return id;
    }

    public String getMedName() {
        return medName;
    }

    public String getMedInstruction() {
        return medInstruction;
    }

    public String getMedImg() {
        return medImg;
    }

    public int getDose() {
        return dose;
    }

    public ArrayList<Integer> getHr() {
        return hr;
    }

    public ArrayList<Integer> getMin() {
        return min;
    }
}
