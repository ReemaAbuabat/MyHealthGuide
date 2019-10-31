package com.example.myhealthguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class addMedication extends AppCompatActivity {

    TextView chooseTime;
    ImageView camera;
    ImageView time;
    ImageView personalImg, plusBtn, subBtn;
    String selectedImg;
    Button addBtn;
    EditText nameText, specialText;
    TextView numOfMedText;
    String name, special,numOfMed, timeClock;
    int counter = 1;
    public ArrayList<Integer> hrMed = new ArrayList<>();
    public ArrayList<Integer> minMed= new ArrayList<>();
    public ArrayList<Day> days = new ArrayList<>();
    private FirebaseUser user;
    Medication medication;
    CheckBox sun,mon,tue,wed,thu,fri,sat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        initToolBar();
        init();

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(addMedication.this, R.style.TimePickerTheme,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseTime.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);

                timePickerDialog.show();


            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAdd();

            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter < 4)
                {
                    counter++;
                    numOfMedText.setText(""+counter+"");
                }
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter !=1){
                    counter--;
                    numOfMedText.setText(""+counter+"");
                }
            }
        });


    }

        private void validateAdd() {
            name = nameText.getText().toString();
            special = specialText.getText().toString();
            numOfMed = numOfMedText.getText().toString();
            timeClock = chooseTime.getText().toString();


            if(name.isEmpty() || timeClock.isEmpty()  )
            {
                wrongInfoDialog("Missing field");
            }else {
                    if(selectedImg==null) {
                        wrongInfoDialog("you have to choose a photo");

                    }
                    else{
                        if(sun.isChecked() || mon.isChecked() || tue.isChecked() || wed.isChecked() || thu.isChecked() || fri.isChecked() || sat.isChecked()) {
                            int numOfMedicatoin = parseInt(numOfMed);
//                            switchMed(timeClock, numOfMedicatoin);
//                            for (int i = 0; i < hr.length; i++) {
//                                hrMed.add(hr[i]);
//                                minMed.add(min[i]);
//                            }
                            int index = timeClock.indexOf(':');
                            int hour = parseInt(timeClock.substring(0,index));
                            hrMed.add(hour);
                            int minutes = parseInt(timeClock.substring(index+1));
                            minMed.add(minutes);

                            checkDays();
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();


                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference myUser = reference.child(userId);
                            DatabaseReference medicationReference = myUser.child("medicationList");

                            String id = medicationReference.push().getKey();
                            medication = new Medication(id, name, special, selectedImg, numOfMedicatoin, hrMed, minMed, days);
                            medicationReference.child(id).setValue(medication);
                            Dialog("added Successfully !");


                        }
                        else{
                            wrongInfoDialog("you have to choose at least one day!");
                        }

                    }

            }



        }

    private void checkDays() {
        if(sun.isChecked())
        {   Day day = new Day("sun",true);
            days.add(day);
        }else{
            Day day = new Day("sun",false);
            days.add(day);
        }
        if(mon.isChecked())
        {   Day day = new Day("mon",true);
            days.add(day);
        }else{
            Day day = new Day("mon",false);
            days.add(day);
        }
        if(tue.isChecked())
        {   Day day = new Day("tue",true);
            days.add(day);
        }else{
            Day day = new Day("tue",false);
            days.add(day);
        }
        if(wed.isChecked())
        {   Day day = new Day("wed",true);
            days.add(day);
        }else{
            Day day = new Day("wed",false);
            days.add(day);
        }
        if(thu.isChecked())
        {   Day day = new Day("thu",true);
            days.add(day);
        }else{
            Day day = new Day("thu",false);
            days.add(day);
        }
        if(fri.isChecked())
        {   Day day = new Day("fri",true);
            days.add(day);
        }else{
            Day day = new Day("fri",false);
            days.add(day);
        }if(sat.isChecked())
        {   Day day = new Day("sat",true);
            days.add(day);
        }else{
            Day day = new Day("sat",false);
            days.add(day);
        }

    }

