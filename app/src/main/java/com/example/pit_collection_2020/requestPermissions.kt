package com.example.pit_collection_2020

import android.Manifest
import android.content.Context
import androidx.core.app.ActivityCompat
import java.lang.Exception
import android.content.pm.PackageManager
import android.app.Activity

fun requestWriteExternalStorage(context: Context, activity: Activity) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
        try {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                100)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun requestReadExternalStorage(context: Context, activity: Activity) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
        try {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                100)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}