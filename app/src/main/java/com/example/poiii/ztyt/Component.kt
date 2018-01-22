package com.example.poiii.ztyt

import android.content.Context

/**
 * Created by poiii on 2018/1/22.
 */
abstract class Component(protected val context: Context) {
    protected var position = floatArrayOf(0.0f, 0.0f, 0.0f)
    abstract fun draw(mProgram: Int)
}