package com.example.myhealthguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseActivity {

    ConstraintLayout splashL;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashL = findViewById(R.id.splashll);

        firebaseAuth = FirebaseAuth.getInstance();
        //checks with the database if the user is already logged into our app or not
        final FirebaseUser user = firebaseAuth.getCurrentUser();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (user != null) {
                    finish();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginSignup.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);

    }//End onCreate()


}//End class


