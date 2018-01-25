package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
abstract class Light(protected var pos: FloatArray,
                     protected var lightColor: FloatArray,
                     protected var lightAmbientCoeff: Float,
                     protected var on: Boolean) {

    fun isOn(): Boolean {
        return on
    }

    fun turnOn() {
        on = true
    }

    fun turnOff() {
        on = false
    }

    fun getColor(): FloatArray {
        return lightColor
    }

    fun setColor(color: FloatArray) {
        if (color.size == 3) {
            lightColor = color
        }
    }

    fun getAmbientCoeff(): Float {
        return lightAmbientCoeff
    }

    fun setAmbientCoeff(value: Float) {
        lightAmbientCoeff = value
    }

    fun getPosition(): FloatArray {
        return pos
    }

    fun setPosition(value: FloatArray) {
        pos = value
    }

}