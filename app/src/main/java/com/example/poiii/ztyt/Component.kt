package com.example.poiii.ztyt

import android.content.Context

/**
 * Created by poiii on 2018/1/22.
 */
abstract class Component(private val context: Context) {
    protected var position = floatArrayOf(0.0f, 0.0f, 0.0f)
    private val modelMatrix = Utils.loadIdentity()
    private var shaderCode = ""
    private var shader = 0

    var inited = false

    val children = ArrayList<Component>()

    open fun setShader(id: Int) {
        shaderCode = Utils.loadShaderCode(context, id)
        inited = false
    }

    abstract fun initDraw()

    abstract fun draw(mProgram: Int)

    abstract fun drawChild(mProgram: Int)
}