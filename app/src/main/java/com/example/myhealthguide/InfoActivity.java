package com.example.myhealthguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    String name, allowedFood,notAllowedFood;
    TextView allowedText,notAllowedText;
//    ScrollView allowedScroll;
//    TextView allowed_read;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        allowedText = findViewById(R.id.allowed_info);
        notAllowedText = findViewById(R.id.forbidden_info);

//        allowedScroll =findViewById(R.id.allowed_scroll);
//        allowed_read = findViewById(R.id.allowed_read);
//        allowed_read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               // expand(allowedScroll,400);
//                 allowedScroll.getLayoutParams().height = ScrollView.LayoutParams.MATCH_PARENT;
//            }
//        });

        getExtras();
        initToolBar();
    }
//    public static void expand(final View v, int targetHeight) {
//
//        int prevHeight  = v.getHeight();
//
//        v.setVisibility(View.VISIBLE);
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                v.getLayoutParams().height = (int) animation.getAnimatedValue();
//                v.requestLayout();
//            }
//        });
//        valueAnimator.setInterpolator(new DecelerateInterpolator());
////        valueAnimator.setDuration(duration);
//        valueAnimator.start();
//    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);
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
            allowedFood = (String) intent.getSerializableExtra("allowedFood");
            notAllowedFood = (String) intent.getSerializableExtra("notAllowedFood");

            allowedText.setText(allowedFood);
            notAllowedText.setText(notAllowedFood);


        }//End of if

    }//End of getExtras()
}
