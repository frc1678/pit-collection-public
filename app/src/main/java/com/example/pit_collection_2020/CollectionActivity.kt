package com.example.pit_collection_2020

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.team_info_collection.*
import schemaRead
import java.lang.Integer.parseInt

//Create spinners (drivetrain and motor type).
class CollectionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var teamNum: Int? = null
    var crossTrench: Boolean? = null
    var drivetrain: String? = null
    var hasGroundIntake: Boolean? = null
    var numberOfDriveMotors: Int? = null
    var drivetrainMotor: String? = null
    var indexNumDrivetrain: Int? = null
    var indexNumMotor: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_info_collection)

        //Populate spinner with arrays from strings.xml
        createSpinner(R.id.spin_drivetrain, R.array.drivetrain_array)
        createSpinner(R.id.spin_drivetrain_motor_type, R.array.drivetrain_motor_type_array)
        teamNum = parseInt(getIntent().getStringExtra("teamNumber").toString())
        tv_team_number.setText("$teamNum")

        saveButton()
    }

    //Function to create and populate a spinner
    private fun createSpinner(spinner: Int, array: Int) {
        val spinner: Spinner = findViewById(spinner)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this, array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }

    //Saves data into a JSON file
    fun saveButton() {
        btn_save_button.setOnClickListener {
            // If number of motors editText is empty, show Snackbar as a reminder
            if (et_number_of_motors.text.isEmpty()){
                val numberOfMotorSnack = Snackbar.make(
                    it,
                    "Please Enter Number of Drivetrain Motors",
                    Snackbar.LENGTH_SHORT
                )
                numberOfMotorSnack.show()
            } else {
                crossTrench = tb_can_cross_trench.isChecked
                numberOfDriveMotors = parseInt(et_number_of_motors.text.toString())

                // Use schemaRead() function to read pit_collection_schema.yml and use indexOf() to find corresponding enum value
                drivetrain = spin_drivetrain.getSelectedItem().toString().toLowerCase()
                var schemaInfoDrivetrain = (schemaRead(this).getValue("enums").getValue("drivetrain")).toString()
                var splitSchemaDrivetrain = schemaInfoDrivetrain.split(",")
                for (drivetrain in splitSchemaDrivetrain) {
                    if (drivetrain.contains(drivetrain)) {
                        indexNumDrivetrain = splitSchemaDrivetrain.indexOf(drivetrain)
                    }
                }
                drivetrainMotor = spin_drivetrain_motor_type.getSelectedItem().toString().toLowerCase()
                var schemaInfoMotor = (schemaRead(this).getValue("enums").getValue("drivetrain_motor_type")).toString()
                var splitSchemaMotor = schemaInfoMotor.split(",")
                for (motor in splitSchemaMotor) {
                    if (motor.contains(drivetrainMotor.toString())) {
                        indexNumMotor = splitSchemaMotor.indexOf(motor)
                    }
                }

                hasGroundIntake = tb_can_ground_intake.isChecked
                //TODO Move below code to CollectionActivity and link to save button

                // Save variable information as a pitData class.
                var information = PitData(
                    teamNum,
                    crossTrench,
                    indexNumDrivetrain,
                    hasGroundIntake,
                    numberOfDriveMotors,
                    indexNumMotor
                )
                convertToJson(information)
                var jsonData = convertToJson(information)
                var file_name = "${teamNum}_pit"
                writeToFile(file_name, jsonData)

            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
