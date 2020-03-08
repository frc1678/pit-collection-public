// Copyright (c) 2019 FRC Team 1678: Citrus Circuits
package com.frc1678.pit_collection

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.collection_subjective_activity.*
import kotlinx.android.synthetic.main.collection_objective_activity.btn_save_button
import kotlinx.android.synthetic.main.collection_objective_activity.tv_team_number
import java.io.File
import android.widget.RadioGroup
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.CompoundButton


class CollectionSubjectiveActivity : CollectionActivity() {

    private var team_number: Int? = null
    private var climber_strap_installation_difficulty: Int? = null
    private var climber_strap_installation_notes: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_subjective_activity)
        setToolbarText(actionBar, supportActionBar)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        subjectiveNotesEntered()

        team_number = Integer.parseInt(intent?.getStringExtra("teamNumber").toString())
        tv_team_number.text = "$team_number"

        saveButton()
        populateScreen()
    }

    private fun populateScreen() {
        if (File("/storage/emulated/0/Download/${team_number}_subj_pit.json").exists()) {
            val jsonFile = subjJsonFileRead(team_number)
            when (jsonFile.climber_strap_installation_difficulty) {
                1 -> {
                    radio_one.isChecked = true
                    climber_strap_installation_difficulty = 1
                    radio_group_two.clearCheck()
                }
                2 -> {
                    radio_two.isChecked = true
                    climber_strap_installation_difficulty = 2
                    radio_group_two.clearCheck()
                }
                3 -> {
                    radio_three.isChecked = true
                    climber_strap_installation_difficulty = 3
                    radio_group_two.clearCheck()
                }
                4 -> {
                    radio_four.isChecked = true
                    climber_strap_installation_difficulty = 4
                    radio_group_two.clearCheck()
                }
                5 -> {
                    radio_five.isChecked = true
                    climber_strap_installation_difficulty = 5
                    radio_group_two.clearCheck()
                }
                6 -> {
                    radio_six.isChecked = true
                    climber_strap_installation_difficulty = 6
                    radio_group_one.clearCheck()
                }
                7 -> {
                    radio_seven.isChecked = true
                    climber_strap_installation_difficulty = 7
                    radio_group_one.clearCheck()
                }
                8 -> {
                    radio_eight.isChecked = true
                    climber_strap_installation_difficulty = 8
                    radio_group_one.clearCheck()
                }
                9 -> {
                    radio_nine.isChecked = true
                    climber_strap_installation_difficulty = 9
                    radio_group_one.clearCheck()
                }
                10 -> {
                    radio_ten.isChecked = true
                    climber_strap_installation_difficulty = 10
                    radio_group_one.clearCheck()
                }
            }
            et_subjective_notes.setText(jsonFile.climber_strap_installation_notes?.replace("\"", ""))
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_one ->
                    if (checked) {
                        climber_strap_installation_difficulty = 1
                        radio_group_two.clearCheck()
                    }
                R.id.radio_two ->
                    if (checked) {
                        climber_strap_installation_difficulty = 2
                        radio_group_two.clearCheck()
                    }
                R.id.radio_three ->
                    if (checked) {
                        climber_strap_installation_difficulty = 3
                        radio_group_two.clearCheck()
                    }
                R.id.radio_four ->
                    if (checked) {
                        climber_strap_installation_difficulty = 4
                        radio_group_two.clearCheck()
                    }
                R.id.radio_five ->
                    if (checked) {
                        climber_strap_installation_difficulty = 5
                        radio_group_two.clearCheck()
                    }
                R.id.radio_six ->
                    if (checked) {
                        climber_strap_installation_difficulty = 6
                        radio_group_one.clearCheck()
                    }
                R.id.radio_seven ->
                    if (checked) {
                        climber_strap_installation_difficulty = 7
                        radio_group_one.clearCheck()
                    }
                R.id.radio_eight ->
                    if (checked) {
                        climber_strap_installation_difficulty = 8
                        radio_group_one.clearCheck()
                    }
                R.id.radio_nine ->
                    if (checked) {
                        climber_strap_installation_difficulty = 9
                        radio_group_one.clearCheck()
                    }
                R.id.radio_ten ->
                    if (checked) {
                        climber_strap_installation_difficulty = 10
                        radio_group_one.clearCheck()
                    }
            }
        }
    }

    private fun subjectiveNotesEntered() {
        et_subjective_notes.addTextChangedListener {
            climber_strap_installation_notes = et_subjective_notes.text.toString()
        }
    }

    //Saves data into a JSON file
    private fun saveButton() {
        btn_save_button.setOnClickListener {
            // If number of motors editText is empty, show Snackbar as a reminder
            if (climber_strap_installation_difficulty == null) {
                val climberStrapInstallationDifficultySnack = Snackbar.make(
                    it,
                    "Please Enter A Climber Installation Difficulty Value",
                    Snackbar.LENGTH_SHORT
                )
                climberStrapInstallationDifficultySnack.show()
            } else {
                val subjectiveInformation = Constants.DataSubjective(
                    team_number,
                    climber_strap_installation_difficulty,
                    climber_strap_installation_notes
                )
                val jsonData = convertToJson(subjectiveInformation)
                val file_name = "${team_number}_subj_pit"
                writeToFile(file_name, jsonData)
                val element = team_number
                val intent = Intent(this, TeamListActivity::class.java)
                intent.putExtra("team_number", element)
                startActivity(intent)
            }
        }
    }
}