package com.example.myhealthguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class MedicationInformation extends BaseActivity {
    private String id;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    EditText nameText, instruction;
    TextView chooseTime, dose;
    ImageView time, camera, plusBtn, subBtn, personalImg;
    DatabaseReference medicationReference;
    CheckBox sun, mon, tue, wed, thu, fri, sat;
    Medication medication;
    public ArrayList<Integer> hrMed = new ArrayList<>();
    public ArrayList<Integer> minMed = new ArrayList<>();
    public ArrayList<Day> days = new ArrayList<>();
    String PreImg;
    int counter;
    String hr, min;
    Button delete;
    String name, special, numOfDose, timeClock;
    String selectedImg;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_information);
        initToolBar();
        getExtras();
        progressDialog = new ProgressDialog(this);
        init();

        getDataDetailProduct();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicationInformation.this);

                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.updateMsg));

                //Setting Negative "ok" Button
                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (validate()) {
                            Dialog2(getString(R.string.sucessUpdate));
                        }


                    }//end onClick
                });//end setPositiveButton

                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicationInformation.this);

                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.deleteMsg));

                //Setting Negative "ok" Button
                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMed();

                        finish();

                    }//end onClick
                });//end setPositiveButton

                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();

            }//End onClick()
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MedicationInformation.this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
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

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter < 4) {
                    counter++;
                    dose.setText("" + counter + "");
                }
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 1) {
                    counter--;
                    dose.setText("" + counter + "");
                }
            }
        });


    }


    private Boolean validate() {

        name = nameText.getText().toString();
        special = instruction.getText().toString();
        numOfDose = dose.getText().toString();
        timeClock = chooseTime.getText().toString();


        if (name.isEmpty() || timeClock.isEmpty()) {
            wrongInfoDialog(getString(R.string.MissFields));
        } else if (sun.isChecked() || mon.isChecked() || tue.isChecked() || wed.isChecked() || thu.isChecked() || fri.isChecked() || sat.isChecked()) {
            int numOfMedicatoin = parseInt(numOfDose);

            if (selectedImg == null) {
                selectedImg = PreImg;
            }
            int index = timeClock.indexOf(':');
            int hour = parseInt(timeClock.substring(0, index));
            hrMed.add(hour);
            int minutes = parseInt(timeClock.substring(index + 1));
            minMed.add(minutes);

            checkDays();
            user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myUser = reference.child(userId);
            DatabaseReference medicationReference = myUser.child("medicationList");

            Medication medication1 = new Medication(id, name, special, selectedImg, numOfMedicatoin, hrMed, minMed, days);
            medicationReference.child(id).setValue(medication1);
            return true;


        } else {
            wrongInfoDialog(getString(R.string.dayWarning));
        }


        return false;


    }

    private void deleteMed() {
        progressDialog.setMessage(getString(R.string.Please_wait));
        progressDialog.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference med = FirebaseDatabase.getInstance().getReference().child(userId).child("medicationList").child(id);
        med.removeValue();

        Dialog(getString(R.string.successDelete));

    }

    private void checkDays() {
        if (sun.isChecked()) {
            Day day = new Day(getString(R.string.sun), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.sun), false);
            days.add(day);
        }
        if (mon.isChecked()) {
            Day day = new Day(getString(R.string.mon), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.mon), false);
            days.add(day);
        }
        if (tue.isChecked()) {
            Day day = new Day(getString(R.string.tue), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.tue), false);
            days.add(day);
        }
        if (wed.isChecked()) {
            Day day = new Day(getString(R.string.wed), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.wed), false);
            days.add(day);
        }
        if (thu.isChecked()) {
            Day day = new Day(getString(R.string.thur), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.thur), false);
            days.add(day);
        }
        if (fri.isChecked()) {
            Day day = new Day(getString(R.string.fri), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.fri), false);
            days.add(day);
        }
        if (sat.isChecked()) {
            Day day = new Day(getString(R.string.sat), true);
            days.add(day);
        } else {
            Day day = new Day(getString(R.string.sat), false);
            days.add(day);
        }

    }

    private void init() {
        nameText = findViewById(R.id.med_name);
        instruction = findViewById(R.id.med_sp);
        dose = findViewById(R.id.med_dose_med);
        chooseTime = findViewById(R.id.med_time);
        time = findViewById(R.id.time_clock_med);
        delete = findViewById(R.id.delete_med);
        camera = findViewById(R.id.camera_btn_med);
        plusBtn = findViewById(R.id.plus_med);
        subBtn = findViewById(R.id.sub_med);
        update = findViewById(R.id.update_med);
        personalImg = findViewById(R.id.med_pic_info);
        sun = findViewById(R.id.sunm);
        mon = findViewById(R.id.monm);
        tue = findViewById(R.id.tuem);
        wed = findViewById(R.id.wedm);
        thu = findViewById(R.id.thum);
        fri = findViewById(R.id.frim);
        sat = findViewById(R.id.satm);


    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            id = (String) intent.getSerializableExtra("id");

        }//End of if

    }//End of getExtras()

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_medInfo);
        toolbar.setTitle(getString(R.string.Medication_information));
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

        progressDialog.setMessage(getString(R.string.Please_wait));
        progressDialog.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        medicationReference = FirebaseDatabase.getInstance().getReference().child(userId).child("medicationList");
        medicationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String idMed = postSnapshot.getKey();
                    Log.d("test", idMed);
                    Medication m = postSnapshot.getValue(Medication.class);
                    if (!(m.getMedName().equals("firstEmptyOne")) && (m.getId().equals(id))) {
                        medication = m;
                        Log.d("test", medication.getMedName());
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
        nameText.setText(medication.getMedName());
        if (medication.getMedInstruction().length() == 0) {
            instruction.setText(getString(R.string.Nothing));
        } else {
            instruction.setText(medication.getMedInstruction());
        }

        dose.setText(String.valueOf(medication.getDose()));
        counter = medication.getDose();
        hr = String.valueOf(medication.getHr().get(0));
        min = String.valueOf(medication.getMin().get(0));
        if (hr.length() == 1) {
            hr = "0" + hr;
        }
        if (min.length() == 1) {
            min = "0" + min;
        }

        chooseTime.setText(hr + ":" + min);

        Bitmap bitmap = StringToBitMap(medication.getMedImg());
        PreImg = medication.getMedImg();
        personalImg.setImageBitmap(bitmap);
        ArrayList<Day> days = medication.getDays();

        for (Day d : days) {
            if (d.getName().equals(getString(R.string.sun)) && d.isCheck()) {
                sun.setChecked(true);
            }
            if (d.getName().equals(getString(R.string.mon)) && d.isCheck()) {
                mon.setChecked(true);
            }
            if (d.getName().equals(getString(R.string.tue)) && d.isCheck()) {
                tue.setChecked(true);
            }
            if (d.getName().equals(getString(R.string.wed)) && d.isCheck()) {
                wed.setChecked(true);
            }
            if (d.getName().equals(getString(R.string.thur)) && d.isCheck()) {
                thu.setChecked(true);
            }
            if (d.getName().equals(getString(R.string.fri)) && d.isCheck()) {
                fri.setChecked(true);
            }
            if (d.getName().equals(getString(R.string.sat)) && d.isCheck()) {
                sat.setChecked(true);
            }
        }


    }

    private void selectImage() {

        final CharSequence[] items = {getString(R.string.Take_Photo), getString(R.string.Choose_from_Library),
                getString(R.string.Cancel)};

        TextView title = new TextView(this);
        title.setText(getString(R.string.Add_Photo));
        title.setBackgroundColor(getColor(R.color.colorPrimary));
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCustomTitle(title);


        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(R.string.Take_Photo)) {
                    captureFromCamera();
                }//End of if
                else if (items[item].equals(R.string.Choose_from_Library)) {
                    pickFromGallery();
                }//End of else if
                else if (items[item].equals(R.string.Cancel)) {
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

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
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

    private void Dialog2(String msg) {
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
