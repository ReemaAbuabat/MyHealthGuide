package com.example.myhealthguide;

public class Medication {

    public long medId;
    String medName;
    String medInstruction;
    String medImg;
    int dose;
    int[] hr,min;


    public Medication(long medId, String medName, String medInstruction, String medImg, int dose, int[] hr, int[] min) {
        this.medId = medId;
        this.medName = medName;
        this.medInstruction = medInstruction;
        this.medImg = medImg;
        this.dose = dose;
        this.hr = hr;
        this.min = min;
    }
}
