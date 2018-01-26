package com.example.poiii.ztyt

import android.content.Context
import android.opengl.Matrix
import android.util.Log

/**
 * Created by poi on 1/25/18.
 */
class ObjcetLoader(private val context: Context, private val fileId: Int, private val scale: Float) {

    private val content = Utils.loadContent(context, fileId)
    private val vertices = ArrayList<Float>()
    private val normals = ArrayList<Float>()
    private val materials = ArrayList<NamedMaterial>()
    private var useMaterial: NamedMaterial? = null
    private val materialParts = ArrayList<MaterialPart>()
    private var currentMaterialPart: MaterialPart? = null
    private var textureVt = ArrayList<Float>()

    private val defaultVT = floatArrayOf(
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
    )

    fun getObject(): ArrayList<UniversalObject> {
        OBJParser()
        val retObjs = ArrayList<UniversalObject>()
        for (entry in materialParts) {
            val material = if (entry.material!!.getKd()[0] < 0.0f)
                Material(floatArrayOf(-1.0f, -1.0f, -1.0f),
                        floatArrayOf(-1.0f, -1.0f, -1.0f),
                        floatArrayOf(-1.0f, -1.0f, -1.0f),
                        -1.0f)
            else entry.material!!.changeToMaterial()

            var obj: UniversalObject
            val img = Utils.loadImage(context, entry.material!!.texture)
            obj = UniversalObject(context,
                    entry.positions.toFloatArray(),
                    entry.normals.toFloatArray(),
                    entry.indices.toIntArray(),
                    entry.vt.toFloatArray(),
                    img,
                    material)
            retObjs.add(obj)
        }
        return retObjs
    }

    private fun OBJParser() {
        val lines = content.split('\n')
        var currentMaterialName = ""
        var mtlUsed = false

        for (line in lines) {
            val sp = StringParser(line)
            val command = sp.getWord() ?: continue
            when (command) {
                "mtllib" -> {
                    val file = sp.getWord()!!.replace(Regex("\\."), "_")
                    val mtlContent = Utils.loadContent(context, context.resources.getIdentifier(file, "raw", context.packageName))//Config.PATH + file)
                    readMTLFile(mtlContent)
                }
                "v" -> {
                    vertices.add(sp.getWord()!!.toFloat() * scale)
                    vertices.add(sp.getWord()!!.toFloat() * scale)
                    vertices.add(sp.getWord()!!.toFloat() * scale)
                }
                "vn" -> {
                    normals.add(sp.getWord()!!.toFloat())
                    normals.add(sp.getWord()!!.toFloat())
                    normals.add(sp.getWord()!!.toFloat())
                }
                "usemtl" -> {
                    mtlUsed = true
                    currentMaterialName = sp.getWord()!!
                    materials
                            .filter { it.name == currentMaterialName }
                            .forEach { this.useMaterial = it }
                    currentMaterialPart?.let { materialParts.add(it) }
                    currentMaterialPart = MaterialPart()
                    currentMaterialPart?.let { it.material = useMaterial }
                }
                "f" -> {
                    if (!mtlUsed) {
                        mtlUsed = true
                        currentMaterialPart = MaterialPart()
                    }
                    parseFace(sp, currentMaterialName, vertices, textureVt, normals)
                }
                "vt" -> {
                    textureVt.add(sp.getWord()!!.toFloat() * scale)
                    textureVt.add(sp.getWord()!!.toFloat() * scale)
                }
            }
        }
        currentMaterialPart?.let { materialParts.add(it) }
    }

