package com.redbreadplease.cyclop.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.badlogic.gdx.math.Vector3
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.libgdx.DoNotKnowHowToCall
import kotlinx.android.synthetic.main.activity_debug.*
import java.lang.Math.toDegrees
import kotlin.concurrent.thread

class DebugActivity : AppCompatActivity() {
    private lateinit var gX: TextView
    private lateinit var gY: TextView
    private lateinit var gZ: TextView

    private lateinit var mX: TextView
    private lateinit var mY: TextView
    private lateinit var mZ: TextView

    private lateinit var doNotKnowHowToCall: DoNotKnowHowToCall

    private lateinit var angle: TextView

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        thread { this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        thread {
            doNotKnowHowToCall = DoNotKnowHowToCall()

            angle = findViewById(R.id.debug_info_angle)

            gX = findViewById(R.id.gravity_x)
            gY = findViewById(R.id.gravity_y)
            gZ = findViewById(R.id.gravity_z)

            mX = findViewById(R.id.magnetic_x)
            mY = findViewById(R.id.magnetic_y)
            mZ = findViewById(R.id.magnetic_z)

            (getSystemService(Context.SENSOR_SERVICE) as SensorManager).also { sensorManager ->
                sensorManager.registerListener(
                    object : SensorEventListener {
                        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
                        override fun onSensorChanged(event: SensorEvent) {
                            event.values.also {
                                gX.text = "Gravity x: \n" + it[0].toString()
                                gY.text = "Gravity y: \n" + it[1].toString()
                                gZ.text = "Gravity z: \n" + it[2].toString()
                                doNotKnowHowToCall.updateGravityFlow(it[0], it[1], it[2])
                            }
                        }
                    },
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL
                )
                sensorManager.registerListener(
                    object : SensorEventListener {
                        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
                        override fun onSensorChanged(event: SensorEvent) {
                            event.values.also {
                                mX.text = "Magnetic field x: \n" + it[0].toString()
                                mY.text = "Magnetic field y: \n" + it[1].toString()
                                mZ.text = "Magnetic field z: \n" + it[2].toString()
                                doNotKnowHowToCall.updateMagneticFlow(it[0], it[1], it[2])
                                doNotKnowHowToCall.getProjectionVectorOnPlane(
                                    doNotKnowHowToCall.gravityDirection,
                                    Vector3.Z
                                ).also { zPlaneProjection ->
                                    doNotKnowHowToCall.getProjectionVectorOnPlane(
                                        doNotKnowHowToCall.gravityDirection,
                                        doNotKnowHowToCall.magneticFlowDirection
                                    ).also { magneticFlowPlaneProjection ->
                                        var horizontalAngle = toDegrees(
                                            doNotKnowHowToCall.getAngle(
                                                zPlaneProjection,
                                                magneticFlowPlaneProjection
                                            ).toDouble()
                                        )

                                        val crsVector = magneticFlowPlaneProjection.crs(
                                            zPlaneProjection
                                        )

                                        var crsAngle = doNotKnowHowToCall.getAngle(
                                            crsVector,
                                            doNotKnowHowToCall.gravityDirection
                                        ).toDouble()

                                        debug_info_angle.text = doNotKnowHowToCall.getAngle(
                                            magneticFlowPlaneProjection,
                                            Vector3.Z
                                        ).toString()

                                        //debug_info_angle.text = crsVector.toString()
                                        //.replace("(", "").replace(")", "").replace(",", "\n")
                                        /*
                                        if (crsAngle > PI / 2)
                                            debug_info_angle.text =
                                                angle.toString() + "\n-1\n" + crsAngle.toString()
                                        else
                                            debug_info_angle.text =
                                                angle.toString() + "\n0\n" + crsAngle.toString()
                                         */
                                    }
                                }
                            }
                        }
                    },
                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
        }
    }

    fun toAppMenu(view: View) {
        startActivity(Intent(this, AppMenuActivity::class.java))
    }
}