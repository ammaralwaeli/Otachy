package com.srit.otachy.ui

import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import com.srit.otachy.R
import com.srit.otachy.helpers.SharedPrefHelper
import com.srit.otachy.ui.activity.LoginActivity

interface CanLogout{
    fun logout(activity: AppCompatActivity){
        SharedPrefHelper.getInstance().accessToken= null
        activity.finishAffinity()
        LoginActivity.newInstance(activity)
    }

    fun expireLogout(activity: AppCompatActivity){
        Toast.makeText(activity, activity.getString(R.string.account_expire), Toast.LENGTH_LONG).show()
        logout(activity)
    }
}