package com.example.pit_collection_2020

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.mode_selection.*

class ModeSelectionActivity : AppCompatActivity() {
    var modeSelection: Constants.MODE_SELECTION = Constants.MODE_SELECTION.NONE

    private fun startTeamList() {
        putIntoStorage(this, "mode_selection", modeSelection)
        val intent = Intent(this, TeamListActivity::class.java)
        startActivity(intent)
    }

    private fun initButtonOnClicks() {
        btn_select_mode_pit.setOnClickListener { view ->
            modeSelection = Constants.MODE_SELECTION.PIT
            startTeamList()
        }
        btn_select_mode_seals.setOnClickListener { view ->
            modeSelection = Constants.MODE_SELECTION.SEALS
            startTeamList()
        }
    }

    override fun onResume() {
        super.onResume()
        requestStoragePermissions(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mode_selection)
        if (this.getSharedPreferences("PREFS", 0).contains("mode_selection")) {
            startTeamList()
        } else {
            initButtonOnClicks()
        }
    }
}