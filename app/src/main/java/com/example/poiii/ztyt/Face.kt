package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
class Face(var materialName: String = "") {
    val vIndices = ArrayList<Int>()
    val nIndices = ArrayList<Int>()
    val tIndices = ArrayList<Int>()
    var normal = floatArrayOf(0.0f, 0.0f, 0.0f)
    var numIndeices = 0
}