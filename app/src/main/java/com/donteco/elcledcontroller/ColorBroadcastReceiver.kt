package com.donteco.elcledcontroller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ColorBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras?.getString("COLOR")?.let {
            LedController.setClosestColor(ColorUtils.parseJsonColor(it))
        }
    }
}