package com.example.poiii.ztyt

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by poiii on 2018/1/22.
 */
class CameraView(activity: Context) : GLSurfaceView(activity) {
    private val render = CameraRender(activity)

    init {
        this.setEGLContextClientVersion(2)
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        this.setRenderer(render)
        this.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    fun addObject(component: Component) {
        render.addComponent(component)
    }

    private class CameraRender(activity: Context) : GLSurfaceView.Renderer {
        private val vertexShaderCode = Utils.loadShaderCode(activity, R.raw.vertex)
        private val fragmentShaderCode = Utils.loadShaderCode(activity, R.raw.fragment)
        private var mProgram = 0
        private var vertexShader = 0
        private var fragShader = 0

        private val components = ArrayList<Component>()
        //(0, { _ -> Component(activity) })

        override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f)
            vertexShader = Utils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
            fragShader = Utils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
            mProgram = GLES20.glCreateProgram()
            GLES20.glAttachShader(mProgram, vertexShader)
            GLES20.glAttachShader(mProgram, fragShader)
            GLES20.glLinkProgram(mProgram)
        }

        override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
        }

        override fun onDrawFrame(p0: GL10?) {
            for (component in components) {
                component.draw(mProgram)
            }
        }

        fun addComponent(component: Component) {
            components.add(component)
        }
    }
}