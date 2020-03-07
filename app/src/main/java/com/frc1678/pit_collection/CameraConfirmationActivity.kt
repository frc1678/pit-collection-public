package com.frc1678.pit_collection

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import kotlinx.android.synthetic.main.camera_confirmation_activity.*
import java.io.File

class CameraConfirmationActivity : CollectionObjectiveActivity() {
    private var fileName: String? = null
    private var teamNum: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_confirmation_activity)

        setToolbarText(actionBar, supportActionBar)

        teamNum = intent?.getStringExtra("teamNumber").toString()
        fileName = intent?.getStringExtra("fileName").toString()

        tv_team_number_confirm.text = teamNum

        displayImage(fileName!!)
        setOnClickListeners(teamNum.toString(), fileName.toString())
    }

    private fun displayImage(fileName: String) {
        val imgFile = File(fileName)

        if (imgFile.exists()) {
            val myBitmap: Bitmap = BitmapFactory.decodeFile(fileName)
            iv_picture_confirm.setImageBitmap(myBitmap)

        }
    }

    private fun setOnClickListeners(teamNum: String, fileName: String) {
        delete.setOnClickListener {
            File(fileName).delete()
            startActivity(
                putExtras(intent, Intent(this, CameraActivity::class.java), teamNum),
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    delete, "proceed_button"
                ).toBundle()
            )
        }
        btn_continue.setOnClickListener {
            startActivity(
                putExtras(intent, Intent(this, CameraActivity::class.java), teamNum),
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    btn_continue, "proceed_button"
                ).toBundle()
            )
        }
    }
}