package com.example.poiii.ztyt

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import android.opengl.Matrix
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Created by poiii on 2018/1/22.
 */
open class UniversalObject(context: Context,
                           private val vertices: FloatArray,
                           protected val normals: FloatArray,
                           protected val indices: IntArray,
                           protected val textureCoords: FloatArray,
                           protected val textureImage: Bitmap?,
                           protected val material: Material) : Component(context) {

    private val COORDS_PER_VERTEX = 3
    private val vertexStride = COORDS_PER_VERTEX * 4
    private val vertexCount = vertices.size / COORDS_PER_VERTEX

    private val texture = intArrayOf(0)

    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var normalBuffer: FloatBuffer
    private lateinit var texCoordBuffer: FloatBuffer
    private lateinit var indexBuffer: IntBuffer

    override fun initDraw() {
        if (textureImage != null) {
            GLES20.glGenTextures(1, texture, 0)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureImage, 0)
            //GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        }
        vertexBuffer = Utils.array2Buffer(vertices)
        normalBuffer = Utils.array2Buffer(normals)
        texCoordBuffer = Utils.array2Buffer(textureCoords)
        indexBuffer = Utils.array2Buffer(indices)

        inited = true
    }

    override fun draw(mProgram: Int, camera: Camera, modelMatrix: FloatArray) {
        if (!inited) {
            initDraw()
        }

        val modelMat = Utils.loadIdentity()
        Matrix.multiplyMM(modelMat, 0, modelMatrix, 0, this.modelMatrix, 0)
        val modelNormalMat = Utils.loadIdentity()
        Matrix.invertM(modelNormalMat, 0, modelMat, 0)
        Matrix.transposeM(modelNormalMat, 0, modelNormalMat, 0)

        Scene.lightingShader.setCamera(camera)
        Scene.lightingShader.setLight(Scene.light)

        GLES20.glUseProgram(mProgram)
        val mPerspectiveMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uPerspectiveMatrix")
        GLES20.glUniformMatrix4fv(mPerspectiveMatrixHandle, 1, false, camera.getPerspectiveMatrix(), 0)
        val mViewMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uViewMatrix")
        GLES20.glUniformMatrix4fv(mViewMatrixHandle, 1, false, camera.getViewMatrix(), 0)
        val mModelMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uModelMatrix")
        GLES20.glUniformMatrix4fv(mModelMatrixHandle, 1, false, modelMat, 0)

        val mModelNormalMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uModelNormalMatrix")
        GLES20.glUniformMatrix4fv(mModelNormalMatrixHandle, 1, false, modelNormalMat, 0)

        val mMaterialAmbient = GLES20.glGetUniformLocation(mProgram, "uMaterial.ambient")
        GLES20.glUniform3fv(mMaterialAmbient, 1, material.ambientColor, 0)
        val mMaterialDiffuse = GLES20.glGetUniformLocation(mProgram, "uMaterial.diffuse")
        GLES20.glUniform3fv(mMaterialDiffuse, 1, material.diffuseColor, 0)
        val mMaterialSpecular = GLES20.glGetUniformLocation(mProgram, "uMaterial.specular")
        GLES20.glUniform3fv(mMaterialSpecular, 1, material.specularColor, 0)
        val mMaterialShininess = GLES20.glGetUniformLocation(mProgram, "uMaterial.shininess")
        GLES20.glUniform1f(mMaterialShininess, material.shininess)

        if (textureImage != null) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
            val texDiffuseMap = GLES20.glGetUniformLocation(mProgram, "uMaterial.diffuseMap")
            GLES20.glUniform1i(texDiffuseMap, 0)
            val mTextureMap = GLES20.glGetUniformLocation(mProgram, "uMaterial.useDiffuseMap")
            GLES20.glUniform1f(mTextureMap, 0.0f)
        } else {
            val mTextureMap = GLES20.glGetUniformLocation(mProgram, "uMaterial.useDiffuseMap")
            GLES20.glUniform1f(mTextureMap, 0.0f)
        }

        val mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer)
        val mNormalHandle = GLES20.glGetAttribLocation(mProgram, "vNormal")
        GLES20.glEnableVertexAttribArray(mNormalHandle)
        GLES20.glVertexAttribPointer(mNormalHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, normalBuffer)
        val mTexCoordsHandle = GLES20.glGetAttribLocation(mProgram, "vTexCoord")
        GLES20.glEnableVertexAttribArray(mTexCoordsHandle)
        GLES20.glVertexAttribPointer(mTexCoordsHandle, 2, GLES20.GL_FLOAT, false, 2 * 4, texCoordBuffer)
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.size, GLES20.GL_UNSIGNED_INT, indexBuffer)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
        GLES20.glDisableVertexAttribArray(mNormalHandle)
        GLES20.glDisableVertexAttribArray(mTexCoordsHandle)
    }

    override fun drawChild(mProgram: Int, camera: Camera, modelMatrix: FloatArray) {
        for (component in children) {
            component.draw(mProgram, camera, modelMatrix)
            component.drawChild(mProgram, camera, modelMatrix)
        }
    }
}