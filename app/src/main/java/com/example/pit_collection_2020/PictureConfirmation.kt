package com.example.pit_collection_2020

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.camera_confirmation.*
import kotlinx.android.synthetic.main.camera_preview.*
import java.io.File

class PictureConfirmation : AppCompatActivity() {
    private var fileName : String? = null
    private var teamNum : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_confirmation)

        teamNum = Integer.parseInt(intent.getStringExtra("teamNumber").toString())
        fileName = intent.getStringExtra("fileName")

        tv_team_number_confirm.setText("$teamNum")

        displayImage(fileName!!)
        setOnClickListeners("$teamNum","$fileName")
    }

    fun displayImage(fileName:String){
        val imgFile = File(fileName)

        if (imgFile.exists()) {
            var myBitmap: Bitmap = BitmapFactory.decodeFile(fileName)
            iv_picture_confirm.setImageBitmap(myBitmap)

        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, PitCollectionActivity::class.java)
        intent.putExtra("teamNumber", "$teamNum"
        )
        startActivity(intent)
    }


    fun setOnClickListeners(teamNum:String, fileName:String){
        delete.setOnClickListener{
            File(fileName).delete()
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("teamNumber", teamNum)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                delete, "proceed_button").toBundle())
        }
        btn_continue.setOnClickListener(){
            val intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("teamNumber", teamNum)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                btn_continue, "proceed_button").toBundle())
        }
    }
}