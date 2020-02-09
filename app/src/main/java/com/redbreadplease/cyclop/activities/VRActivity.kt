package com.redbreadplease.cyclop.activities

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.redbreadplease.cyclop.vr.VREngine


class VRActivity : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*thread {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }*/
        initialize(
            VREngine(getSystemService(Context.SENSOR_SERVICE) as SensorManager),
            AndroidApplicationConfiguration()
        )
    }
}
