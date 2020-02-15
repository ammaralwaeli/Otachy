package com.srit.otachy.ui;

import android.content.Context;
import android.icu.text.UnicodeSetIterator;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.srit.otachy.R;
import com.srit.otachy.helpers.SharedPrefHelper;
import com.srit.otachy.helpers.ViewExtensionsKt;
import com.srit.otachy.ui.activity.LoginActivity;

public class Logout {

    public static void logout(AppCompatActivity activity){
        SharedPrefHelper.getInstance().setAccessToken(null);
        activity.finishAffinity();
        LoginActivity.Factory.newInstance(activity);
    }

    public static void expireToken(ViewGroup contentLayout,AppCompatActivity activity){
        ViewExtensionsKt.showSnackBar(contentLayout, activity.getString(R.string.account_expire),
                true);
        logout(activity);
    }
}
