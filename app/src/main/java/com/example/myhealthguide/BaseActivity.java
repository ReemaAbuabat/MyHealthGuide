package com.example.myhealthguide;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleDefaultAppLocale();

    }//end onCreate()

    private void handleDefaultAppLocale() {
        setAppLocale(MySharedPrefrance.getString(this, Constants.Keys.APP_LANGUAGE, getSystemLocalLanguage()));

    }//end handleDefaultAppLocale()

    private void setAppLocale(String app_language) {
        //set new lang pref
        MySharedPrefrance.putString(this, Constants.Keys.APP_LANGUAGE, app_language);
        //change lang configuration
        Locale locale = new Locale(app_language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

    }//end setAppLocale(String app_language)

    private String getSystemLocalLanguage() {
        Locale locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getSystem().getConfiguration().getLocales().get(0);
        }//end if
        else {
            locale = getResources().getSystem().getConfiguration().locale;
        }//end else
        return locale.getLanguage();

    }//end getSystemLocalLanguage()


}//end of class
