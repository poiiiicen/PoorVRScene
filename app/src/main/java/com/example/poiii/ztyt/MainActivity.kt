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

        //addObj(R.raw.skybox_obj, 50.0f)
        val option = BitmapFactory.Options()
        //option.inPreferredConfig = Bitmap.Config
        //val img = BitmapFactory.decodeResource(resources, R.drawable.down)
        //Log.i("image", img.toString())


        addObj(R.raw.cube_obj, 6.0f, true, floatArrayOf(0.0f, -2.5f, 0.0f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.table_obj, 3.2f, true, floatArrayOf(-0.85f, -8.5f, -7.5f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.chair_obj, 4.4f, true, floatArrayOf(-2.2f, -8.5f, -9.7f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.door_obj, 0.035f, true, floatArrayOf(1.0f, -8.47f, 12.0f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.doorframe_obj, 0.035f, true, floatArrayOf(1.0f, -8.47f, 12.0f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.poubelleinox_obj, 0.024f, true, floatArrayOf(3.7f, -7.0f, -8.0f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.tvcenter_obj, 0.05f, true, floatArrayOf(-5.0f, -4.8f, -2.0f), true, (Math.PI * 0.5f).toFloat(), floatArrayOf(0.0f, 1.0f, 0.0f))
        addObj(R.raw.shelf_obj, 4.0f, true, floatArrayOf(5.0f, -8.5f, 5.0f), true, (Math.PI * 0.5f).toFloat(), floatArrayOf(0.0f, 1.0f, 0.0f))
        addObj(R.raw.tv_obj, 0.05f, true, floatArrayOf(6.0f, -2.0f, 6.0f), true, (-Math.PI * 0.5f).toFloat(), floatArrayOf(0.0f, 1.0f, 0.0f))
        addObj(R.raw.window_obj, 0.033f, true, floatArrayOf(-2.8f, -3.2f, -12f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.desklamp_obj, 0.028f, true, floatArrayOf(0.8f, -6.2f, -8.0f), false, 0.0f, floatArrayOf(0.0f, 0.0f, 0.0f))
        addObj(R.raw.clock_obj, 0.045f, true, floatArrayOf(2.0f, -1.0f, -12.0f), true, (Math.PI * 0.5f).toFloat(), floatArrayOf(1.0f, 0.0f, 0.0f))

        Timer().scheduleAtFixedRate(task, interval.toLong(), interval.toLong())
    }

    private fun cameraInit(camera: GLSurfaceView, render: GLSurfaceView.Renderer) {
        camera.setEGLContextClientVersion(2)
        camera.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        camera.setRenderer(render)
        camera.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    private fun addObj(obj: Int, scale: Float, isMoved: Boolean, move: FloatArray, isRotated: Boolean, angualr: Float, axis: FloatArray) {
        val objLoader = ObjcetLoader(this, obj, scale)
        val list = objLoader.getObject()
        for (entry in list) {
            Scene.addObject(entry)
            if (isMoved) {
                entry.translate(move)
            }
            if (isRotated) {
                entry.rotate(angualr, axis)
            }
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
