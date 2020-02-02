package com.example.pit_collection_2020
import android.os.Environment
import java.io.BufferedWriter
import java.io.FileWriter
import com.google.gson.Gson
import android.util.Log

//TODO Remove this class and replace with actual collected data variables
data class pitData (
    var team_number: Int,
    var can_cross_trench: Boolean,
    var drivetrain: String,
    var has_ground_intake: Boolean,
    var drivetrain_motors: Int,
    var drivetrain_motor_type: String) {

}

fun convertToJson(): String {
    //TODO Replace example data with pit schema
    val teamPitData = pitData(1678, true, "Tank", true, 4, "Falcons")

    val jsonString = Gson().toJson(teamPitData)

    return jsonString

}
//Writes team information to a JSON file
fun writeToFile(file_name: String, jsonString: String) {
    var file = BufferedWriter(
        FileWriter(
            "/storage/emulated/0/${Environment.DIRECTORY_DOWNLOADS}/$file_name.json", false
        )
    )
    file.write("$jsonString\n")
    file.close()
}

