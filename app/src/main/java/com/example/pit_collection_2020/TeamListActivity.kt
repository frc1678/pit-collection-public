package com.example.pit_collection_2020

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class TeamListActivity : AppCompatActivity() {
    var teamsListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_list)
        teamsListView = findViewById(R.id.teams_list) as ListView
        val teamsList = arrayOf(
            "118",
            "148",
            "971",
            "1323",
            "1678",
            "2056"
        )

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            R.layout.team_cell,
            R.id.team_number,
            teamsList
        )
        teamsListView!!.setAdapter(arrayAdapter)
    }
}