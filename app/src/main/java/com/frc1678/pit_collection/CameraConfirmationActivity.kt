package com.frc1678.pit_collection

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.camera_confirmation_activity.*
import java.io.File

class CameraConfirmationActivity : AppCompatActivity() {
    private var fileName: String? = null
    private var teamNum: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_confirmation_activity)

        toolbarText(actionBar, supportActionBar, this)

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

    override fun onBackPressed() {}

    private fun putExtras(): Intent {
        val intentToNextActivity = Intent(this, CameraActivity::class.java)
        intentToNextActivity.putExtra(
            "teamNumber", teamNum
        ).putExtra("can_cross_trench", intent.getBooleanExtra("can_cross_trench", false))
            .putExtra("has_ground_intake", intent.getBooleanExtra("has_ground_intake", false))
            .putExtra("drivetrain_pos", intent.getIntExtra("drivetrain_pos", -1))
            .putExtra("drivetrain_motor_pos", intent.getIntExtra("drivetrain_motor_pos", -1))
            .putExtra("num_motors", intent.getIntExtra("num_motors", 0))
            .putExtra("after_camera", true)
        return intentToNextActivity
    }

    private fun setOnClickListeners(teamNum: String, fileName: String) {
        delete.setOnClickListener {
            File(fileName).delete()
            startActivity(
                putExtras(), ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    delete, "proceed_button"
                ).toBundle()
            )
        }
        btn_continue.setOnClickListener {
            startActivity(
                putExtras(), ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    btn_continue, "proceed_button"
                ).toBundle()
            )
        }
    }
}