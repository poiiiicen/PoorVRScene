package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
class NamedMaterial(val name: String) {
    var texture = ""
    private var ka = floatArrayOf(0.2f, 0.2f, 0.2f)
    private var kd = floatArrayOf(0.8f, 0.8f, 0.8f)
    private var ks = floatArrayOf(0.0f, 0.0f, 0.0f)

    fun setKa(color: FloatArray) {
        ka = color
    }

    fun getKa(): FloatArray {
        return ka
    }

    fun setKd(color: FloatArray) {
        kd = color
    }

    fun getKd(): FloatArray {
        return kd
    }

    fun setKs(color: FloatArray) {
        ks = color
    }

    fun getKs(): FloatArray {
        return ks
    }

    fun changeToMaterial(): Material {
        return Material(ka, kd, ks, 40.0f)
    }
}
