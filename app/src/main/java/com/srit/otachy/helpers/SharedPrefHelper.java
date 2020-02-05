package com.srit.otachy.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static com.srit.otachy.helpers.Apps.BUTCHER;
import static com.srit.otachy.helpers.Apps.OTCHY;

public class SharedPrefHelper {
    private static SharedPrefHelper instance;

    private static String PREF_NAME = "mySettingsPref";
    private static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static String APP_TYPE = "APP_TYPE";

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

    public String getAccessToken(){
        return mSharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken){
        mSharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

    public Apps getAppType(){

        switch (mSharedPreferences.getInt(APP_TYPE,0)){
            case 0:return OTCHY;
            case 1:return BUTCHER;
        }
        return null;
    }

    public void setAppType(Apps appType){
        int x;
        switch (appType){
            case OTCHY:x=0;
                mSharedPreferences.edit().putInt(APP_TYPE, x).apply();
                break;
            case BUTCHER:x=1;
                mSharedPreferences.edit().putInt(APP_TYPE, x).apply();
                break;

        }

    }
}
