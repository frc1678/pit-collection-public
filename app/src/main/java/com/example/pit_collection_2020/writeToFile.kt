package com.example.pit_collection_2020
import android.os.Environment
import java.io.BufferedWriter
import java.io.FileWriter
import com.google.gson.Gson

//TODO Remove this class and replace with actual collected data variables

fun convertToJson(teamData: Any?): String {
    //TODO Replace example data with pit schema
    return Gson().toJson(teamData)
}

//Writes team information to a JSON file
fun writeToFile(file_name: String, jsonString: String) {
    // Pixel File Location: Files -> Pancake Stack (Upper Left Hand Menu) -> Downloads -> Arranged By Date/Time
    // Lenovo Tablet File Location: Files -> Internal Storage (Bottom) -> Download (Middle) -> Arranged Alphanumerically
    var file = BufferedWriter(
        FileWriter(
            "/storage/self/primary/${Environment.DIRECTORY_DOWNLOADS}/$file_name.json", false
        )
    )
    file.write("$jsonString\n")
    file.close()
}


