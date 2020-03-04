package com.redbreadplease.cyclop.libgdx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.redbreadplease.cyclop.libgdx.Models.Companion.liningSphereRadius
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.tan

private const val horizonWidth: Float = 8f
private const val circlePartsAmount = 12

fun getCoordinateAxes(modelBuilder: ModelBuilder): MutableList<ModelInstance> {
    val instances = mutableListOf<ModelInstance>()
    val axesLength: Float = liningSphereRadius * 3
    for (axesIndex in 0..2) {
        val model = modelBuilder.createBox(
            20f,
            axesLength,
            20f,
            Material(
                ColorAttribute.createDiffuse(
                    when (axesIndex) {
                        0 -> Color.RED
                        1 -> Color.GREEN
                        2 -> Color.BLUE
                        else -> Color.DARK_GRAY
                    }
                )
            ),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        val instance = ModelInstance(model)
        val figurePosition: Vector3? = Vector3(0f, 0f, 0f)
        val figureQuaternion = Quaternion(
            when (axesIndex) {
                0 -> Vector3.Z
                1 -> Vector3.Y
                2 -> Vector3.X
                else -> null
            }, when (axesIndex) {
                0 -> 90f
                1 -> 0f
                2 -> 90f
                else -> 0f
            }
        )
        instance.transform.set(
            figurePosition,
            figureQuaternion
        )
        instances.add(instance)
        val sphereRadius = 200f
        val sphereInstance = ModelInstance(
            modelBuilder.createSphere(
                sphereRadius,
                sphereRadius,
                sphereRadius,
                10,
                10,
                Material(
                    ColorAttribute.createDiffuse(
                        when (axesIndex) {
                            0 -> Color.RED
                            1 -> Color.GREEN
                            2 -> Color.BLUE
                            else -> Color.DARK_GRAY
                        }
                    )
                ),
                (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
            )
        )
        val spherePosition: Vector3? = Vector3(
            when (axesIndex) {
                0 -> axesLength / 2
                else -> 0f
            },
            when (axesIndex) {
                1 -> axesLength / 2
                else -> 0f
            },
            when (axesIndex) {
                2 -> axesLength / 2
                else -> 0f
            }
        )
        val sphereQuaternion = Quaternion(Vector3.X, 0f)
        sphereInstance.transform.set(
            spherePosition,
            sphereQuaternion
        )
        instances.add(sphereInstance)
    }
    return instances
}

fun getLiningSphereLatitudes(modelBuilder: ModelBuilder): MutableList<ModelInstance> {
    val instances: MutableList<ModelInstance> = mutableListOf()
    val absMaxHeightStepValue = 10
    for (heightStep in -absMaxHeightStepValue..absMaxHeightStepValue) {
        val z = heightStep / (absMaxHeightStepValue + 0.2f) * liningSphereRadius
        val sizeCoefficient =
            (sqrt((liningSphereRadius + abs(z)) * (liningSphereRadius - abs(z)).toDouble())).toFloat() / liningSphereRadius
        val model = modelBuilder.createBox(
            liningSphereRadius / 3.7f * sizeCoefficient,
            horizonWidth,
            horizonWidth,
            Material(ColorAttribute.createDiffuse(Color.RED)),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        for (rotateStep in 0 until circlePartsAmount) {
            var instance = ModelInstance(model)
            val tan: Float =
                tan((PI * rotateStep.toDouble() / circlePartsAmount.toDouble())).toFloat()
            val x1: Float = (liningSphereRadius / sqrt(tan * tan + 1))
            val x2 = -x1
            val y1 = tan * x1
            val y2 = tan * x2
            val firstFigurePosition: Vector3? =
                Vector3(x1 * sizeCoefficient, z, y1 * sizeCoefficient) // following with fun
            val secondFigurePosition: Vector3? =
                Vector3(x2 * sizeCoefficient, z, y2 * sizeCoefficient) // following with fun
            val figureQuaternion = Quaternion(
                Vector3.Y,
                (90f - 180f * rotateStep.toDouble() / circlePartsAmount.toDouble()).toFloat()
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

fun getLiningSphereLongitudes(modelBuilder: ModelBuilder): MutableList<ModelInstance> {
    val instances: MutableList<ModelInstance> = mutableListOf()
    val absMaxHeightStepValue = 10
    for (heightStep in -absMaxHeightStepValue..absMaxHeightStepValue) {
        val z = heightStep / (absMaxHeightStepValue + 0.2f) * liningSphereRadius
        val sizeCoefficient =
            (sqrt((liningSphereRadius + abs(z)) * (liningSphereRadius - abs(z)).toDouble())).toFloat() / liningSphereRadius
        val model = modelBuilder.createBox(
            horizonWidth * 3,
            horizonWidth * 3, //liningSphereRadius / 3.7f * sizeCoefficient
            horizonWidth * 3,
            Material(ColorAttribute.createDiffuse(Color.BLUE)),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        for (rotateStep in 0 until circlePartsAmount) {
            var instance = ModelInstance(model)
            val tan: Float =
                tan((PI * rotateStep.toDouble() / circlePartsAmount.toDouble())).toFloat()
            val x1: Float = (liningSphereRadius / sqrt(tan * tan + 1))
            val x2 = -x1
            val y1 = tan * x1
            val y2 = tan * x2
            val firstFigurePosition: Vector3? =
                Vector3(y1 * sizeCoefficient, z, x1 * sizeCoefficient) // following with fun
            val secondFigurePosition: Vector3? =
                Vector3(y2 * sizeCoefficient, z, x2 * sizeCoefficient) // following with fun
            // use to look at
            instance.transform.set(
                firstFigurePosition,
                Quaternion(
                    Vector3.Y,
                    (90f - 180f * rotateStep.toDouble() / circlePartsAmount.toDouble()).toFloat()
                )
            )
            instances.add(instance.copy())
            instance = ModelInstance(model)
            instance.transform.set(
                secondFigurePosition,
                Quaternion(
                    Vector3.Y,
                    (90f - 180f * rotateStep.toDouble() / circlePartsAmount.toDouble()).toFloat()
                )
            )
            instances.add(instance.copy())
        }
    }
    return instances
}