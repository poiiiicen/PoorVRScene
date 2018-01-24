package com.example.poiii.ztyt

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import android.opengl.GLES20
import android.content.ContentValues.TAG
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


/**
 * Created by poiii on 2018/1/15.
 */
object Utils {
    @JvmStatic
    fun loadShaderCode(context: Context, id: Int): String {
        val builder = StringBuilder()
        val inputStream = context.resources.openRawResource(id)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line = reader.readLine()
        while (line != null) {
            builder.append(line)
                    .append('\n')
            line = reader.readLine()
        }
        reader.close()
        return builder.toString()
    }

    @JvmStatic
    fun loadShader(shaderType: Int, source: String): Int {
        var shader = GLES20.glCreateShader(shaderType)
        if (0 != shader) {
            GLES20.glShaderSource(shader, source)
            GLES20.glCompileShader(shader)
            val compiled = IntArray(1)
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader:" + shaderType)
                Log.e(TAG, "GLES20 Error:" + GLES20.glGetShaderInfoLog(shader))
                GLES20.glDeleteShader(shader)
                shader = 0
            }
        }
        return shader
    }

    @JvmStatic
    fun loadIdentity(): FloatArray {
        return floatArrayOf(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
    }

    @JvmStatic
    fun array2Buffer(array: FloatArray): FloatBuffer {
        val bb = ByteBuffer.allocateDirect(array.size * 4)
        bb.order(ByteOrder.nativeOrder())
        val ret = bb.asFloatBuffer()
        ret.put(array)
        ret.position(0)
        return ret
    }
}