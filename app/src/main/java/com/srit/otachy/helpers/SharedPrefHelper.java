package com.srit.otachy.helpers;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefHelper {
    private static SharedPrefHelper instance;

    private static String PREF_NAME = "mySettingsPref";
    private static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static String IS_REGISTER = "IS_REGISTER";
    private static String IS_VERIFICATION = "IS_VERIFICATION";

    public static void init(Context context) {
        instance= new SharedPrefHelper(context);
    }


    public static SharedPrefHelper getInstance(){
        if(instance == null) throw new NullPointerException();
        return instance;
    }

    private SharedPreferences mSharedPreferences;

    private SharedPrefHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public boolean getIsRegister() {
        return mSharedPreferences.getBoolean(IS_REGISTER,false);
    }

    public void setIsRegister(boolean isRegister) {
        mSharedPreferences.edit().putBoolean(IS_REGISTER, isRegister).apply();
    }

    public boolean getIsVerification() {
        return mSharedPreferences.getBoolean(IS_VERIFICATION,false);
    }

    public void setIsVerification(boolean isVerification) {
        mSharedPreferences.edit().putBoolean(IS_VERIFICATION, isVerification).apply();
    }

    public String getAccessToken(){
        return mSharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken){
        mSharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

}
