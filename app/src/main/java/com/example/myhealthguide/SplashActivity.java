package com.example.myhealthguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    ConstraintLayout splashL;
    private FirebaseAuth firebaseAuth;
  //  private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashL = findViewById(R.id.splashll);
      //  splashL.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        //checks with the database if the user is already logged into our app or not
        final FirebaseUser user = firebaseAuth.getCurrentUser();



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (user != null ){
                    finish();
                    Intent i = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(SplashActivity.this, LoginSignup.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);

    }//End onCreate()

//    @Override
//    public void onClick(View view) {
//        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//        startActivity(intent);
//    }



}//End class


