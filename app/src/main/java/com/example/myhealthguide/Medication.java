package com.example.myhealthguide;

public class Medication {


    String medName;
    String medInstruction;
    String medImg;
    int dose;
    int[] hr,min;


    public Medication( String medName, String medInstruction, String medImg, int dose, int[] hr, int[] min) {

        this.medName = medName;
        this.medInstruction = medInstruction;
        this.medImg = medImg;
        this.dose = dose;
        this.hr = hr;
        this.min = min;
    }
}
