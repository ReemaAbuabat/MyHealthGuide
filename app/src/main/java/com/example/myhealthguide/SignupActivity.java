package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignupActivity extends BaseActivity {

    private EditText userName, email, password, confirmPassword;
    private Button signup;
    private TextView haveAccount;
    private FirebaseAuth firebaseAuth;
    private String name, pass, cpass, mail;
    private String TAG = SignupActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private ArrayList<Medication> medications = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.signupName);
        email = findViewById(R.id.signupEmail);
        password = findViewById(R.id.signupPass);
        confirmPassword = findViewById(R.id.signupcPass);
        signup = findViewById(R.id.signupBtn);
        haveAccount = findViewById(R.id.have_account);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // validate email and password
                //if valid :

                if (validate()) {
                    String userEmail = email.getText().toString().trim();
                    String userPass = password.getText().toString().trim();
                    progressDialog.setMessage(getString(R.string.Please_wait));
                    progressDialog.show();


                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override

                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        sendEmailVerificationAction();

                                    } else {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthUserCollisionException existEmail) {

                                            progressDialog.dismiss();
                                            wrongInfoDialog(getString(R.string.email_Exist));

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }//end else block
                                }//End onComplete()
                            });
                }//End if block

            }//End onClick()
        });
    }//End onCreate()

    private boolean validate() {


        name = userName.getText().toString();
        pass = password.getText().toString();
        cpass = confirmPassword.getText().toString();
        mail = email.getText().toString();
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        //password match
        //email verified

        if (name.isEmpty() || pass.isEmpty() || cpass.isEmpty() || mail.isEmpty()) {

            wrongInfoDialog(getString(R.string.MissingField));

        } else {
            if (!mail.matches(regex)) {

                wrongInfoDialog(getString(R.string.emailFormat));

            } else {
                if (pass.length() < 6) {
                    //todo: make dialog!

                    wrongInfoDialog(getString(R.string.password6Char));

//
                } else {
                    if (!pass.equals(cpass)) {

                        wrongInfoDialog(getString(R.string.matchPass));


                    } else
                        return true;

                }
            }


        }
        return false;
    }


    private void sendEmailVerificationAction() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        //todo: make dialog
                        progressDialog.dismiss();
                        Dialog(getString(R.string.EmailVer));

                        // Toast.makeText(SignupActivity.this, "Verification email has been sent!", Toast.LENGTH_LONG).show();

                    } else {
                        progressDialog.dismiss();
                        wrongInfoDialog(getString(R.string.EmailVerNot));
                    }
                }
            });
        }
    }//End sendEmailVerificationAction()

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        ArrayList<Integer> hr = new ArrayList<>();
        ArrayList<Integer> min = new ArrayList<>();
        hr.add(0);
        min.add(0);
        ArrayList<Day> days = new ArrayList<>();
        days.add(new Day("", true));

        Medication medication = new Medication("0", "firstEmptyOne", "", "", 0, hr, min, days);
        medications.add(medication);
        ArrayList<Favourite> favourites = new ArrayList<>();
        favourites.add(new Favourite("0", "firstEmptyOne"));

        User user = new User(mail, name, medications, favourites);

        databaseReference.setValue(user);
//        databaseReference.setValue(medication);
//        String id = databaseItem.p


    }//End sendUserData()

    private void wrongInfoDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title


        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                finish();
                Intent intent;
                intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()


}
