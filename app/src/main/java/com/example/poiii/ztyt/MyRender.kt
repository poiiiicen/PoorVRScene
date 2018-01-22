package com.example.poiii.ztyt

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by poiii on 2018/1/15.
 */
class MyRender(private val activity: MainActivity) : GLSurfaceView.Renderer {
    private val VERTEX_SHADER = Utils.loadShaderCode(activity, R.raw.vertex)
    private val FRAGMENT_SHADER = Utils.loadShaderCode(activity, R.raw.fragment)
    private var mProgram = 0
    private lateinit var vertexBuffer: FloatBuffer
    private var vertexShader = 0
    private var fragShader = 0

    private val triangleCoords = floatArrayOf(0.5f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    )
    private val vertexCount = 12;

    val color = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f)
        val bb = ByteBuffer.allocateDirect(triangleCoords.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(triangleCoords)
        vertexBuffer.position(0)
        vertexShader = Utils.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER)
        fragShader = Utils.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER)
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragShader)
        GLES20.glLinkProgram(mProgram)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl!!.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glUseProgram(mProgram)
        val mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer)
        val mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
}