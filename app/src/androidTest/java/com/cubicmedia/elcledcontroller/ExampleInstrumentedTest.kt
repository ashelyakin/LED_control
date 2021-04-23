package com.cubicmedia.elcledcontroller

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.donteco.elcledcontroller.ColorUtils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.cubicmedia.newElcLedController", appContext.packageName)
    }

    @Test
    fun checkColor(){
        val color = ColorUtils.parseJsonColor("rgba(219,140,140,0)")
        val command = ColorUtils.findClosestColor(color)
        Log.d("COLOR CHECK", command.toString())
    }
}