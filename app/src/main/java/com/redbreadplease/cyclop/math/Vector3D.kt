package com.redbreadplease.cyclop.math

import com.badlogic.gdx.math.Vector3
import kotlin.math.sqrt

class Vector3D(
    private var x: Float,
    private var y: Float,
    private var z: Float
) {
    fun getX(): Float = x

    fun getY(): Float = y

    fun getZ(): Float = z

    fun getLength(): Float = sqrt(x * x + y * y + z * z)

    fun setLike(like: Vector3D?) = set(like!!.getX(), like.getY(), like.getZ())

    fun set(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    fun smooth(x: Float, y: Float, z: Float, smoothCoefficient: Int) {
        this.x = (this.x * (smoothCoefficient - 1) + x) / smoothCoefficient
        this.y = (this.y * (smoothCoefficient - 1) + y) / smoothCoefficient
        this.z = (this.z * (smoothCoefficient - 1) + z) / smoothCoefficient
    }

    companion object {
        fun getNormalizedVector(vector: Vector3D): Vector3D {
            val itsLength: Float = vector.getLength()
            return Vector3D(
                vector.getX() / itsLength,
                vector.getY() / itsLength,
                vector.getZ() / itsLength
            )
        }
    }

    fun parseToVectorGDX(): Vector3 = Vector3(x, y, z)
}