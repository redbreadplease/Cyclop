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
    private val liningSphereRadius: Float = 1400f // default: 400f
    private val horizonWidth: Float = 8f
    private val circlePartsAmount = 12

    fun getModelInstances(): MutableList<ModelInstance> {
        val modelInstances: MutableList<ModelInstance> = mutableListOf()

        //modelInstances.add(getPyramidInstance())
        getCircleByLinesInstances().forEach { modelInstances.add(it) }

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

    private fun getCircleByLinesInstances(): MutableList<ModelInstance> {
        val instances: MutableList<ModelInstance> = mutableListOf()
        val absMaxHeightStepValue = 10
        for (heightStep in -absMaxHeightStepValue..absMaxHeightStepValue) {
            val z = heightStep / (absMaxHeightStepValue + 0.2f) * liningSphereRadius
            val sizeCoefficient =
                (sqrt((liningSphereRadius + abs(z)) * (liningSphereRadius - abs(z)).toDouble())).toFloat() / liningSphereRadius
            val model = modelBuilder.createBox(
                horizonWidth,
                liningSphereRadius / 3.7f * sizeCoefficient,
                horizonWidth,
                Material(ColorAttribute.createDiffuse(Color.RED)),
                (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
            )
            for (rotateStep in 0..(circlePartsAmount - 1)) {
                var instance = ModelInstance(model)
                val tan: Float =
                    tan((PI * rotateStep.toDouble() / circlePartsAmount.toDouble())).toFloat()
                val x1: Float = (liningSphereRadius / sqrt(tan * tan + 1))
                val x2 = -x1
                val y1 = tan * x1
                val y2 = tan * x2
                val firstFigurePosition: Vector3? =
                    Vector3(
                        z, x1 * sizeCoefficient,
                        y1 * sizeCoefficient
                    ) // following with fun
                val secondFigurePosition: Vector3? =
                    Vector3(
                        z, x2 * sizeCoefficient,
                        y2 * sizeCoefficient
                    ) // following with fun
                val figureQuaternion = Quaternion(
                    Vector3.X,
                    (90f + 180f * rotateStep.toDouble() / circlePartsAmount.toDouble()).toFloat()
                ) // this too
                instance.transform.set(
                    firstFigurePosition,
                    figureQuaternion
                )
                instances.add(instance.copy())
                instance = ModelInstance(model)
                instance.transform.set(
                    secondFigurePosition,
                    figureQuaternion
                )
                instances.add(instance.copy())
            }
        }
        return instances
    }
}