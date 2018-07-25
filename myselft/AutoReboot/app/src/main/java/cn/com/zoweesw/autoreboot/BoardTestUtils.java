package cn.com.zoweesw.autoreboot;


import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


public class BoardTestUtils {

    protected static final String TAG = "Reboot";
    public static final String LEDS_PATH = "/sys/class/leds/";
    public static final String LEDS_BRIGHTNESS = "/brightness";


    public static boolean setLedStatus(boolean redOn, boolean greenOn, boolean blueOn) {
        Log.d(TAG, "BoardTestUtils setLedStatus (" + redOn + ", " + greenOn + ", " + blueOn + ")");
        String ledpath = LEDS_PATH + "redled-sys" + LEDS_BRIGHTNESS;
        if (!writeFile(ledpath, boolToString(redOn))) {
            return false;
        }
        ledpath = LEDS_PATH + "greenled-sys" + LEDS_BRIGHTNESS;
        if (!writeFile(ledpath, boolToString(greenOn))) {
            return false;
        }
        ledpath = LEDS_PATH + "blueled-sys" + LEDS_BRIGHTNESS;
        if (!writeFile(ledpath, boolToString(blueOn))) {
            return false;
        }
        return true;
    }

    private static boolean writeFile(String file, String value) {
        Log.d(TAG, "writeFile BoardTestUtils");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(value);
            fw.close();
            return true;
        } catch (Exception e) {
            Log.w(TAG, e.fillInStackTrace().toString());
            return false;
        }
    }

    private static String boolToString(boolean valueBool) {
        Log.d(TAG, "writeFile BoardTestUtils");
        String valueStr = "0";
        if (valueBool) {
            valueStr = "1";
        }
        return valueStr;
    }
}
