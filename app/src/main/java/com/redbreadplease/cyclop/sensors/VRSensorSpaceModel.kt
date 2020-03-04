package com.redbreadplease.cyclop.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.redbreadplease.cyclop.libgdx.AbstractVR


abstract class VRSensorSpaceModel(private val sensorManager: SensorManager) : AbstractVR() {
    private lateinit var sensorAccelerometer: Sensor
    private lateinit var sensorMagneticField: Sensor

    private val accelerometerSensorEventListener: SensorEventListener =
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
            override fun onSensorChanged(event: SensorEvent) {
                event.values.also { doNotKnowHowToCall.updateGravityFlow(it[0], it[1], it[2]) }
            }
        }

    private val magneticFieldSensorEventListener: SensorEventListener =
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
            override fun onSensorChanged(event: SensorEvent) {
                event.values.also { doNotKnowHowToCall.updateMagneticFlow(it[0], it[1], it[2]) }
            }
        }

    override fun create() {
        super.create()
        registerListeners()
    }

    private fun registerListeners() {
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also {
            sensorManager.registerListener(
                accelerometerSensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD).also {
            sensorManager.registerListener(
                magneticFieldSensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }
}