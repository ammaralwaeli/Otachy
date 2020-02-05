package com.srit.otachy.helpers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {
     const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 0
     const val REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 1

    fun canWriteToExternalStorage(activity: Activity?): Boolean { // Here, thisActivity is the current activity
        return if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: complete all cases
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION
                )
            false
        } else { // Permission has already been granted
            true
        }
    }

    fun canReadFromExternalStorage(activity: Activity?): Boolean { // Here, thisActivity is the current activity
        return if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE_PERMISSION
                )
            false
        } else { // Permission has already been granted
            true
        }
    }

}