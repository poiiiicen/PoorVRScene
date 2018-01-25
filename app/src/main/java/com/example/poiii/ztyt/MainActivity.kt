package com.example.poiii.ztyt

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var cameraView: GLSurfaceView
    private lateinit var cameraRender: CameraRender

    private val interval = 100
    //private lateinit var timer: Timer
    private val task = Wander()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Scene.lightingShader.setShader(this, R.raw.vertex, R.raw.fragment)

        cameraRender = CameraRender(this)

        cameraView = findViewById(R.id.left_camera)
        cameraInit(cameraView, cameraRender);

        Scene.addObject(Triangle(this, floatArrayOf(5.0f, 5.0f, 0.0f, // top
                -5.0f, -5.0f, 0.0f, // bottom left
                5.0f, -5.0f, 0.0f  // bottom right
        )))

        Timer().scheduleAtFixedRate(task, interval.toLong(), interval.toLong())
    }

    private fun cameraInit(camera: GLSurfaceView, render: GLSurfaceView.Renderer) {
        camera.setEGLContextClientVersion(2)
        camera.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        camera.setRenderer(render)
        camera.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onPause() {
        super.onPause()
        cameraView.onPause()
    }

    override fun onResume() {
        super.onResume()
        cameraView.onResume()
    }
}
