package com.example.pit_collection_2020

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.team_list.*

//Reads the csv file, populates a listView, and starts CollectionActivity.
class TeamListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.team_list)

        val teamsList = csvFileRead("team_list.csv", false)[0].trim().split(" ")
        lv_teams_list.adapter = ArrayAdapter<String>(
            this, R.layout.team_cell, R.id.team_number, teamsList
        )

        lv_teams_list.setOnItemClickListener { parent, view, position, id ->
            val element = teamsList[position]
            val intent = Intent(this, CollectionActivity::class.java)
            intent.putExtra("teamNumber", element)
            startActivity(intent)
        }
    }
}
