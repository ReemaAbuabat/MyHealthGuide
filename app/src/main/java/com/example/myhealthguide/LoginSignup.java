package com.example.myhealthguide;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginSignup extends BaseActivity {

    Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        //init buttons
        loginBtn = findViewById(R.id.login1btn);
        signupBtn = findViewById(R.id.signup1btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignup.this, LoginActivity.class));
            }//End onClick()
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignup.this, SignupActivity.class));
            }//End onCLick()
        });


    }//End onCreate()
}//End class
