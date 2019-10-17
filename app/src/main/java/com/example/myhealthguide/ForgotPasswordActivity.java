package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

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

            if(usermail.equals("")){
                Toast.makeText(ForgotPasswordActivity.this,"Please enter your registered email address",Toast.LENGTH_LONG).show();
                 } else {
                firebaseAuth.sendPasswordResetEmail(usermail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPasswordActivity.this,"Password reset email sent!",Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                                }else {
                                    Toast.makeText(ForgotPasswordActivity.this,"Error in sending password reset email!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });

    }//End onCreate()

    private void init(){
        forgetPassMailET = findViewById(R.id.forgetPassEmailET);
        resetBtn = findViewById(R.id.resetButton);
        backToLoginBtn = findViewById(R.id.backToLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }//End init()

}//End class
