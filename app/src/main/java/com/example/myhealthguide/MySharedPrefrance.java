package com.example.myhealthguide;



import android.content.Context;
import android.content.SharedPreferences;




public class MySharedPrefrance {
    private static SharedPreferences prf;

    private MySharedPrefrance() {
    }//end private constructor "singleton"

    public static SharedPreferences getInstance(Context context) {
        if (prf == null) {
            prf = context.getSharedPreferences(String.valueOf("user_details"), Context.MODE_PRIVATE);
        }//end if

        return prf;
    }//end getInstance


    public static void clearData(Context context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.clear();

        editor.commit();

    }//end clearData

    public static void clearValue(Context context, String key) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.remove(key);
        editor.commit();
    }//end clearData

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, value);
        editor.commit();
    }//end putString

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }//end putInt

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }//end putBoolean

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putFloat(key, value);
        editor.commit();
    }//end putFloat

    public static void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }//end putLong

    public static boolean getBoolean(Context context, String key, boolean value) {
        return getInstance(context).getBoolean(key, value);
    }//end getBoolean


    public static int getInt(Context context, String key, int value) {
        return getInstance(context).getInt(key, value);
    }//end getInt


    public static String getString(Context context, String key, String value) {
        return getInstance(context).getString(key, value);
    }//end getString

}//end of class

