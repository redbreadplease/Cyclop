package com.redbreadplease.cyclop.sensors

import com.redbreadplease.cyclop.math.Vector3D

class Orientation {
    private var startVectorValuesAmount: Int = 0
    private val maxStartVectorsValuesAmount = 20

    private var smoothCoefficient: Int = 100

    private var startOrientationVector: Vector3D? = null
    private var curOrientationVector: Vector3D? = null

    init {
        startOrientationVector =
            Vector3D(0f, 0f, 0f)
        curOrientationVector =
            Vector3D(0f, 0f, 0f)
    }

    fun getOrientationVector(): Vector3D? {
        return if (startOrientationVector == null || curOrientationVector == null) {
            println("!!!!!!!!!!Null vector was given!")
            null
        } else
            Vector3D.getNormalizedVector(
                Vector3D(
                    startOrientationVector!!.getX() - curOrientationVector!!.getX(),
                    startOrientationVector!!.getY() - curOrientationVector!!.getY(),
                    startOrientationVector!!.getZ() - curOrientationVector!!.getZ()
                )
            )
    }

    fun update(x: Float, y: Float, z: Float) {
        if (updateStartOrientationVector(x, y, z))
            return
        else
            updateCurVector(x, y, z)
    }

    private fun updateCurVector(x: Float, y: Float, z: Float) {
        curOrientationVector!!.smooth(x, y, z, smoothCoefficient)
    }

    private fun updateStartOrientationVector(x: Float, y: Float, z: Float): Boolean {
        return when (startVectorValuesAmount) {
            0 -> {
                startOrientationVector =
                    Vector3D(x, y, z)
                curOrientationVector =
                    Vector3D(x, y, z)
                startVectorValuesAmount += 1
                true
            }
            maxStartVectorsValuesAmount -> {
                false
            }
            else -> {
                startOrientationVector!!.smooth(x, y, z, startVectorValuesAmount)
                curOrientationVector!!.setLike(startOrientationVector)
                startVectorValuesAmount += 1
                true
            }
        }
    }

    private fun setSmooth(smoothCoefficient: Int) {
        this.smoothCoefficient = smoothCoefficient
    }
}