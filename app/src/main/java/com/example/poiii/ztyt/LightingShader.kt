package com.example.poiii.ztyt

import android.content.Context
import android.opengl.GLES20

/**
 * Created by poi on 1/25/18.
 */
class LightingShader : Shader() {
    override fun setShader(context: Context, vertexId: Int, fragmentId: Int) {
        vertexShaderCode = Utils.loadShaderCode(context, vertexId)
        fragmentShaderCode = Utils.loadShaderCode(context, fragmentId)
    }

    override fun shaderInit() {
        vertexShader = Utils.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        fragmentShader = Utils.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)
    }
}