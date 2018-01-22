package com.example.poiii.ztyt

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var leftCameraView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leftCameraView = findViewById(R.id.surface)
        leftCameraView.addObject(Triangle(this, floatArrayOf(0.5f, 0.5f, 0.0f, // top
                -0.5f, -0.5f, 0.0f, // bottom left
                0.5f, -0.5f, 0.0f  // bottom right
        )))
    }

    override fun onPause() {
        super.onPause()
        leftCameraView.onPause()
    }

    override fun onResume() {
        super.onResume()
        leftCameraView.onResume()
    }
}
