package com.frc1678.pit_collection

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.zetcode.subjJsonFileRead
import kotlinx.android.synthetic.main.collection_subjective_activity.*
import kotlinx.android.synthetic.main.collection_objective_activity.btn_save_button
import kotlinx.android.synthetic.main.collection_objective_activity.tv_team_number
import java.io.File

class CollectionSubjectiveActivity : AppCompatActivity() {

    var team_number: Int? = null
    var climber_strap_installation_time: Int? = null
    var climber_strap_installation_notes: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collection_subjective_activity)
        toolbarText(actionBar, supportActionBar, this)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        subjectiveNotesEntered()

        team_number = Integer.parseInt(intent.getStringExtra("teamNumber").toString())
        tv_team_number.text = "$team_number"

        saveButton()
        populateScreen()
    }

    fun populateScreen() {
        if (File("/storage/emulated/0/Download/${team_number}_subj_pit.json").exists()) {
            val jsonFile = subjJsonFileRead(team_number)
            when (jsonFile.climber_strap_installation_time) {
                0 -> {
                    radio_zero.isChecked = true
                    climber_strap_installation_time = 0
                }
                1 -> {
                    radio_one.isChecked = true
                    climber_strap_installation_time = 1
                }
                2 -> {
                    radio_two.isChecked = true
                    climber_strap_installation_time = 2
                }
                3 -> {
                    radio_three.isChecked = true
                    climber_strap_installation_time = 3
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

    fun subjectiveNotesEntered() {
        et_subjective_notes.addTextChangedListener {
            climber_strap_installation_notes = et_subjective_notes.text.toString()
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
                var subjectiveInformation = DataSubjective(
                    team_number,
                    climber_strap_installation_time,
                    climber_strap_installation_notes
                )
                var jsonData = convertToJson(subjectiveInformation)
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
