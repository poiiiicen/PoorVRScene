package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
object Scene {
    val components = ArrayList<Component>()
    val lightingShader = LightingShader()
    var shaderInited = false

    fun addObject(component: Component) {
        components.add(component)
    }
}