package com.redbreadplease.cyclop.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.SensorManager
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.redbreadplease.cyclop.vr.VREngine
import kotlin.concurrent.thread


class VRActivity : AndroidApplication() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*thread {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }*/
        thread { this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        initialize(
            VREngine(getSystemService(Context.SENSOR_SERVICE) as SensorManager),
            AndroidApplicationConfiguration()
        )
    }
}
