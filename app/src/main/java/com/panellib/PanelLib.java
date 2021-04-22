package com.panellib;

public class PanelLib {

    public enum LedColor {
        LED_RED,
        LED_GREEN,
        LED_BLUE
    }

    public native boolean SetBacklightColor(int i, int i2, int i3);

    public native boolean SetLedBrightness(LedColor ledColor, int i);
}
