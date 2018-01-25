package com.example.poiii.ztyt

/**
 * Created by poi on 1/25/18.
 */
class StringParser(private var str: String) {
    private var index = 0

    fun getWord(): String? {
        while (index < str.length) {
            val c = str[index]
            if (!(c == '\t' || c == ' ' || c == '(' || c == ')' || c == '"')) break
            index++
        }
        var end = index
        while (end < str.length) {
            val c = str[end]
            if (c == '\t' || c == ' ' || c == '(' || c == ')' || c == '"') break
            end++
        }
        val ret = if (end == index) null else str.substring(index, end)
        index = end + 1
        return ret
    }

    private fun skipDelimiters(start: Int): Int {
        while (start < str.length) {
            val c = str[start]
            if (!(c == '\t' || c == ' ' || c == '(' || c == ')' || c == '"')) break
        }
        return start
    }
}