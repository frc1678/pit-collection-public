// Copyright (c) 2019 FRC Team 1678: Citrus Circuits
package com.frc1678.pit_collection

import android.Manifest
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import androidx.core.app.ActivityCompat
import com.opencsv.CSVReader
import kotlinx.android.synthetic.main.team_list_activity.*
import java.io.File
import java.io.FileReader

//Reads the csv file, populates a listView, and starts CollectionObjectiveDataActivity.
class TeamListActivity : CollectionActivity() {
    var teamsList: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_list_activity)
        setToolbarText(actionBar, supportActionBar)
    }

    fun csvFileRead(file: String, skipHeader: Boolean, context: Context): MutableList<String> {
        val csvFile = File("/storage/emulated/0/${Environment.DIRECTORY_DOWNLOADS}/$file")
        val csvFileContents: MutableList<String> = ArrayList()
        if (csvFile.exists()) {
            val csvReader = CSVReader(FileReader(csvFile))
            var currentLine: Array<String>? = csvReader.readNext()
            lateinit var currentMutableLine: String

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

            csvReader.close()
        } else {
            AlertDialog.Builder(context)
                .setMessage("There is no teams list CSV file on this device")
                .show()
        }
        return csvFileContents
    }

    // Starts the mode selection activity of the previously selected selection mode
    private fun intentToMatchInput() {
        this.getSharedPreferences("PREFS", 0).edit().remove("mode_collection_select_activity")
            .apply()
        startActivity(
            Intent(this, ModeCollectionSelectActivity::class.java),
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }

    // Restart app from ModeCollectionSelectActivity.kt when back button is long pressed.
    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage(R.string.error_back)
            .setNegativeButton("OK") { _, _ -> TeamListActivity() }
            .show()
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder(this).setMessage(R.string.error_back_reset)
                .setPositiveButton("Yes") { _, _ -> intentToMatchInput() }
                .setNegativeButton("No") { _, _ -> TeamListActivity() }
                .show()
        }
        return super.onKeyLongPress(keyCode, event)
    }


    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (csvFileRead("team_list.csv", false, this) != ArrayList<String>()) {
                teamsList = csvFileRead("team_list.csv", false, this)[0].trim().split(" ")
            }
            lv_teams_list.adapter = TeamListAdapter(
                this,
                teamsList,
                retrieveFromStorage("mode_collection_select_activity")
            )

            lv_teams_list.setOnItemClickListener { _, _, position, _ ->
                if (teamsList.isNotEmpty()) {
                    val element = teamsList[position]
                    val intent: Intent
                    if (retrieveFromStorage("mode_collection_select_activity") == Constants.ModeSelection.OBJECTIVE.toString()
                    ) {
                        intent = Intent(this, CollectionObjectiveDataActivity::class.java)
                        intent.putExtra("teamNumber", element)
                        startActivity(intent)
                    } else if (retrieveFromStorage("mode_collection_select_activity") == Constants.ModeSelection.SUBJECTIVE.toString()
                    ) {
                        intent = Intent(this, CollectionSubjectiveActivity::class.java)
                        intent.putExtra("teamNumber", element)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
