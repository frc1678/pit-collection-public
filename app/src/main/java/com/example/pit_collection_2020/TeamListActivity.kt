package com.example.pit_collection_2020

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.team_list.*

//Reads the csv file, populates a listView, and starts CollectionActivity.
class TeamListActivity : AppCompatActivity() {
    var teamsList: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.team_list)
        requestWriteExternalStorage(this, this)
        requestReadExternalStorage(this, this)

    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            if (csvFileRead("team_list.csv", false, this) != ArrayList<String>()) {
                teamsList = csvFileRead("team_list.csv", false, this)[0].trim().split(" ")
            }
            lv_teams_list.adapter = ArrayAdapter<String>(
                this, R.layout.team_cell, R.id.team_number, teamsList
            )

            lv_teams_list.setOnItemClickListener { parent, view, position, id ->
                if (teamsList.isNotEmpty()) {
                    val element = teamsList[position]
                    val intent = Intent(this, CollectionActivity::class.java)
                    intent.putExtra("teamNumber", element)
                    startActivity(intent)
                }
            }
        }


    }
}

