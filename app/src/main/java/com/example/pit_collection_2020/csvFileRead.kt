/*
* csvFileRead.kt
* pit-collection
*
* Created on 1/25/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.pit_collection_2020

import android.Manifest
import android.os.Environment
import android.util.Log
import com.opencsv.*
import java.io.File
import java.io.FileReader
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.Manifest.permission.READ_EXTERNAL_STORAGE



/* Function to read a CSV file. Returns a list of strings
 where each individual list element is a single line of the csv file.

 absolute path: context.getExternalFilesDir(null)!!.absolutePath
 */

fun csvFileRead(file: String, skipHeader: Boolean): MutableList<String> {
    val csvFile = File( "/storage/emulated/0/${Environment.DIRECTORY_DOWNLOADS}/$file")
    val csvFileContents: MutableList<String> = ArrayList()
    val csvReader = CSVReader(FileReader(csvFile))

    var currentLine: Array<String>? = csvReader.readNext()

    lateinit var currentMutableLine: String

    /*If the file contains a header that is not needed when collecting data,
    this statement will skip the first line of the file (the supposed header).
     */

    if (skipHeader) {
        csvReader.readNext()
    }

    while (currentLine != null) {
        //Resets the current line's value for every new line as the while loop proceeds.
        currentMutableLine = ""

        for (lineContents in currentLine) {
            currentMutableLine += " $lineContents"
        }

        //Adds the current line's data to the list of the CSV file's contents (csvFileContents).
        csvFileContents.add(currentMutableLine)
        currentLine = csvReader.readNext()
    }

    for (x in csvFileContents) {
        Log.i("MATCHSCHEDULE", x)
    }

    csvReader.close()
    return csvFileContents
}


