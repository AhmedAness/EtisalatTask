package com.etisalat.sampletask.bases.Activities

import android.Manifest.permission.*
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_capture_image.*
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras
import com.etisalat.sampletask.R


class CaptureImage : AppCompatActivity() {


    val PERMISSION_REQUEST_CODE = 1
    val REQUEST_IMAGE_CAPTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture_image)
        CaptureAndDisplay.setOnClickListener{
            if(!checkPersmission()){
                ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA),
                        PERMISSION_REQUEST_CODE)
            }else{
                takePicture()
            }
        }
    }

    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }
    private fun takePicture() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        &&grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    takePicture()

                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
                return
            }

            else -> {

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            val photo = data!!.getExtras().get("data") as Bitmap
            CaptureAndDisplay.setImageBitmap(photo)

        }
    }
}
