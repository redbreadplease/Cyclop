package com.redbreadplease.cyclop.libgdx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3

class Models {
    private val modelBuilder = ModelBuilder()

    companion object {
        const val liningSphereRadius: Float = 1400f // default: 400f
    }

    fun getModelInstances(): MutableList<ModelInstance> {
        val modelInstances: MutableList<ModelInstance> = mutableListOf()

        getLiningSphereLatitudes(modelBuilder).forEach { modelInstances.add(it) }
        getLiningSphereLongitudes(modelBuilder).forEach { modelInstances.add(it) }
        getCoordinateAxes(modelBuilder).forEach { modelInstances.add(it) }

        return modelInstances
    }

    // This function has no purpose. Just for debug
    private fun getPyramidInstance(): ModelInstance {
        val model = modelBuilder.createCone(
            50f,
            120f,
            50f,
            4,
            Material(ColorAttribute.createDiffuse(Color.BLUE)),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        val instance = ModelInstance(model)
        instance.transform.set(Vector3(0f, 0f, 0f), Quaternion(Vector3.Y, 0f))
        return instance
    }
}