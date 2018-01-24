package com.example.poiii.ztyt

import android.content.Context

/**
 * Created by poi on 1/25/18.
 */
abstract class Shader {
    var vertexShaderCode = ""
    var fragmentShaderCode = ""
    var vertexShader = 0
    var fragmentShader = 0
    var mProgram = 0

    abstract fun setShader(context: Context, vertexId: Int, fragmentId: Int)

    abstract fun shaderInit()
}