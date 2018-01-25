package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
object Scene {
    val components = ArrayList<Component>()
    val lightingShader = LightingShader()
    var shaderInited = false

    val light = PointLight(floatArrayOf(0.0f, 0.0f, 2.0f), Config.gray, 0.2f, true)

    val leftCamera = Camera(floatArrayOf(-8.0f, 0.0f, 1.0f), floatArrayOf(-8.0f, 0.0f, 0.0f), floatArrayOf(0.0f, 1.0f, 0.0f))
    val rightCamera = Camera(floatArrayOf(8.0f, 0.0f, 1.0f), floatArrayOf(8.0f, 0.0f, 0.0f), floatArrayOf(0.0f, 1.0f, 0.0f))

    fun addObject(component: Component) {
        components.add(component)
    }
}