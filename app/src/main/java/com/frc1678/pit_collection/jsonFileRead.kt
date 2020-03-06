package com.zetcode

import com.frc1678.pit_collection.DataObjective
import com.frc1678.pit_collection.DataSubjective
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileReader
import java.lang.Integer.parseInt

fun pitJsonFileRead(teamNum: Int?): DataObjective {
    val fileName = "/storage/emulated/0/Download/${teamNum}_obj_pit.json"

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
    var boolHasGroundIntake: Boolean? = null

    // Make values from jo boolean instead of jsonObject
    fun initSpecialVariables(): DataObjective {
        when (crossTrench.toString()) {
            "true" -> boolCrossTrench = true
            "false" -> boolCrossTrench = false
        }
        when (hasGroundIntake.toString()) {
            "true" -> boolHasGroundIntake = true
            "false" -> boolHasGroundIntake = false
        }

        // Create a DataObjective object with the information from jo
        var readInformation = DataObjective(
            teamNum,
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

fun subjJsonFileRead(teamNum: Int?): DataSubjective {
    val fileName = "/storage/emulated/0/Download/${teamNum}_subj_pit.json"

    // Make a json object called jo
    val obj = JsonParser().parse(FileReader(fileName))
    val jo = obj as JsonObject

    // Get values from the jo json file
    val climber_strap_installation_time = jo.get("climber_strap_installation_time")
    val climber_strap_installation_notes = jo.get("climber_strap_installation_notes")

    val readInformation = DataSubjective(
        teamNum,
        parseInt(climber_strap_installation_time.toString()),
        climber_strap_installation_notes?.toString()
    )

    return readInformation
}