package com.example.poiii.ztyt

import android.content.Context
import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by poiii on 2018/1/22.
 */
abstract class Component(private val context: Context) {
    protected var position = floatArrayOf(0.0f, 0.0f, 0.0f)
    private val modelMatrix = Utils.loadIdentity()
    private var shaderCode = ""
    private var shader = 0

    var inited = false

    open fun setShader(id: Int) {
        shaderCode = Utils.loadShaderCode(context, id)
        inited = false
    }

    abstract fun initDraw()

    abstract fun draw(mProgram: Int)
}