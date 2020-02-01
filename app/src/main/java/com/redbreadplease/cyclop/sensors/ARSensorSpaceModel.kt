package com.redbreadplease.cyclop.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.redbreadplease.cyclop.libgdx.AbstractVR
import com.redbreadplease.cyclop.math.Vector3D


abstract class ARSensorSpaceModel(private val sensorManager: SensorManager) : AbstractVR() {
    private var orientation: Orientation? = null

    private var sensorAccelerometer: Sensor? = null

    private val workingSensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {
            orientation!!.update(event.values[0], event.values[1], event.values[2])
        }
    }

    override fun create() {
        super.create()
        createOrientationModel()
        registerListeners()
    }

    private fun createOrientationModel() {
        orientation = Orientation()
    }

    private fun registerListeners() {
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
            workingSensorEventListener,
            sensorAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun getNormalizedOrientationVector(): Vector3D? = orientation!!.getOrientationVector()
}