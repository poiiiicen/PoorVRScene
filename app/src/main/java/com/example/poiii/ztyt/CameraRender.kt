package com.example.poiii.ztyt

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by poiii on 2018/1/22.
 */
class CameraRender(activity: Context) : GLSurfaceView.Renderer {
    private var width = 0
    private var height = 0

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f)
        if (!Scene.shaderInited) {
            Scene.lightingShader.shaderInit()
        }
    }

    override fun onSurfaceChanged(p0: GL10?, width: Int, height: Int) {
        this.width = width
        this.height = height
        val ratio = width.toFloat() / height.toFloat() / 2.0f
        Scene.leftCamera.setPerspective(0.0f, ratio, 0.1f, 100.0f)
        Scene.rightCamera.setPerspective(0.0f, ratio, 0.1f, 100.0f)
    }

    override fun onDrawFrame(p0: GL10?) {
        GLES20.glViewport(0, 0, width / 2, height)
        for (component in Scene.components) {
            component.draw(Scene.lightingShader.mProgram, Scene.leftCamera, Utils.loadIdentity())
            component.drawChild(Scene.lightingShader.mProgram, Scene.leftCamera, component.getModelMatrix())
        }
        GLES20.glViewport(width / 2, 0, width / 2, height)
        for (component in Scene.components) {
            component.draw(Scene.lightingShader.mProgram, Scene.rightCamera, Utils.loadIdentity())
            component.drawChild(Scene.lightingShader.mProgram, Scene.rightCamera, component.getModelMatrix())
        }
    }

}
