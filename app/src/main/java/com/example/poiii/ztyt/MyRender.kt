package com.example.poiii.ztyt

import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by poiii on 2018/1/15.
 */
class MyRender(private val activity: MainActivity) : GLSurfaceView.Renderer {
    private val VERTEX_SHADER: String = Utils.loadShader(activity, R.raw.vertex)
    private val FRAGMENT_SHADER: String = Utils.loadShader(activity, R.raw.fragment)

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDrawFrame(p0: GL10?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}