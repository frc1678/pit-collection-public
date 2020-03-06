package com.frc1678.pit_collection

import android.Manifest
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.team_list_activity.*

//Reads the csv file, populates a listView, and starts CollectionObjectiveActivity.
class TeamListActivity : AppCompatActivity() {
    var teamsList: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_list_activity)
        toolbarText(actionBar, supportActionBar, this)
        requestStoragePermissions(this, this)
        requestCamera(this, this)
    }

    // Starts the mode selection activity of the previously selected selection mode
    private fun intentToMatchInput() {
        this.getSharedPreferences("PREFS", 0).edit().remove("mode_collection_select_activity").apply()
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
            lv_teams_list.adapter = TeamListAdapter(this, teamsList)

            lv_teams_list.setOnItemClickListener { parent, view, position, id ->
                if (teamsList.isNotEmpty()) {
                    val element = teamsList[position]
                    val intent: Intent
                    if (retrieveFromStorage(
                            this,
                            "mode_collection_select_activity"
                        ) == Constants.ModeSelection.OBJECTIVE.toString()
                    ) {
                        intent = Intent(this, CollectionObjectiveActivity::class.java)
                        intent.putExtra("teamNumber", element)
                        startActivity(intent)
                    } else if (retrieveFromStorage(
                            this,
                            "mode_collection_select_activity"
                        ) == Constants.ModeSelection.SUBJECTIVE.toString()
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

