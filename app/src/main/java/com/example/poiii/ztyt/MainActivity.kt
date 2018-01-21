package com.example.poiii.ztyt

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var mGLSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mGLSurfaceView = findViewById(R.id.surface)
        mGLSurfaceView.setEGLContextClientVersion(2)
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        mGLSurfaceView.setRenderer(MyRender(this))
        mGLSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }
}
