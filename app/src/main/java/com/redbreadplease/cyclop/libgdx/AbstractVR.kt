package com.redbreadplease.cyclop.libgdx

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController
import com.badlogic.gdx.math.Vector3
import kotlin.concurrent.thread


abstract class AbstractVR : ApplicationAdapter() {
    private val startPos = Vector3(0f, 0f, 0f)

    protected lateinit var doNotKnowHowToCall: DoNotKnowHowToCall

    protected lateinit var cam: PerspectiveCamera
    protected lateinit var camController: CameraInputController
    protected var instances: MutableList<ModelInstance> = mutableListOf()
    protected lateinit var modelBatch: ModelBatch
    protected lateinit var environment: Environment

    override fun create() {
        setDoNotHowToCall()
        setCamera()
        setEnvironment()
        setCameraController()
        setModelBatch()
        setModels()
        startResettingCameraLookDirectionThread()
    }

    private fun setCamera() {
        cam = PerspectiveCamera(
            67f /*120f*/,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        ).apply {
            this.near = 0f
            this.far = 4000f
            this.position[startPos.x, startPos.y] = startPos.z
        }.also {
            it.lookAt(doNotKnowHowToCall.camLookingAt)
            it.update()
        }
    }

    private fun setEnvironment() {
        environment = Environment().also {
            it.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
            it.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, 10f, 10f, 70f))
        }
    }

    private fun setModelBatch() {
        modelBatch = ModelBatch()
    }

    private fun setModels() {
        Models().getModelInstances().forEach { instances.add(it) }
    }

    private fun setCameraController() {
        camController = CameraInputController(cam).also { Gdx.input.inputProcessor = it }
    }

    private fun setDoNotHowToCall() {
        doNotKnowHowToCall = DoNotKnowHowToCall()
    }

    private fun startResettingCameraLookDirectionThread() {
        thread {
            while (true) {
                doNotKnowHowToCall.resetCamLookingInDirection()
            }
        }
    }

    protected fun resetCameraLookingAt() {
        val direction = doNotKnowHowToCall.camLookingAt
        if (!direction.x.isNaN() && !direction.x.isInfinite() && !direction.y.isNaN() && !direction.y.isInfinite() && !direction.z.isNaN() && !direction.z.isInfinite())
            cam.lookAt(direction)
    }

    override fun dispose() {
        modelBatch.dispose()
    }

    fun getCamPosition(): Vector3 = Vector3(cam.position.x, cam.position.y, cam.position.z)
}