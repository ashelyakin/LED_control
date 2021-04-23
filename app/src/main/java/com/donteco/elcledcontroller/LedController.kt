package com.donteco.elcledcontroller

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.Socket
import kotlin.collections.ArrayList
import kotlin.experimental.xor

object LedController {

    enum class LedColors(val command: String, val color: Int) {
        RED("0x04", Color.rgb(255, 0, 0)),
        GREEN("0x05", Color.rgb(0, 255, 0)),
        BLUE("0x06", Color.rgb(0, 0, 255)),
        WHITE("0x07", Color.rgb(255, 255, 255)),
        GRAY("0x07", Color.rgb(140, 140, 140)),
        ORANGE("0x08", Color.rgb(255, 180, 0)),
        YELLOW("0x0c", Color.rgb(255, 255, 0)),
        YELLOW_GREEN("0x10", Color.rgb(200, 255, 0)),
        GREEN_YELLOW("0x14", Color.rgb(170, 255, 0)),
        SUPER_LIGHT_BLUE("0x09", Color.rgb(0, 255, 255)),
        SUPER_LIGHT_BLUE2("0x0d", Color.rgb(0, 215, 255)),
        SUPER_LIGHT_BLUE3("0x11", Color.rgb(0, 193, 255)),
        SUPER_LIGHT_BLUE4("0x15", Color.rgb(0, 180, 255)),
        PURPLE1("0x0a", Color.rgb(58, 0, 255)),
        PURPLE2("0x0e", Color.rgb(83, 0, 255)),
        PURPLE3("0x12", Color.rgb(120, 0, 255)),
        PURPLE4("0x16", Color.rgb(120, 85, 255)),

        /// additional for best parse
        BLACK("0x06", Color.rgb(0, 0, 0)), //if need black -> use blue led
        LIGHT_PURPLE("0x0e", Color.rgb(220, 136, 255)), // use purple
        DARK_PURPLE("0x0e", Color.rgb(92, 0, 97)),
        PINK("0x04", Color.rgb(255, 165, 165)), //if pink -> use red led
        LIGHT_GREEN("0x05", Color.rgb(155, 255, 136)), // use green
        DARK_GREEN("0x05", Color.rgb(3, 93, 3)), // use green
        LIGHT_BLUE("0x06", Color.rgb(136, 159, 255)), //use blue
        LIGHT_YELLOW("0x0c", Color.rgb(255, 234, 136)), // use yellow
        GRAY_YELLOW_GREEN("0x10", Color.rgb(84, 93, 3)), // use yellow_green

    }

    fun setClosestColor(color: Int) {
        val closestColor = (ColorUtils.findClosestColor(color).color)
        sendCommandOnSocket(closestColor.red, closestColor.green, closestColor.blue)
    }

    private fun sendCommandOnSocket(red: Int, green: Int, blue: Int) {
        GlobalScope.launch {
            val client = Socket("localhost", 5000)
            val listByte = ArrayList<Byte>()
            listByte.addAll(listOf(0x09.toByte(), 0x01.toByte(), 0x00.toByte(), 0xF3.toByte(), 0x01.toByte()))
            listByte.add(red.toByte())
            listByte.add(green.toByte())
            listByte.add(blue.toByte())
            listByte.add(getXORCheckSum(listByte))
            client.outputStream.write(listByte.toByteArray())
            client.close()
        }
    }

    private fun getXORCheckSum(bytes: ArrayList<Byte>): Byte{
        var res = bytes[0]
        for (byte in bytes.takeLast(bytes.size - 1))
            res = res xor byte
        return res
    }

}