//    private void switchMed(String timeClock, int numOfMedicatoin) {
//
//        hr = new Integer[numOfMedicatoin];
//        min = new Integer[numOfMedicatoin];
//        switch (numOfMedicatoin)
//        {
//            case 1:
//            {
//                int index = timeClock.indexOf(':');
//                int hour = parseInt(timeClock.substring(0,index));
//                hr[0] = hour;
//                int minutes = parseInt(timeClock.substring(index+1));
//                min[0] = minutes;
//                break;
//
//            }
//            case 2:
//            {
//                int index = timeClock.indexOf(':');
//                int hour = parseInt(timeClock.substring(0,index));
//                hr[0] = hour;
//                int minutes = parseInt(timeClock.substring(index+1));
//                min[0] = minutes;
//
//                if(hour < 12)
//                {
//                    hr[1]= hour+12;
//                    min[1]=minutes;
//                }
//                else{
//                    hr[1]= hour-12;
//                    min[1]=minutes;
//                }
//                break;
//
//            }
//            case 3:
//            {
//                int index = timeClock.indexOf(':');
//                int hour = parseInt(timeClock.substring(0,index));
//                hr[0] = hour;
//                int minutes = parseInt(timeClock.substring(index+1));
//                min[0] = minutes;
//
//                if(hour < 8)
//                {
//                    hr[1]= hour+8;
//                    min[1]=minutes;
//
//                    hr[2] = hr[1]+8;
//                    min[2]=minutes;
//
//
//                }
//                else{
//                    hr[1]= hour-8;
//                    min[1]=minutes;
//
//                    hr[2] = hr[1]-8;
//                    min[2]=minutes;
//                }
//                break;
//
//            }
//            case 4:
//            {
//                int index = timeClock.indexOf(':');
//                int hour = parseInt(timeClock.substring(0,index));
//                hr[0] = hour;
//                int minutes = parseInt(timeClock.substring(index+1));
//                min[0] = minutes;
//
//                if(hour < 6)
//                {
//                    hr[1]= hour+6;
//                    min[1]=minutes;
//
//                    hr[2] = hr[1]+6;
//                    min[2]=minutes;
//
//                    hr[3] = hr[2]+6;
//                    min[3]=minutes;
//
//
//                }
//                else{
//                    hr[1]= hour-6;
//                    min[1]=minutes;
//
//                    hr[2] = hr[1]-6;
//                    min[2]=minutes;
//
//                    hr[3] = hr[2]-6;
//                    min[3]=minutes;
//                }
//                break;
//
//            }
//        }
//    }

    public void init() {
        chooseTime = findViewById(R.id.etChooseTime);
        camera = findViewById(R.id.camera_btn);
        time = findViewById(R.id.time_clock);
        personalImg = findViewById(R.id.profile_pic);
        addBtn = findViewById(R.id.addMed);
        nameText = findViewById(R.id.name_med);
        specialText = findViewById(R.id.special);
        numOfMedText = findViewById(R.id.numOfT);
        plusBtn = findViewById(R.id.plus);
        subBtn = findViewById(R.id.sub);
        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thu = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);



    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addMed);
        toolbar.setTitle("Add Medication");
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

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        TextView title = new TextView(this);
        title.setText("Add Photo!");
        title.setBackgroundColor(getColor(R.color.colorPrimary));
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(title);


        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    captureFromCamera();
                }//End of if
                else if (items[item].equals("Choose from Library")) {
                    pickFromGallery();
                }//End of else if
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }//End of else if
            }//End of onClick()
        });
        builder.show();
    }//End of selectImage()

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, Constants.Keys.GALLERY_REQUEST_CODE);
    }//End of pickFromGallery()

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            //End of if
            if (requestCode == Constants.Keys.CAMERA_REQUEST_CODE || requestCode == Constants.Keys.GALLERY_REQUEST_CODE)
                try {
                    final Bitmap bitmap;
                    if (data.getData() == null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                    }//End of if
                    else {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    }//End of else


                    selectedImg = BitMapToString(bitmap);
                    personalImg.setImageBitmap(bitmap);




                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }//End of catch
                catch (IOException e) {
                    e.printStackTrace();
                }//End of catch

    }//End of onActivityResult()


    private void captureFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, Constants.Keys.CAMERA_REQUEST_CODE);
        }

    }
    private void wrongInfoDialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle(R.string.Wrong);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    private void Dialog(String msg) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
        // Setting Dialog Title


        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();


            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()


}
