package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicationInformation extends AppCompatActivity {
    private String id;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    TextView name,instruction,dose,time;
    ImageView medImage;
    DatabaseReference medicationReference;
    CheckBox sun,mon,tue,wed,thu,fri,sat;
    Medication medication;
    String hr,min;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_information);
        initToolBar();
        getExtras();
        progressDialog = new ProgressDialog(this);
        init();

        getDataDetailProduct();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicationInformation.this);

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to delete this medication?");

                //Setting Negative "ok" Button
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMed();

                        finish();

                    }//end onClick
                });//end setPositiveButton

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();

            }//End onClick()
        });




    }

    private void deleteMed() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference med = FirebaseDatabase.getInstance().getReference().child(userId).child("medicationList").child(id);
         med.removeValue();

    }

    private void init() {
        name = findViewById(R.id.med_name);
        instruction = findViewById(R.id.med_sp);
        dose = findViewById(R.id.med_dose_med);
        time = findViewById(R.id.med_time);
        medImage = findViewById(R.id.med_pic_info);
        delete = findViewById(R.id.delete_med);
        sun = findViewById(R.id.sunm);
        mon = findViewById(R.id.monm);
        tue = findViewById(R.id.tuem);
        wed = findViewById(R.id.wedm);
        thu = findViewById(R.id.thum);
        fri = findViewById(R.id.frim);
        sat = findViewById(R.id.satm);
        sun.setChecked(false);
        mon.setChecked(false);
        tue.setChecked(false);
        wed.setChecked(false);
        thu.setChecked(false);
        fri.setChecked(false);
        sat.setChecked(false);

    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            id = (String) intent.getSerializableExtra("id");

        }//End of if

    }//End of getExtras()
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_medInfo);
        toolbar.setTitle("Medication information");
        setSupportActionBar(toolbar);
        //set toolbar back Button
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }//End of onClick()
        });//End of OnClickListener()

    }//End of initToolBar();
    private void getDataDetailProduct() {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        medicationReference = FirebaseDatabase.getInstance().getReference().child(userId).child("medicationList");
        medicationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String idMed = postSnapshot.getKey();
                    Log.d("test",idMed);
                    Medication m = postSnapshot.getValue(Medication.class);
                    if(!(m.getMedName().equals("firstEmptyOne")) && (m.getId().equals(id)) ){
                        medication = m;
                        Log.d("test",medication.getMedName());
                        break;
                    }

                }
                setView();
                progressDialog.hide();



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.hide();
            }
        });
    }

    private void setView() {
        name.setText(medication.getMedName());
        if(medication.getMedInstruction().length() == 0)
        {
            instruction.setText("Nothing");
        }else{
            instruction.setText(medication.getMedInstruction());
        }

        dose.setText(String.valueOf(medication.getDose()));
        hr = String .valueOf(medication.getHr().get(0));
        min = String .valueOf(medication.getMin().get(0));
        if(hr.length() == 1)
        {
            hr = "0"+hr;
        }
        if(min.length()==1)
        {
            min = "0"+min;
        }

        time.setText(hr+":"+min);

        Bitmap bitmap = StringToBitMap(medication.getMedImg());
        medImage.setImageBitmap(bitmap);
        ArrayList<Day> days = medication.getDays();

        for(Day d : days ){
            if(d.getName().equals("sun") && d.isCheck()){
                sun.setChecked(true);
            }
            if(d.getName().equals("mon") && d.isCheck()){
                mon.setChecked(true);
            }
            if(d.getName().equals("tue") && d.isCheck()){
                tue.setChecked(true);
            }
            if(d.getName().equals("wed") && d.isCheck()){
                wed.setChecked(true);
            }
            if(d.getName().equals("thu") && d.isCheck()){
                thu.setChecked(true);
            }
            if(d.getName().equals("fri") && d.isCheck()){
                fri.setChecked(true);
            }
            if(d.getName().equals("sat") && d.isCheck()) {
                sat.setChecked(true);
            }
        }


    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
