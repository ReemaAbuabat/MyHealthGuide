package com.example.myhealthguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LanguageActivity extends BaseActivity {

    Toolbar toolbar;
    private LinearLayout llArabic, llEnglish;
    private ImageView arabic, english;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_layout);

        initToolbar();
        //initializing the views
        initViews();
        checkLanguage();


    }//end onCreate

    private void initViews() {
        arabic = findViewById(R.id.Arabic_img);
        english = findViewById(R.id.English_img);

        arabic.setVisibility(INVISIBLE);
        english.setVisibility(INVISIBLE);

        llArabic = findViewById(R.id.arabic_layout);
        llEnglish = findViewById(R.id.english_layout);

        settingListeners();
    }//end initViews()

    private void settingListeners() {
        llArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arabic.setVisibility(VISIBLE);
                english.setVisibility(INVISIBLE);
                setLanguage("ar");
            }//end onClick
        });//end arabic layout
        llEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arabic.setVisibility(INVISIBLE);
                english.setVisibility(VISIBLE);
                setLanguage("en");
            }//end onClick
        });//end english layout
    }//end settingListeners()

    private void checkLanguage() {
        Locale locale;
        locale = getResources().getSystem().getConfiguration().locale;
        String language = MySharedPrefrance.getString(this, Constants.Keys.APP_LANGUAGE, locale.getLanguage());

        if (language.equals("en")) {
            arabic.setVisibility(INVISIBLE);
            english.setVisibility(VISIBLE);
        } //end if

        else {
            arabic.setVisibility(VISIBLE);
            english.setVisibility(INVISIBLE);
        }//end else


    }//End of checkLanguage


    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.language));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }//end onClick()
        });//end setting backBtn
    }//end initToolbar()

    public void setLanguage(String language) {
        MySharedPrefrance.putString(this, Constants.Keys.APP_LANGUAGE, language);
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }//end setLanguage

}//end class



