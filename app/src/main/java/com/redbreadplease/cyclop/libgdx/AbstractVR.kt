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


abstract class AbstractVR : ApplicationAdapter() {
    protected var cam: PerspectiveCamera? = null
    protected var camController: CameraInputController? = null
    private val startPos = floatArrayOf(0f, 0f, 0f)
    private val lookAt = floatArrayOf(1f, 0f, 0f)
    protected var instances: MutableList<ModelInstance> = mutableListOf()
    protected var modelBatch: ModelBatch? = null
    protected var environment: Environment? = null

    override fun create() {
        setEnvironment()
        setCamera()
        setCameraController()
        setModelBatch()
        setModels()
    }

    private fun setCamera() {
        cam = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        cam!!.position[startPos[0], startPos[1]] = startPos[2]
        cam!!.lookAt(lookAt[0], lookAt[0], lookAt[0])
        cam!!.near = 0f
        cam!!.far = 2000f
        cam!!.update()
    }

    private fun setEnvironment() {
        environment = Environment()
        environment!!.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment!!.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, 10f, 10f, 70f))
    }

    private fun setModelBatch() {
        modelBatch = ModelBatch()
    }

    private fun setModels() {
        Models().getModelInstances().forEach {
            instances.add(it)
        }
    }

    private fun setCameraController() {
        camController = CameraInputController(cam)
        Gdx.input.inputProcessor = camController

    }

    override fun dispose() {
        modelBatch!!.dispose()
    }

    fun getCamPosition(): Vector3 = Vector3(cam!!.position.x, cam!!.position.y, cam!!.position.z)
}