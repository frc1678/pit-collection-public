package com.example.pit_collection_2020

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.zetcode.pitJsonFileRead
import kotlinx.android.synthetic.main.team_info_collection.*
import schemaRead
import java.io.File
import java.lang.Integer.parseInt

//Create spinners (drivetrain and motor type).
class PitCollectionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        //Populate spinner with arrays from strings.xml
        createSpinner(spin_drivetrain, R.array.drivetrain_array, this)
        createSpinner(spin_drivetrain_motor_type, R.array.drivetrain_motor_type_array, this)
        teamNum = parseInt(getIntent().getStringExtra("teamNumber").toString())
        tv_team_number.setText("$teamNum")

        cameraButton("$teamNum")
        saveButton()
        populateScreen()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }

    fun cameraButton(teamNum:String) {
        btn_camera.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("teamNumber", teamNum)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                btn_camera, "proceed_button").toBundle())
        }
    }
    fun populateScreen() {
        if (File("/storage/emulated/0/Download/${teamNum}__obj_pit.json").exists()) {

            val jsonFile = pitJsonFileRead(teamNum)
            tb_can_cross_trench.isChecked = jsonFile.can_cross_trench as Boolean
            tb_can_ground_intake.isChecked = jsonFile.has_ground_intake as Boolean
            spin_drivetrain.setSelection(jsonFile.drivetrain as Int)
            spin_drivetrain_motor_type.setSelection(jsonFile.drivetrain_motor_type as Int)
            et_number_of_motors.setText(jsonFile.drivetrain_motors.toString())
        }
    }

    //Saves data into a JSON file
    fun saveButton() {
        btn_save_button.setOnClickListener {
            // If number of motors editText is empty, show Snackbar as a reminder
            if (et_number_of_motors.text.isEmpty()) {
                val numberOfMotorSnack = Snackbar.make(
                    it,
                    "Please Enter Number Of Drivetrain Motors",
                    Snackbar.LENGTH_SHORT
                )
                numberOfMotorSnack.show()
            } else if (spin_drivetrain.getSelectedItem().toString() == "Drivetrain") {
                val drivetrainSnack = Snackbar.make(
                    it,
                    "Please Define A Drivetrain",
                    Snackbar.LENGTH_SHORT
                )
                drivetrainSnack.show()
            } else if (spin_drivetrain_motor_type.getSelectedItem().toString() == "Drivetrain Motor Type") {
                val drivetrainMotorTypeSnack = Snackbar.make(
                    it,
                    "Please Define A Drivetrain Motor Type",
                    Snackbar.LENGTH_SHORT
                )
                drivetrainMotorTypeSnack.show()
            } else {
                crossTrench = tb_can_cross_trench.isChecked
                numberOfDriveMotors = parseInt(et_number_of_motors.text.toString())

                // Use schemaRead() function to read pit_collection_schema.yml and use indexOf() to find corresponding enum value
                drivetrain = spin_drivetrain.getSelectedItem().toString().toLowerCase()
                var schemaInfoDrivetrain = (schemaRead(R.raw.pit_collection_schema, this).getValue("enums").getValue("drivetrain")).toString()
                var splitSchemaDrivetrain = schemaInfoDrivetrain.split(",")
                for (drivetrain in splitSchemaDrivetrain) {
                    if (drivetrain.contains(drivetrain)) {
                        indexNumDrivetrain = splitSchemaDrivetrain.indexOf(drivetrain)
                    }
                }
                drivetrainMotor = spin_drivetrain_motor_type.getSelectedItem().toString().toLowerCase()
                var schemaInfoMotor = (schemaRead(R.raw.pit_collection_schema, this).getValue("enums").getValue("drivetrain_motor_type")).toString()
                var splitSchemaMotor = schemaInfoMotor.split(",")
                for (motor in splitSchemaMotor) {
                    if (motor.contains(drivetrainMotor.toString())) {
                        indexNumMotor = splitSchemaMotor.indexOf(motor)
                    }
                }

                hasGroundIntake = tb_can_ground_intake.isChecked
                //TODO Move below code to PitCollectionActivity and link to save button

                // Save variable information as a pitData class.
                var information = PitData(
                    teamNum,
                    crossTrench,
                    indexNumDrivetrain,
                    hasGroundIntake,
                    numberOfDriveMotors,
                    indexNumMotor
                )
                var jsonData = convertToJson(information)
                var file_name = "${teamNum}_obj_pit"
                writeToFile(file_name, jsonData)

                val element = teamNum
                val intent = Intent(this, TeamListActivity::class.java)
                intent.putExtra("teamNumber", element)
                startActivity(intent)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
