package com.example.poiii.ztyt

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by poiii on 2018/1/15.
 */
object Utils {
    @JvmStatic
    fun loadShader(context: Context, id: Int): String {
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
}