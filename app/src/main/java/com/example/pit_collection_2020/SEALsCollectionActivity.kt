package com.example.pit_collection_2020

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.seals_collection.*
import kotlinx.android.synthetic.main.team_info_collection.btn_save_button
import kotlinx.android.synthetic.main.team_info_collection.tv_team_number

class SEALsCollectionActivity : AppCompatActivity() {
    var teamNum: Int? = null
    var climberStrapInstallationTime: Int? = null
    lateinit var climberStrapInstallationNotes: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seals_collection)
        sealsNotesEntered()
        teamNum = Integer.parseInt(getIntent().getStringExtra("teamNumber").toString())
        tv_team_number.setText("$teamNum")
        saveButton()
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_zero ->
                    // Low time to install climber
                    if (checked) {
                        climberStrapInstallationTime = 0
                    }
                R.id.radio_one ->
                    // Average time to install climber
                    if (checked) {
                        climberStrapInstallationTime = 1
                    }
                R.id.radio_two ->
                    // High time to install climber
                    if (checked) {
                        climberStrapInstallationTime = 2
                    }
                R.id.radio_three ->
                    // Impossible to install climber
                    if (checked) {
                        climberStrapInstallationTime = 3
                    }
            }
        }
    }

    fun sealsNotesEntered() {
        seals_notes.addTextChangedListener {
            climberStrapInstallationNotes = seals_notes.text.toString()
        }
    }

    //Saves data into a JSON file
    fun saveButton() {
        btn_save_button.setOnClickListener {
            // If number of motors editText is empty, show Snackbar as a reminder
            if (climberStrapInstallationTime == null) {
                val climberStrapInstallationTimeSnack = Snackbar.make(
                    it,
                    "Please Enter A Climb Compatibility Value",
                    Snackbar.LENGTH_SHORT
                )
                climberStrapInstallationTimeSnack.show()
            } else {
                var sealsInformation = SubjectiveData(
                    teamNum,
                    climberStrapInstallationTime,
                    climberStrapInstallationNotes
                )
                var jsonData = convertToJson(sealsInformation)
                var file_name = "${teamNum}_subjective"
                writeToFile(file_name, jsonData)
            }
        }
    }
}
