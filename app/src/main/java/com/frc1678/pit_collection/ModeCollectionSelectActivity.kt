package com.frc1678.pit_collection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.mode_collection_select_activity.*

class ModeCollectionSelectActivity : AppCompatActivity() {
    var modeSelection: Constants.ModeSelection = Constants.ModeSelection.NONE

    private fun startTeamList() {
        val intent = Intent(this, TeamListActivity::class.java)
        startActivity(intent)
    }

    private fun initButtonOnClicks() {
        btn_objective_collection_mode.setOnClickListener { view ->
            modeSelection = Constants.ModeSelection.OBJECTIVE
            putIntoStorage(this, "mode_collection_select_activity", modeSelection)
            startTeamList()
        }
        btn_subjective_collection_mode.setOnClickListener { view ->
            modeSelection = Constants.ModeSelection.SUBJECTIVE
            putIntoStorage(this, "mode_collection_select_activity", modeSelection)
            startTeamList()
        }
    }

    override fun onResume() {
        super.onResume()
        requestStoragePermissions(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mode_collection_select_activity)
        toolbarText(actionBar, supportActionBar, this)
        if (this.getSharedPreferences("PREFS", 0).contains("mode_collection_select_activity")) {
            startTeamList()
        } else {
            initButtonOnClicks()
        }
    }
}