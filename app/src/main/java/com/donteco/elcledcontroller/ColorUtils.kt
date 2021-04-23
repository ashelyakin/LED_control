package com.donteco.elcledcontroller

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import java.lang.Exception
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

object ColorUtils {

    @Deprecated("because")
    private fun findDest(color1: Int, color2: Int): Double {
        return (((color1.red - color2.red).toFloat() * 0.299).pow(2)
                + ((color1.green - color2.green).toFloat() * 0.587).pow(2)
                + ((color1.blue - color2.blue).toFloat() * 0.114).pow(2))

    }

    private fun findDest2(color1: Int, color2: Int): Float {
        return sqrt(
            ((color1.red - color2.red).toFloat()).pow(2)
                    + ((color1.green - color2.green).toFloat()).pow(2)
                    + ((color1.blue - color2.blue).toFloat()).pow(2)
        )

    }

    fun findClosestColor(color: Int): LedController.LedColors {

        val dests = LedController.LedColors.values().map {
            findDest2(color, it.color)
        }

        val index = dests.indexOf(dests.minOrNull())

        return LedController.LedColors.values()[index]
    }

    fun parseJsonColor(color: String): Int {
        return try {
            val comps =
                color
                    .replace("rgba(", "")
                    .replace(")", "")
                    .split(",")
            //        val a = ((comps[3].toDouble() * 255).toInt() and 0xff) shl 24
            val r = Integer.parseInt(comps[0])
            val g = Integer.parseInt(comps[1])
            val b = Integer.parseInt(comps[2])
            Color.rgb(r, g, b)
        } catch (e: Exception) {
            Color.WHITE
        }
    }

}