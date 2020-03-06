package com.frc1678.pit_collection

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner


//Function to create and populate a spinner
fun createSpinner(spinner: Spinner, array: Int, context: Context) {
    ArrayAdapter.createFromResource(
        context, array, android.R.layout.simple_spinner_item
    ).also { adapter ->
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    spinner.onItemSelectedListener = object : OnItemSelectedListener {
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
