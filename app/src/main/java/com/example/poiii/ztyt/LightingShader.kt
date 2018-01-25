package com.example.poiii.ztyt

import android.content.Context
import android.opengl.GLES20
import javax.microedition.khronos.opengles.GL

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

    fun setLight(light: Light) {
        if (light is PointLight) {
            val pointLightValid = GLES20.glGetUniformLocation(mProgram, "uPointLights[0].valid")
            GLES20.glUniform1f(pointLightValid, 1.0f)
            val pointLightPos = GLES20.glGetUniformLocation(mProgram, "uPointLights[0].position")
            GLES20.glUniform3fv(pointLightPos, 1, light.getPosition(), 0)
            val pointLightColor = GLES20.glGetUniformLocation(mProgram, "uPointLights[0].color")
            GLES20.glUniform3fv(pointLightColor, 1, light.getColor(), 0)
            val pointLightAmbientCoeff = GLES20.glGetUniformLocation(mProgram, "uPointLights[0].ambientCoeff")
            GLES20.glUniform1f(pointLightAmbientCoeff, light.getAmbientCoeff())
        }
        for (i in 1..Config.NUM_POINT_LIGHT) {
            val valid = GLES20.glGetUniformLocation(mProgram, "uPointLights")
            GLES20.glUniform1f(valid, 0.0f)
        }
    }

    fun setCamera(camera: Camera) {
        val cameraPos = GLES20.glGetUniformLocation(mProgram, "uCameraPos")
        GLES20.glUniform3fv(cameraPos, 1, camera.getPosition(), 0)
    }
}