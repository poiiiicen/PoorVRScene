package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
class PointLight(pos: FloatArray,
                 lightColor: FloatArray,
                 lightAmbientCoeff: Float,
                 on: Boolean) : Light(pos, lightColor, lightAmbientCoeff, on) {
}