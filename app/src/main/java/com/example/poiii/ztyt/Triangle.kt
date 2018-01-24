package com.example.poiii.ztyt

import android.content.Context
import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by poiii on 2018/1/22.
 */
open class Triangle(context: Context, private val vertices: FloatArray) : Component(context) {
    private val COORDS_PER_VERTEX = 3
    private val vertexStride = COORDS_PER_VERTEX * 4
    private val vertexCount = vertices.size / COORDS_PER_VERTEX

    private lateinit var vertexBuffer: FloatBuffer

    override fun initDraw() {
        val bb = ByteBuffer.allocateDirect(vertices.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
        inited = true
    }

    override fun draw(mProgram: Int) {
        if (!inited) {
            initDraw()
        }
        GLES20.glUseProgram(mProgram)
        val mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer)
        val mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        GLES20.glUniform4fv(mColorHandle, 1, Config.red, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(mProgram)
    }

    override fun drawChild(mProgram: Int) {
        for (component in children) {
            component.draw(mProgram)
            component.drawChild(mProgram)
        }
    }
}