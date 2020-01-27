package com.redbreadplease.cyclop.ar

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.*
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.Label


class ARStars : ApplicationAdapter() {
    private var cam: PerspectiveCamera? = null
    private val startPos = floatArrayOf(150f, -9f, 0f)
    private var model: Model? = null
    private var instance: ModelInstance? = null
    private var modelBatch: ModelBatch? = null
    private var environment: Environment? = null
    private var degrees: Float = 0f
    private var label: Label? = null

    override fun create() {
        setEnvironment()
        setCamera()
        setModelBatch()
        setModels()
    }

    private fun setCamera() {
        cam = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        cam!!.position[startPos[0], startPos[1]] = startPos[2]
        cam!!.lookAt(0f, 0f, 0f)
        cam!!.near = 1f
        cam!!.far = 300f
        cam!!.update()
    }

    private fun setEnvironment() {
        environment = Environment()
        environment!!.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment!!.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, 10f, 10f, 20f))
    }

    private fun setModelBatch() {
        modelBatch = ModelBatch()
    }

    private fun setModels() {
        val modelBuilder =
            ModelBuilder()
        model = modelBuilder.createCone(
            50f,
            120f,
            50f,
            4,
            Material(ColorAttribute.createDiffuse(Color.RED)),
            (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal.toLong().toInt()).toLong()
        )
        instance = ModelInstance(model)

    }

    override fun render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        degrees += 1f
        instance!!.transform.setToRotation(Vector3.Y, degrees)
        modelBatch!!.begin(cam)
        modelBatch!!.render(instance, environment)
        modelBatch!!.end()
    }

    override fun dispose() {
        model!!.dispose()
        modelBatch!!.dispose()
    }
}
