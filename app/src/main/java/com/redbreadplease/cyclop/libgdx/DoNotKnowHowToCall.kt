package com.redbreadplease.cyclop.libgdx

import com.badlogic.gdx.math.Vector3
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class DoNotKnowHowToCall {
    private var gravityDirection: Vector3 = Vector3(0f, 0f, 0f)
    private var magneticFlowDirection: Vector3 = Vector3(0f, 0f, 0f)

    private val smoothMagneticFlowValuesCoefficient = 70
    private val smoothAccelerometerValuesCoefficient = 170
    private val smoothCameraDirectionChangingCoefficient = 1

    companion object {
        const val camLookingVectorLength: Float = 10f
    }

    var camLookingAt: Vector3 = Vector3(0f, 0f, 0f)

    fun resetCamLookingInDirection() {
        lateinit var expectedMagneticFlowDirection: Vector3
        lateinit var zProjectionOnGPerpendicularPlane: Vector3
        gravityDirection.also { gd ->
            (gd.x * gd.x + gd.y * gd.y + gd.z * gd.z).also { gdSumSq ->
                magneticFlowDirection.also { mf ->
                    (-(gd.x * mf.x + gd.y * mf.y + gd.z * mf.z) / gdSumSq).also { k ->
                        expectedMagneticFlowDirection =
                            Vector3(mf.x + gd.x * k, mf.y + gd.y * k, mf.z + gd.z * k)
                    }
                }
                Vector3.Z.also { vz ->
                    (-(gd.x * vz.x + gd.y * vz.y + gd.z * vz.z) / gdSumSq).also { k ->
                        zProjectionOnGPerpendicularPlane =
                            Vector3(vz.x + gd.x * k, vz.y + gd.y * k, vz.z + gd.z * k)
                    }
                }
            }
        }
        var angleZProjectionAndMagneticFlow = getAngle(
            expectedMagneticFlowDirection,
            zProjectionOnGPerpendicularPlane
        )

        if (getAngle(
                gravityDirection,
                expectedMagneticFlowDirection.crs(zProjectionOnGPerpendicularPlane)
            ) < PI / 2
        )
            angleZProjectionAndMagneticFlow = (PI * 2).toFloat() - angleZProjectionAndMagneticFlow


        angleZProjectionAndMagneticFlow.also { fi ->
            getAngle(Vector3.Y, zProjectionOnGPerpendicularPlane).also { theta ->
                camLookingVectorLength.also { r ->
                    updateCameraLookingDirection(
                        r * sin(theta) * cos(fi),
                        0f, //r * cos(theta),
                        r * sin(theta) * sin(fi)
                    )
                }
            }
        }
    }

    private fun getAngle(v1: Vector3, v2: Vector3) = acos(
        Vector3.dot(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z) / (v1.len() * v2.len())
    )

    fun updateGravityFlow(x: Float, y: Float, z: Float) {
        updateFlow(x, y, z, smoothAccelerometerValuesCoefficient, gravityDirection)
    }

    fun updateMagneticFlow(x: Float, y: Float, z: Float) {
        updateFlow(x, y, z, smoothMagneticFlowValuesCoefficient, magneticFlowDirection)
    }

    private fun updateCameraLookingDirection(x: Float, y: Float, z: Float) {
        updateFlow(x, y, z, smoothCameraDirectionChangingCoefficient, camLookingAt)
    }

    private fun updateFlow(x: Float, y: Float, z: Float, smooth: Int, directionToUpdate: Vector3) {
        directionToUpdate.also {
            if (!it.x.isNaN() && !it.x.isInfinite() && !x.isNaN() && !x.isInfinite())
                it.x = ((smooth - 1) * it.x + x) / smooth
            if (!it.y.isNaN() && !it.y.isInfinite() && !y.isNaN() && !y.isInfinite())
                it.y = ((smooth - 1) * it.y + y) / smooth
            if (!it.y.isNaN() && !it.y.isInfinite() && !z.isNaN() && !z.isInfinite())
                it.z = ((smooth - 1) * it.z + z) / smooth
        }
    }
}