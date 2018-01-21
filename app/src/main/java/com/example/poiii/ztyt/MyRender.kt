package com.example.poiii.ztyt

import android.opengl.GLSurfaceView
import android.opengl.GLU
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

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl!!.glViewport(0, 0, width, height)

        val ratio = width.toFloat() / height.toFloat()
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glLoadIdentity()
        gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 3.0f, 7.0f)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDrawFrame(gl: GL10?) {
        gl!!.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadIdentity()
        GLU.gluLookAt(gl, 0.0f, 0.0f, -5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}