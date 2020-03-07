package com.frc1678.pit_collection

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

open class CollectionObjectiveActivity : CollectionActivity() {

    // Create and populate a spinner.
    fun createSpinner(spinner: Spinner, array: Int) {
        ArrayAdapter.createFromResource(
            this, array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                spinner.setSelection(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                return
            }
        }
    }

    fun putExtras(getIntent: Intent, intent: Intent, teamNum: String): Intent {
        intent.putExtra("teamNumber", teamNum)
            .putExtra("can_cross_trench", getIntent.getBooleanExtra("can_cross_trench", false))
            .putExtra("has_ground_intake", getIntent.getBooleanExtra("has_ground_intake", false))
            .putExtra("drivetrain_pos", getIntent.getIntExtra("drivetrain_pos", -1))
            .putExtra("drivetrain_motor_pos", getIntent.getIntExtra("drivetrain_motor_pos", -1))
            .putExtra("num_motors", getIntent.getIntExtra("num_motors", 0))
        return intent
    }

    override fun onBackPressed() {}
}