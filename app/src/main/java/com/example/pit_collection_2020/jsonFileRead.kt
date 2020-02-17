package com.zetcode

import com.example.pit_collection_2020.PitData
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileReader
import java.lang.Integer.parseInt

fun jsonFileRead(teamName: Int?) : PitData{
    val fileName = "/storage/emulated/0/Download/${teamName}_pit.json"

    // Make a json object called jo
    val obj = JsonParser().parse(FileReader(fileName))
    val jo = obj as JsonObject

    // Get values from the jo json file
    val crossTrench = jo.get("can_cross_trench")
    val indexNumDrivetrain = jo.get("drivetrain")
    val hasGroundIntake = jo.get("has_ground_intake")
    val numberOfDriveMotors = jo.get("drivetrain_motors")
    val indexNumMotor = jo.get("drivetrain_motor_type")

    var boolCrossTrench: Boolean? = null
    var boolHasGroundIntake : Boolean? = null

    // Make values from jo boolean instead of jsonObject
    fun initSpecialVariables() : PitData{
        when (crossTrench.toString()) {
            "true" -> boolCrossTrench = true
            "false" -> boolCrossTrench = false
        }
        when (hasGroundIntake.toString()) {
            "true" -> boolHasGroundIntake = true
            "false" -> boolHasGroundIntake = false
        }

        // Create a PitData object with the information from jo
        var readInformation = PitData(
            teamName,
            boolCrossTrench,
            parseInt(indexNumDrivetrain.toString()),
            boolHasGroundIntake,
            parseInt(numberOfDriveMotors.toString()),
            parseInt(indexNumMotor.toString())
        )

        return readInformation

    }
    return initSpecialVariables()
}
