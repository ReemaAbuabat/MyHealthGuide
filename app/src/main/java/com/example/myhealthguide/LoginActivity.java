package com.example.myhealthguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    private TextView forgotPass, createAccount;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //checks with the database if the user is already logged into our app or not
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//
//        if (user != null ){
//            finish();
//            Intent i = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(i);
//        }//End if block

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail, userPassword;
                userEmail = email.getText().toString();
                userPassword = password.getText().toString();
            validate(userEmail,userPassword);
            }//End onClick()
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }//End onClick()
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

    }//End onCreate()

    private void init(){
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPass);
        forgotPass = findViewById(R.id.forgetPass);
        createAccount = findViewById(R.id.createAccount);
        login = findViewById(R.id.loginBtn);
    }//End init()

    private void validate(String userMail, String userPass){
if( (!userMail.isEmpty()) && (!userPass.isEmpty())) {
    progressDialog.setMessage("Please wait...");
    progressDialog.show();
    firebaseAuth.signInWithEmailAndPassword(userMail, userPass)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        checkEmailVerification();
                    } else {

                        try {
                            throw task.getException();

                        }   // if user enters wrong password.
                        catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                            Log.d("LoginActivity", "onComplete: malformed_email");
                            progressDialog.dismiss();
                            wrongInfoDialog("Invalid email or password, please check and try again");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(LoginActivity.this,"something went wrong", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        wrongInfoDialog("Invalid email or password, please check and try again");
                    }

                }
            });
}
else
{
    wrongInfoDialog("Missing fields");
}

    }//end validate()

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean isVerified = firebaseUser.isEmailVerified();


        if(isVerified){
            finish();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            MySharedPrefrance.putString(this, "password", password.getText().toString());
            startActivity(i);
        }else{
            //todo: make dialog!
wrongInfoDialog("Please verify your email address");
firebaseAuth.signOut();
        }
    }//End checkEmailVerification()


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
                email.setText("");
                password.setText("");

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

}//End class
