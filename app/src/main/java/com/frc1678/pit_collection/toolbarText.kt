package com.frc1678.pit_collection

import android.app.ActionBar
import android.content.Context

fun toolbarText(view: ActionBar?, support: androidx.appcompat.app.ActionBar?, context: Context) {
    when {
        retrieveFromStorage(
            context,
            "mode_collection_select_activity"
        ) == Constants.ModeSelection.OBJECTIVE.toString() -> {
            view?.title = context.getString(R.string.tv_objective_version, version_num)
            support?.title = context.getString(R.string.tv_objective_version, version_num)
            view?.show()
            support?.show()
        }
        retrieveFromStorage(
            context,
            "mode_collection_select_activity"
        ) == Constants.ModeSelection.SUBJECTIVE.toString() -> {
            view?.title = context.getString(R.string.tv_subjective_version, version_num)
            support?.title = context.getString(R.string.tv_subjective_version, version_num)
            view?.show()
            support?.show()
        }
        else -> {
            view?.title = context.getString(R.string.tv_version_num, version_num)
            support?.title = context.getString(R.string.tv_version_num, version_num)
            view?.show()
            support?.show()
        }
    }
}