package com.example.poiii.ztyt

import android.content.Context
import android.opengl.Matrix

/**
 * Created by poiii on 2018/1/22.
 */
abstract class Component(protected val context: Context) {
    protected val modelMatrix = Utils.loadIdentity()

    protected var inited = false

    protected val children = ArrayList<Component>()

    open fun getModelMat(): FloatArray {
        return modelMatrix
    }

    abstract fun initDraw()

    abstract fun draw(mProgram: Int, camera: Camera, modelMatrix: FloatArray)

    abstract fun drawChild(mProgram: Int, camera: Camera, modelMatrix: FloatArray)

    open fun translate(move: FloatArray) {
        Matrix.translateM(modelMatrix, 0, modelMatrix, 0, move[0], move[1], move[2])
    }

    open fun rotate(angular: Float, axis: FloatArray) {
        Matrix.rotateM(modelMatrix, 0, modelMatrix, 0, angular, axis[0], axis[1], axis[2])
    }
}