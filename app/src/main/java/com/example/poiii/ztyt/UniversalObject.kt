package com.example.poiii.ztyt

import android.content.Context
import android.graphics.Bitmap

/**
 * Created by poiii on 2018/1/22.
 */
open class UniversalObject(context: Context,
                           private val vertices: FloatArray,
                           protected val normals: FloatArray,
                           protected val indices: FloatArray,
                           protected val textureCoords: FloatArray,
                           protected val texture: Bitmap,
                           protected val material: Material) : Component(context) {
    override fun initDraw() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun draw(mProgram: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawChild(mProgram: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}