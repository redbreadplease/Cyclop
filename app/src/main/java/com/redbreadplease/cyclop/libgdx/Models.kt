package com.redbreadplease.cyclop.libgdx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import kotlin.math.*

class Models {
    private val modelBuilder = ModelBuilder()
    private val horizonCircleRadius: Float = 400f
    private val horizonWidth: Float = 8f

    fun getModelInstances(): MutableList<ModelInstance> {
        val modelInstances: MutableList<ModelInstance> = mutableListOf()

        //modelInstances.add(getPyramidInstance())
        getCircleByLinesInstances().forEach { modelInstances.add(it) }

        return modelInstances
    }

    private fun getPyramidInstance(): ModelInstance {
        val model = modelBuilder.createCone(
            50f,
            120f,
            50f,
            4,
            Material(ColorAttribute.createDiffuse(Color.RED)),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        val instance = ModelInstance(model)
        instance.transform.set(Vector3(0f, 0f, 0f), Quaternion(Vector3.Y, 0f))
        return instance
    }

    private fun getCircleByLinesInstances(): MutableList<ModelInstance> {
        val instances: MutableList<ModelInstance> = mutableListOf()
        val model = modelBuilder.createBox(
            horizonWidth,
            horizonCircleRadius,
            horizonWidth,
            Material(ColorAttribute.createDiffuse(Color.RED)),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        val circlePartsAmount = 12
        for (i in 0..(circlePartsAmount - 1)) {
            var instance = ModelInstance(model)
            val angle = (PI * i.toDouble() / circlePartsAmount.toDouble())
            val tan: Float = tan(angle).toFloat()
            val x1: Float = (horizonCircleRadius / sqrt(tan * tan + 1))
            val x2 = -x1
            val y1 = tan * x1
            val y2 = tan * x2
            instance.transform.set(
                Vector3(0f, x1, y1),
                Quaternion(
                    Vector3.X,
                    (90f + 180f * i.toDouble() / circlePartsAmount.toDouble()).toFloat()
                )
            )
            instances.add(instance.copy())
            instance = ModelInstance(model)
            instance.transform.set(
                Vector3(0f, x2, y2),
                Quaternion(
                    Vector3.X,
                    (90f + 180f * i.toDouble() / circlePartsAmount.toDouble()).toFloat()
                )
            )
            instances.add(instance.copy())
        }
        return instances
    }
}