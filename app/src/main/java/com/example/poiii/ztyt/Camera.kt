package com.example.poiii.ztyt

import android.opengl.Matrix
import java.nio.FloatBuffer

/**
 * Created by poi on 1/25/18.
 */
class Camera(private var pos: FloatArray,
             private var look: FloatArray,
             private var up: FloatArray) {

    private var perspectiveMatrix = Utils.loadIdentity()
    private var viewMatrix = Utils.loadIdentity()

    init {
        Matrix.setLookAtM(viewMatrix, 0, pos[0], pos[1], pos[2], look[0], look[1], look[2], up[0], up[1], up[2])
    }

    fun setPosition(pos: FloatArray) {
        this.pos = pos
    }

    fun getPosition(): FloatArray {
        return pos
    }

    fun setPerspective(fieldOfView: Float, aspect: Float, near: Float, far: Float) {
        Matrix.perspectiveM(perspectiveMatrix, 0, fieldOfView, aspect, near, far)
    }

    fun getPerspectiveMatrix(): FloatArray {
        return perspectiveMatrix
    }

    fun lookAt(look: FloatArray, up: FloatArray) {
        this.look = look
        this.up = up
        Matrix.setLookAtM(viewMatrix, 0, pos[0], pos[1], pos[2], look[0], look[1], look[2], up[0], up[1], up[2])
    }

    fun getViewMatrix(): FloatArray {
        return viewMatrix
    }

    fun getLook(): FloatArray {
        return look
    }

    fun getUp(): FloatArray {
        return up
    }
}