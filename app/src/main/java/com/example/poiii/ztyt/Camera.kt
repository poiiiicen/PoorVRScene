package com.example.poiii.ztyt

import android.opengl.Matrix
import java.nio.FloatBuffer

/**
 * Created by poi on 1/25/18.
 */
class Camera(private var pos: FloatArray,
             private var look: FloatArray,
             private var up: FloatArray) {
    /*
    private var fieldOfView = 0.0f
    private var aspect = 0.0f
    private var near = 0.0f
    private var far = 0.0f
    private var look = floatArrayOf(0.0f, 0.0f, 0.0f)
    private var up = floatArrayOf(0.0f, 0.0f, 0.0f)
    */

    private var perspectiveMatrix = Utils.loadIdentity()
    private var perspectiveMatrixBuffer: FloatBuffer
    private var viewMatrix = Utils.loadIdentity()
    private var viewMatrixBuffer: FloatBuffer

    init {
        Matrix.setLookAtM(viewMatrix, 0, pos[0], pos[1], pos[2], look[0], look[1], look[2], up[0], up[1], up[2])
        perspectiveMatrixBuffer = Utils.array2Buffer(perspectiveMatrix)
        viewMatrixBuffer = Utils.array2Buffer(viewMatrix)
    }

    fun setPosition(pos: FloatArray) {
        this.pos = pos
    }

    fun getPosition(): FloatArray {
        return pos
    }

    fun setPerspective(fieldOfView: Float, aspect: Float, near: Float, far: Float) {
        /*
        this.fieldOfView = fieldOfView
        this.aspect = aspect
        this.near = near
        this.far = far
        */
        //perspectiveMatrix = Utils.loadIdentity()
        Matrix.frustumM(perspectiveMatrix, 0, -aspect, aspect, -1.0f, 1.0f, near, far)
        perspectiveMatrixBuffer = Utils.array2Buffer(perspectiveMatrix)
    }

    fun getPerspectiveMatrix(): FloatArray {
        return perspectiveMatrix
    }

    fun getPerspectiveMatrixBuffer(): FloatBuffer {
        return perspectiveMatrixBuffer
    }

    fun lookAt(look: FloatArray, up: FloatArray) {
        this.look = look
        this.up = up
        Matrix.setLookAtM(viewMatrix, 0, pos[0], pos[1], pos[2], look[0], look[1], look[2], up[0], up[1], up[2])
        viewMatrixBuffer = Utils.array2Buffer(viewMatrix)
    }

    fun getViewMatrix(): FloatArray {
        return viewMatrix
    }

    fun getViewMatrixBuffer(): FloatBuffer {
        return viewMatrixBuffer
    }

    fun getLook(): FloatArray {
        return look
    }

    fun getUp(): FloatArray {
        return up
    }
}