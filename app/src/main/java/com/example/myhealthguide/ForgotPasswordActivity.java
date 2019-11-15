package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText forgetPassMailET;
    private Button resetBtn;
    private TextView backToLoginBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermail = forgetPassMailET.getText().toString().trim();

                if (usermail.equals("")) {
                    wrongInfoDialog(getString(R.string.registered_email_address));

                } else {
                    firebaseAuth.sendPasswordResetEmail(usermail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        wrongInfoDialog(getString(R.string.reset_pass));
                                        finish();
                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                    } else {
                                        wrongInfoDialog(getString(R.string.error_reset_pass));

                                    }
                                }
                            });
                }
            }
        });

    }//End onCreate()

    private void init() {
        forgetPassMailET = findViewById(R.id.forgetPassEmailET);
        resetBtn = findViewById(R.id.resetButton);
        backToLoginBtn = findViewById(R.id.backToLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }//End init()

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


}//End class
