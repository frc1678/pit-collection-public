package com.example.pit_collection_2020

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.team_info_collection.*

//Create spinners (drivetrain and motor type).
class CollectionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_info_collection)

        //Populate spinner with arrays from strings.xml
        createSpinner(R.id.spin_drivetrain, R.array.drivetrain_array)
        createSpinner(R.id.spin_drivetrain_motor_type, R.array.drivetrain_motor_type_array)

        val teamNum = getIntent().getStringExtra("teamNumber").toString()
        tv_team_number.setText(teamNum)
    }

    //Function to create and populate a spinner
    private fun createSpinner(spinner: Int, array: Int) {
        val spinner: Spinner = findViewById(spinner)
        spinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this, array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
