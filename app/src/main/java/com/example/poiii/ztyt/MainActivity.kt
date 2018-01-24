package com.example.poiii.ztyt

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout

class MainActivity : AppCompatActivity() {

    private lateinit var leftCameraView: GLSurfaceView
    private lateinit var rightCameraView: GLSurfaceView
    private lateinit var leftCameraRender: CameraRender
    private lateinit var rightCameraRender: CameraRender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leftCameraRender = CameraRender(this)
        rightCameraRender = CameraRender(this)

        leftCameraView = findViewById(R.id.left_camera)
        cameraInit(leftCameraView, leftCameraRender);
        rightCameraView = findViewById(R.id.right_camera)
        cameraInit(rightCameraView, rightCameraRender)


        leftCameraRender.addObject(Triangle(this, floatArrayOf(0.5f, 0.5f, 0.0f, // top
                -0.5f, -0.5f, 0.0f, // bottom left
                0.5f, -0.5f, 0.0f  // bottom right
        )))
    }

    private fun cameraInit(camera: GLSurfaceView, render: GLSurfaceView.Renderer) {
        camera.setEGLContextClientVersion(2)
        camera.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        camera.setRenderer(render)
        camera.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onPause() {
        super.onPause()
        leftCameraView.onPause()
        rightCameraView.onPause()
    }

    override fun onResume() {
        super.onResume()
        leftCameraView.onResume()
        rightCameraView.onResume()
    }
}
