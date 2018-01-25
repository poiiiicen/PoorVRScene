package com.example.poiii.ztyt

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var cameraView: GLSurfaceView
    private lateinit var cameraRender: CameraRender

    private val interval = 100
    private val task = Wander()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Scene.lightingShader.setShader(this, R.raw.vertex, R.raw.fragment)

        cameraRender = CameraRender(this)

        cameraView = findViewById(R.id.left_camera)
        cameraInit(cameraView, cameraRender)

        /*
        Scene.addObject(Triangle(this, floatArrayOf(5.0f, 5.0f, 0.0f, // top
                -5.0f, -5.0f, 0.0f, // bottom left
                5.0f, -5.0f, 0.0f  // bottom right
        )))
        */

        addObj(R.raw.skybox_obj, 50.0f)
        val option = BitmapFactory.Options()
        //option.inPreferredConfig = Bitmap.Config
        //val img = BitmapFactory.decodeResource(resources, R.drawable.down)
        //Log.i("image", img.toString())

        /*
        addObj(R.raw.cube_obj, 60.0f)
        addObj(R.raw.table_obj, 32.0f)
        addObj(R.raw.chair_obj, 44.0f)
        addObj(R.raw.door_obj, 0.35f)
        addObj(R.raw.doorframe_obj, 0.35f)
        addObj(R.raw.poubelleinox_obj, 0.24f)
        addObj(R.raw.tvcenter_obj, 0.5f)
        addObj(R.raw.tv_obj, 0.5f)
        */

        //Timer().scheduleAtFixedRate(task, interval.toLong(), interval.toLong())
    }

    private fun cameraInit(camera: GLSurfaceView, render: GLSurfaceView.Renderer) {
        camera.setEGLContextClientVersion(2)
        camera.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        camera.setRenderer(render)
        camera.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    private fun addObj(obj: Int, scale: Float) {
        val objLoader = ObjcetLoader(this, obj, scale)
        val list = objLoader.getObject()
        for (entry in list) {
            Scene.addObject(entry)
        }
        /*
        objLoader.getObject().forEach({
            Scene.addObject(it)
        })
        */
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
