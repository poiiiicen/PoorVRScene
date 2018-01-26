package com.example.poiii.ztyt

import android.opengl.Matrix
import java.util.*

/**
 * Created by poi on 1/25/18.
 */
class Wander : TimerTask() {

    private var counter = 0
    private val angular = 180.0f / 120.0f

    override fun run() {
        counter++
        if (counter < 500) {
            setCameraMove(Scene.leftCamera, floatArrayOf(0.0f, 0.0f, 0.0f),
                    -angular, 0.0f, Scene.xAxis, Scene.yAxis)
            setCameraMove(Scene.rightCamera, floatArrayOf(0.0f, 0.0f, 0.0f),
                    -angular, 0.0f, Scene.xAxis, Scene.yAxis)
            val matrix = Utils.loadIdentity()
            Matrix.rotateM(matrix, 0, angular, Scene.xAxis[0], Scene.xAxis[1], Scene.xAxis[2])
            Matrix.multiplyMV(Scene.yAxis, 0, matrix, 0, Scene.yAxis, 0)
        } else if (counter < 1000) {
            setCameraMove(Scene.leftCamera, floatArrayOf(0.0f, 0.0f, 0.0f),
                    0.0f, -angular, Scene.xAxis, Scene.yAxis)
            setCameraMove(Scene.rightCamera, floatArrayOf(0.0f, 0.0f, 0.0f),
                    0.0f, -angular, Scene.xAxis, Scene.yAxis)
            val matrix = Utils.loadIdentity()
            Matrix.rotateM(matrix, 0, angular, Scene.yAxis[0], Scene.yAxis[1], Scene.yAxis[2])
            Matrix.multiplyMV(Scene.xAxis, 0, matrix, 0, Scene.xAxis, 0)
        } else counter = 0
    }

    private fun setCameraMove(camera: Camera, move: FloatArray, xAngular: Float, yAngular: Float, xAxis: FloatArray, yAxis: FloatArray) {
        val pos = camera.getPosition()
        val look = camera.getLook()
        val up = camera.getUp()
        val matrix = Utils.loadIdentity()
        Matrix.rotateM(matrix, 0, matrix, 0, xAngular, xAxis[0], xAxis[1], xAxis[2])
        Matrix.rotateM(matrix, 0, matrix, 0, yAngular, yAxis[0], yAxis[1], yAxis[2])
        Matrix.translateM(matrix, 0, matrix, 0, move[0], move[1], move[2])
        val newPos = floatArrayOf(pos[0], pos[1], pos[2], 1.0f)
        val newLook = floatArrayOf(look[0], look[1], look[2], 1.0f)
        val newUp = floatArrayOf(up[0], up[1], up[2], 1.0f)
        Matrix.multiplyMV(newPos, 0, matrix, 0, newPos, 0)
        Matrix.multiplyMV(newLook, 0, matrix, 0, newLook, 0)
        Matrix.multiplyMV(newUp, 0, matrix, 0, newUp, 0)
        camera.setPosition(floatArrayOf(newPos[0], newPos[1], newPos[2]))
        camera.lookAt(floatArrayOf(newLook[0], newLook[1], newLook[2]), floatArrayOf(newUp[0], newUp[1], newUp[2]))
    }

}