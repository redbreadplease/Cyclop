package com.redbreadplease.cyclop

import com.badlogic.gdx.math.Vector3
import com.redbreadplease.cyclop.libgdx.DoNotKnowHowToCall
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun justTest() {
        val d = DoNotKnowHowToCall()

        println(d.getProjectionVectorOnPlane(Vector3(1f, 1f, 0f), Vector3.X))
    }
}
