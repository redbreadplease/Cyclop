package com.redbreadplease.cyclop.libgdx

import com.badlogic.gdx.math.Vector3
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class DoNotKnowHowToCall {
    var gravityDirection: Vector3 = Vector3(0f, 0f, 0f)
    var magneticFlowDirection: Vector3 = Vector3(0f, 0f, 0f)

    private val smoothMagneticFlowValuesCoefficient = 10
    private val smoothAccelerometerValuesCoefficient = 10
    private val smoothCameraDirectionChangingCoefficient = 1

    companion object {
        const val camLookingVectorLength: Float = 10f
    }

    var camLookingAt: Vector3 = Vector3(0f, 0f, 0f)

    fun getHorizontalAngle() =
        getHorizontalAngle(
            getProjectionVectorOnPlane(gravityDirection, magneticFlowDirection),
            getProjectionVectorOnPlane(gravityDirection, Vector3.Z)
        )

    fun resetCamLookingInDirection() {
        val zProjectionOnGPerpendicularPlane: Vector3 =
            getProjectionVectorOnPlane(gravityDirection, Vector3.Z)
        val angleZProjectionAndMagneticFlow = getHorizontalAngle(
            getProjectionVectorOnPlane(gravityDirection, magneticFlowDirection),
            zProjectionOnGPerpendicularPlane
        )

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

    private fun getHorizontalAngle(
        expectedMagneticFlowDirection: Vector3,
        zProjectionOnGPerpendicularPlane: Vector3
    ): Float =
        getAngle(
            expectedMagneticFlowDirection,
            zProjectionOnGPerpendicularPlane
        ).let {
            if (getAngle(
                    gravityDirection,
                    expectedMagneticFlowDirection.crs(zProjectionOnGPerpendicularPlane)
                ) < PI / 2
            )
                (PI * 2).toFloat() - it
            else
                it
        }

    fun getProjectionVectorOnPlane(
        orthogonalPlaneVector: Vector3,
        vectorToProject: Vector3
    ): Vector3 = orthogonalPlaneVector.let { orthogonal ->
        (orthogonal.x * orthogonal.x + orthogonal.y * orthogonal.y + orthogonal.z * orthogonal.z).let { sumSq ->
            vectorToProject.let { vp ->
                (-(orthogonal.x * vp.x + orthogonal.y * vp.y + orthogonal.z * vp.z) / sumSq).let { k ->
                    Vector3(
                        vp.x + orthogonal.x * k,
                        vp.y + orthogonal.y * k,
                        vp.z + orthogonal.z * k
                    )
                }
            }
        }
    }

    fun getAngle(v1: Vector3, v2: Vector3) = acos(
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