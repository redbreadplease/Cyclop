package com.redbreadplease.cyclop.vr

import android.hardware.SensorManager
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.redbreadplease.cyclop.sensors.VRSensorSpaceModel

class VREngine(sensorManager: SensorManager) : VRSensorSpaceModel(sensorManager) {

    override fun render() {
        resetCameraLookingAt()
        camController.update()
        cam.update()
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        modelBatch.begin(cam)
        modelBatch.render(instances, environment)
        modelBatch.end()
    }
}