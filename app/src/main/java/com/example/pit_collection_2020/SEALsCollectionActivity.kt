package com.example.pit_collection_2020

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.seals_collection.*
import kotlinx.android.synthetic.main.team_info_collection.btn_save_button
import kotlinx.android.synthetic.main.team_info_collection.tv_team_number

class SEALsCollectionActivity : AppCompatActivity() {

    var team_number: Int? = null
    var climber_strap_installation_time: Int? = null
    var climber_strap_installation_notes: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seals_collection)
        sealsNotesEntered()

        team_number = Integer.parseInt(getIntent().getStringExtra("teamNumber").toString())
        tv_team_number.setText("$team_number")

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
                        climber_strap_installation_time = 0
                    }
                R.id.radio_one ->
                    // Average time to install climber
                    if (checked) {
                        climber_strap_installation_time = 1
                    }
                R.id.radio_two ->
                    // High time to install climber
                    if (checked) {
                        climber_strap_installation_time = 2
                    }
                R.id.radio_three ->
                    // Impossible to install climber
                    if (checked) {
                        climber_strap_installation_time = 3
                    }
            }
        }
    }

    fun sealsNotesEntered() {
        seals_notes.addTextChangedListener {
            climber_strap_installation_notes = seals_notes.text.toString()
        }
    }

    //Saves data into a JSON file
    fun saveButton() {
        btn_save_button.setOnClickListener {
            // If number of motors editText is empty, show Snackbar as a reminder
            if (climber_strap_installation_time == null) {
                val climber_strap_installation_time_snack = Snackbar.make(
                    it,
                    "Please Enter A Climber Installation Time Value",
                    Snackbar.LENGTH_SHORT
                )
                climber_strap_installation_time_snack.show()
            } else {
                var sealsInformation = SubjectiveData(
                    team_number,
                    climber_strap_installation_time,
                    climber_strap_installation_notes
                )
                var jsonData = convertToJson(sealsInformation)
                var file_name = "${team_number}_subj_pit"
                writeToFile(file_name, jsonData)
                val element = team_number
                val intent = Intent(this, TeamListActivity::class.java)
                intent.putExtra("team_number", element)
                startActivity(intent)
            }
        }
    }
}
