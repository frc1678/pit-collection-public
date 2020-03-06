package com.frc1678.pit_collection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.team_cell.view.*
import java.io.File

class TeamListAdapter(
    private val context: Context,
    private val teamsList: List<String>
) : BaseAdapter() {
    private var inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = with(inflater) { inflate(R.layout.team_cell, parent, false) }
        view.team_number.text = teamsList[position]
        if (((retrieveFromStorage(
                context,
                "mode_collection_select_activity"
            ) == Constants.ModeSelection.OBJECTIVE.toString()) and (File("/storage/emulated/0/Download/${teamsList[position]}_obj_pit.json").exists())
                    and (File("/storage/emulated/0/Download/${teamsList[position]}_drivetrain.jpg").exists()) and (File(
                "/storage/emulated/0/Download/${teamsList[position]}_full_robot.jpg"
            ).exists())) or ((retrieveFromStorage(
                context,
                "mode_collection_select_activity"
            ) == Constants.ModeSelection.SUBJECTIVE.toString()) and (File("/storage/emulated/0/Download/${teamsList[position]}_subj_pit.json").exists())
                    )
        ) {
            view.setBackgroundColor(context.resources.getColor(R.color.green, null))
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return teamsList.size
    }
}