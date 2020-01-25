package com.example.pit_collection_2020

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.team_list.*

class TeamListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_list)

        val teamsList = csvFileRead("team_list.csv", false)[0].trim().split(" ")
        lv_teams_list.adapter = ArrayAdapter<String>(
            this, R.layout.team_cell, R.id.team_number, teamsList)
    }
}