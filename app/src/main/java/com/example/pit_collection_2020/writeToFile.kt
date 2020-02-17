package com.example.pit_collection_2020
import android.os.Environment
import java.io.BufferedWriter
import java.io.FileWriter
import com.google.gson.Gson

fun convertToJson(teamPitData: PitData): String {
    return Gson().toJson(teamPitData)
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


