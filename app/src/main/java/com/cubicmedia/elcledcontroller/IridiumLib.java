package com.cubicmedia.elcledcontroller;

import android.util.Log;

import com.panellib.PanelLib;

import java.io.File;

public class IridiumLib {
    private static final String TAG = "iRidiumLib";

    public native void light(float f);

    static {
        try {
            System.loadLibrary("iRidium");
            Log.i(TAG, "library iRidium.so loaded successfully");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Can't load library iRidium.so");
            e.printStackTrace();
        }
        try {
            File file = new File("/libpanel.so");
            if (file.exists()) {
                System.load(file.getAbsolutePath());
                Log.i(TAG, "library libpanel.so loaded successfully");
            }
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
        }
    }

    public void setLedBacklight(int i, int i2, int i3) {
        boolean SetBacklightColor = new PanelLib().SetBacklightColor(i, i2, i3);
        Log.d(TAG, "result = " + SetBacklightColor);
    }


}
