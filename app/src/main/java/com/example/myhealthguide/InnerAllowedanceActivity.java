package com.example.myhealthguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InnerAllowedanceActivity extends BaseActivity {

    String name, allowedance;
    TextView allowedanceTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_allowedance);
        allowedanceTXT = findViewById(R.id.allowedanceTXT);


        getExtras();
        initToolBar();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.moh.gov.sa/en/HealthAwareness/MedicalTools/Pages/default.aspx"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
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

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            name = (String) intent.getSerializableExtra("name");
            allowedance = (String) intent.getSerializableExtra("details");

            allowedanceTXT.setText(allowedance);


        }//End of if

    }//End of getExtras()

}
