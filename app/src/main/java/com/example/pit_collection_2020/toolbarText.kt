package com.example.pit_collection_2020

import android.app.ActionBar
import android.content.Context

fun toolbarText(view: ActionBar?, support: androidx.appcompat.app.ActionBar?, context: Context) {
    when {
        retrieveFromStorage(context, "mode_selection") == Constants.MODE_SELECTION.PIT.toString() -> {
            view?.title = context.getString(R.string.tv_objective_version, version_num)
            support?.title = context.getString(R.string.tv_objective_version, version_num)
            view?.show()
            support?.show()
        }
        retrieveFromStorage(context, "mode_selection") == Constants.MODE_SELECTION.SEALS.toString() -> {
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