    private fun parseFace(sp: StringParser, materialName: String, vertices: ArrayList<Float>, textureVt: ArrayList<Float>, normals: ArrayList<Float>): Face {
        val face = Face(materialName)
        var i = 0
        var t = 0
        while (true) {
            val word = sp.getWord()
            if (word == null || word.replace(Regex("^\\s+|\\s+$/g"), "") == "") break
            val subWord = word.split('/')

            if (subWord.isNotEmpty()) {
                val vi = if (subWord[0].toInt() < 0) vertices.size / 2 + subWord[0].toInt() else subWord[0].toInt() - 1
                face.vIndices.add(vi)
                currentMaterialPart?.positions?.add(vertices[vi * 3])
                currentMaterialPart?.positions?.add(vertices[vi * 3 + 1])
                currentMaterialPart?.positions?.add(vertices[vi * 3 + 2])
                face.vIndices[i] = currentMaterialPart!!.count
                currentMaterialPart?.let { it.count++ }
            }
            if (subWord.size > 1 && subWord[1] != "") {
                val ti = if (subWord[1].toInt() < 0) textureVt.size / 2 + subWord[1].toInt() else subWord[1].toInt() - 1
                face.tIndices.add(ti)
                currentMaterialPart?.vt?.add(textureVt[ti * 2])
                currentMaterialPart?.vt?.add(textureVt[ti * 2 + 1])
            } else {
                if (i > 3) {
                    Log.e("too large", i.toString())
                    currentMaterialPart?.vt?.add(defaultVT[(t % 4) * 2])
                    currentMaterialPart?.vt?.add(defaultVT[(t % 4) * 2 + 1])
                    t++
                    Log.e("too large t", t.toString())
                } else {
                    currentMaterialPart?.vt?.add(defaultVT[i * 2])
                    currentMaterialPart?.vt?.add(defaultVT[i * 2 + 1])
                }
            }
            if (subWord.size > 2) {
                val ni = if (subWord[2].toInt() < 0) normals.size / 2 + subWord[2].toInt() else subWord[2].toInt() - 1
                face.nIndices.add(ni)
                currentMaterialPart?.normals?.add(normals[ni * 3])
                currentMaterialPart?.normals?.add(normals[ni * 3 + 1])
                currentMaterialPart?.normals?.add(normals[ni * 3 + 2])
            } else {
                face.nIndices.add(-1)
            }
            i++
        }

        if (face.nIndices[0] == -1) {
            val p0 = floatArrayOf(currentMaterialPart!!.positions[3 * face.vIndices[2]],
                    currentMaterialPart!!.positions[3 * face.vIndices[2] + 1],
                    currentMaterialPart!!.positions[3 * face.vIndices[2] + 2])
            val p1 = floatArrayOf(currentMaterialPart!!.positions[3 * face.vIndices[1]],
                    currentMaterialPart!!.positions[3 * face.vIndices[1] + 1],
                    currentMaterialPart!!.positions[3 * face.vIndices[1] + 2])
            val p2 = floatArrayOf(currentMaterialPart!!.positions[3 * face.vIndices[0]],
                    currentMaterialPart!!.positions[3 * face.vIndices[0] + 1],
                    currentMaterialPart!!.positions[3 * face.vIndices[0] + 2])
            var normal = Utils.calcNormal(p0, p1, p2)

            if (normal[0] == 0.0f && normal[1] == 0.0f && normal[2] == 0.0f) {
                if (face.vIndices.size > 3) {
                    val p3 = floatArrayOf(currentMaterialPart!!.positions[3 * face.vIndices[3]],
                            currentMaterialPart!!.positions[3 * face.vIndices[3] + 1],
                            currentMaterialPart!!.positions[3 * face.vIndices[3] + 2])
                    normal = Utils.calcNormal(p1, p2, p3)
                }
                if (normal[0] == 0.0f && normal[1] == 0.0f && normal[2] == 0.0f) {
                    normal = floatArrayOf(0.0f, 1.0f, 0.0f)
                }
            }
            face.normal = normal
            for (j in 0 until face.vIndices.size) {
                currentMaterialPart?.normals?.add(normal[0])
                currentMaterialPart?.normals?.add(normal[1])
                currentMaterialPart?.normals?.add(normal[2])
            }
        }
        if (face.vIndices.size > 2) {
            val n = face.vIndices.size - 2
            for (j in 0 until n) {
                currentMaterialPart?.indices?.add(face.vIndices[0])
                currentMaterialPart?.indices?.add(face.vIndices[j + 1])
                currentMaterialPart?.indices?.add(face.vIndices[j + 2])
            }
        }
        face.numIndeices = face.vIndices.size
        return face
    }

    private fun readMTLFile(mtlContent: String) {
        val lines = mtlContent.split('\n')
        var currentMaterial: NamedMaterial? = null
        var name: String

        for (line in lines) {
            val sp = StringParser(line)
            val command = sp.getWord() ?: continue
            when (command) {
                "newmtl" -> {
                    name = sp.getWord()!!
                    if (currentMaterial != null) {
                        materials.add(currentMaterial)
                    }
                    currentMaterial = NamedMaterial(name)
                }
                "Ka" -> {
                    if (currentMaterial != null) {
                        currentMaterial.setKa(floatArrayOf(sp.getWord()!!.toFloat(), sp.getWord()!!.toFloat(), sp.getWord()!!.toFloat()))
                    }
                }
                "Kd" -> {
                    if (currentMaterial != null) {
                        currentMaterial.setKd(floatArrayOf(sp.getWord()!!.toFloat(), sp.getWord()!!.toFloat(), sp.getWord()!!.toFloat()))
                    }
                }
                "Ks" -> {
                    if (currentMaterial != null) {
                        currentMaterial.setKs(floatArrayOf(sp.getWord()!!.toFloat(), sp.getWord()!!.toFloat(), sp.getWord()!!.toFloat()))
                    }
                }
                else -> {
                    if (command.endsWith(".jpg")) {
                        if (currentMaterial != null)
                            currentMaterial.texture = command.substringBeforeLast('.')
                    }
                }
            }
            if (currentMaterial != null) materials.add(currentMaterial)
        }
    }
}