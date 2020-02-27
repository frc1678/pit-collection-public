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
import java.io.File
import java.lang.Integer.parseInt
import java.util.*

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

        teamNum = parseInt(intent.getStringExtra("teamNumber")!!.toString())
        tv_team_number.setText("$teamNum")

        toolbarText(actionBar, supportActionBar, this)

        cameraButton("$teamNum")
        saveButton()
        populateScreen()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }

    private fun assignIndexNums() {
        // Use schemaRead() function to read pit_collection_schema.yml and use indexOf() to find corresponding enum value
        drivetrain = spin_drivetrain.selectedItem.toString().toLowerCase(Locale.US)

        indexNumDrivetrain = when (drivetrain) {
            "tank" -> {
                0
            }
            "mecanum" -> {
                1
            }
            "swerve" -> {
                2
            }
            "other" -> {
                3
            }
            else -> -1
        }

        drivetrainMotor =
            spin_drivetrain_motor_type.selectedItem.toString().toLowerCase(Locale.US)

        //Drive Train Motor Type
        //Todo: Hook up to enums instead of hard coding
        indexNumMotor = when (drivetrainMotor) {
            "minicim" -> {
                0
            }
            "cim" -> {
                1
            }
            "neo" -> {
                2
            }
            "falcon" -> {
                3
            }
            else -> -1
        }
    }

    private fun cameraButton(teamNum: String) {
        btn_camera.setOnClickListener {
            assignIndexNums()

            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("teamNumber", teamNum)
                .putExtra("can_cross_trench", tb_can_cross_trench.isChecked)
                .putExtra("has_ground_intake", tb_can_ground_intake.isChecked)
                .putExtra("drivetrain_pos", parseInt(indexNumDrivetrain.toString()))
                .putExtra("drivetrain_motor_pos", parseInt(indexNumMotor.toString()))
            if (et_number_of_motors.text.isNotEmpty()) {
                intent.putExtra("num_motors", parseInt(et_number_of_motors.text.toString()))
            }
            startActivity(
                intent, ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    btn_camera, "proceed_button"
                ).toBundle()
            )
        }
    }

    private fun populateScreen() {
        if (intent.getBooleanExtra("after_camera", false)) {
            tb_can_cross_trench.isChecked = intent.getBooleanExtra("can_cross_trench", false)
            tb_can_ground_intake.isChecked = intent.getBooleanExtra("has_ground_intake", false)
            spin_drivetrain.setSelection(intent.getIntExtra("drivetrain_pos", -1) + 1)
            spin_drivetrain_motor_type.setSelection(
                intent.getIntExtra(
                    "drivetrain_motor_pos",
                    -1
                ) + 1
            )
            if (intent.getIntExtra("num_motors", 0) != 0) {
                et_number_of_motors.setText(intent.getIntExtra("num_motors", 0).toString())
            }
        } else if (File("/storage/emulated/0/Download/${teamNum}_obj_pit.json").exists()) {
            val jsonFile = pitJsonFileRead(teamNum)
            tb_can_cross_trench.isChecked = jsonFile.can_cross_trench as Boolean
            tb_can_ground_intake.isChecked = jsonFile.has_ground_intake as Boolean
            spin_drivetrain.setSelection(parseInt(jsonFile.drivetrain.toString()) + 1)
            spin_drivetrain_motor_type.setSelection(parseInt(jsonFile.drivetrain_motor_type.toString()) + 1)
            et_number_of_motors.setText(jsonFile.drivetrain_motors.toString())
        }
    }

    //Saves data into a JSON file
    private fun saveButton() {
        btn_save_button.setOnClickListener {
            // If number of motors editText is empty, show Snackbar as a reminder
            when {
                et_number_of_motors.text.isEmpty() -> {
                    val numberOfMotorSnack = Snackbar.make(
                        it,
                        "Please Enter Number Of Drivetrain Motors",
                        Snackbar.LENGTH_SHORT
                    )
                    numberOfMotorSnack.show()
                }
                spin_drivetrain.getSelectedItem().toString() == "Drivetrain" -> {
                    val drivetrainSnack = Snackbar.make(
                        it,
                        "Please Define A Drivetrain",
                        Snackbar.LENGTH_SHORT
                    )
                    drivetrainSnack.show()
                }
                spin_drivetrain_motor_type.getSelectedItem().toString() == "Drivetrain Motor Type" -> {
                    val drivetrainMotorTypeSnack = Snackbar.make(
                        it,
                        "Please Define A Drivetrain Motor Type",
                        Snackbar.LENGTH_SHORT
                    )
                    drivetrainMotorTypeSnack.show()
                }
                else -> {
                    crossTrench = tb_can_cross_trench.isChecked
                    numberOfDriveMotors = parseInt(et_number_of_motors.text.toString())
                    hasGroundIntake = tb_can_ground_intake.isChecked
                    //TODO Move below code to PitCollectionActivity and link to save button

                    assignIndexNums()

                    // Save variable information as a pitData class.
                    val information = PitData(
                        teamNum,
                        crossTrench,
                        indexNumDrivetrain,
                        hasGroundIntake,
                        numberOfDriveMotors,
                        indexNumMotor
                    )
                    val jsonData = convertToJson(information)
                    val fileName = "${teamNum}_obj_pit"
                    writeToFile(fileName, jsonData)

                    val element = teamNum
                    val intent = Intent(this, TeamListActivity::class.java)
                    intent.putExtra("teamNumber", element)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        val intent = Intent(this, TeamListActivity::class.java)
        startActivity(intent)
    }
}
