package com.example.poiii.ztyt

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import android.opengl.GLES20
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Matrix
import android.util.Log
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer


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
        val matrix = floatArrayOf(
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f)
        Matrix.setIdentityM(matrix, 0)
        return matrix
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

    @JvmStatic
    fun array2Buffer(array: IntArray): IntBuffer {
        val bb = ByteBuffer.allocateDirect(array.size * 4)
        bb.order(ByteOrder.nativeOrder())
        val ret = bb.asIntBuffer()
        ret.put(array)
        ret.position(0)
        return ret
    }

    @JvmStatic
    fun loadContent(context: Context, fileId: Int): String {
        val builder = StringBuilder()
        val inputStream = context.resources.openRawResource(fileId)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line = reader.readLine()
        while (line != null) {
            builder.append(line).append('\n')
            line = reader.readLine()
        }
        reader.close()
        return builder.toString()
    }

    @JvmStatic
    fun loadContent(context: Context, filename: String): String {
        val builder = StringBuilder()
        val inputStream = context.javaClass.getResourceAsStream(filename)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line = reader.readLine()
        while (line != null) {
            builder.append(line).append('\n')
            line = reader.readLine()
        }
        reader.close()
        return builder.toString()
    }

    @JvmStatic
    fun calcNormal(p0: FloatArray, p1: FloatArray, p2: FloatArray): FloatArray {
        val v0 = floatArrayOf(0.0f, 0.0f, 0.0f)
        val v1 = floatArrayOf(0.0f, 0.0f, 0.0f)
        for (i in 0 until 3) {
            v0[i] = p0[i] - p1[i]
            v1[i] = p2[i] - p1[i]
        }

        val c = floatArrayOf(
                v0[1] * v1[2] - v0[2] * v1[1],
                v0[2] * v1[0] - v0[0] * v1[2],
                v0[0] * v1[1] - v0[1] * v1[0]
        )
        return normalize(c)
    }

    @JvmStatic
    fun normalize(v: FloatArray): FloatArray {
        var g = Math.sqrt((v[0] * v[0] + v[1] * v[1] + v[2] * v[2]).toDouble())
        if (g == 1.0) {
            return v
        } else if (g == 0.0) {
            return floatArrayOf(0.0f, 0.0f, 0.0f)
        }
        g = 1.0 / g
        return floatArrayOf((v[0] * g).toFloat(), (v[1] * g).toFloat(), (v[2] * g).toFloat())
    }

    @JvmStatic
    fun loadImage(context: Context, source: String): Bitmap? {
        if (source == "") return null
        return BitmapFactory.decodeResource(context.resources, context.resources.getIdentifier(source, "raw", context.packageName))
    }
}