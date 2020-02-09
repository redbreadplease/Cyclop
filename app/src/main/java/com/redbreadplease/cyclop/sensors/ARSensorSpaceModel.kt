package com.redbreadplease.cyclop.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.redbreadplease.cyclop.libgdx.AbstractVR


abstract class ARSensorSpaceModel(private val sensorManager: SensorManager) : AbstractVR() {
    private var sensorAccelerometer: Sensor? = null

    private val workingSensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        override fun onSensorChanged(event: SensorEvent) {}
    }

    override fun create() {
        super.create()
        registerListeners()
    }

    private fun registerListeners() {
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
            workingSensorEventListener,
            sensorAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}