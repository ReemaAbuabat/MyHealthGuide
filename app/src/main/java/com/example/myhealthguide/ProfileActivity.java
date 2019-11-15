package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends BaseActivity {

    private ImageView logout;
    Button language;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextInputEditText profileName, profileEmail, profilePassword;
    private ProgressDialog progressDialog;
    private Button changeBtn;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolBar();
        progressDialog = new ProgressDialog(this);
        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        profilePassword = findViewById(R.id.profile_password);
        language=findViewById(R.id.languageBtn);
        changeBtn = findViewById(R.id.change);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, LanguageActivity.class);
                startActivity(i);            }
        });


        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle(getString(R.string.passwordNew));

                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.enter_new_pass));
                final EditText input = new EditText(ProfileActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input); // uncomment this line

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton(getString(R.string.change),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                if (input.getText().toString().isEmpty()) {
                                    wrongInfoDialog(getString(R.string.MissFields));

                                } else {

                                    if (input.getText().toString().length() < 6) {
                                        wrongInfoDialog(getString(R.string.passMin));
                                    } else {
                                        progressDialog.setMessage(getString(R.string.Please_wait));
                                        progressDialog.show();
                                        changePass(input.getText().toString());
                                    }
                                }

                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton(getString(R.string.Cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                // closed

                // Showing Alert Message
                alertDialog.show();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();

        progressDialog.setMessage(getString(R.string.Please_wait));
        progressDialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = reference.child(userId);


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                email = dataSnapshot.child("userEmail").getValue().toString();
                profileEmail.setText(name);
                profileName.setText(email);
                setPass();

                progressDialog.hide();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);

                // Setting Dialog Message
                alertDialog.setMessage(getString(R.string.logoutMsg));

                //Setting Negative "ok" Button
                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        finish();
                        Intent intent = new Intent(ProfileActivity.this, LoginSignup.class);
                        startActivity(intent);
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

    }//End onClick()



    private void changePass(final String newPass) {
        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, pass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                progressDialog.hide();
                                wrongInfoDialog(getString(R.string.Something_went_wrong));
                            } else {
                                progressDialog.hide();
                                goodDialog(getString(R.string.pass_changed_successfully));

                            }
                        }
                    });
                } else {
                    progressDialog.hide();
                    wrongInfoDialog(getString(R.string.Authentication_Failed));
                }
            }
        });
    }


    private void setPass() {
        pass = MySharedPrefrance.getString(this, "password", "");

        profilePassword.setText(pass);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbar.setTitle(getString(R.string.Profile));
        setSupportActionBar(toolbar);
        //set toolbar back Button
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                onPause();
            }//End of onClick()
        });//End of OnClickListener()

    }//End of initToolBar();

    private void wrongInfoDialog(String msg) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle(R.string.Wrong);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    private void goodDialog(String msg) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
        // Setting Dialog Title


        // Setting Dialog Message
        alertDialog.setMessage(msg);


        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()
}//End class
