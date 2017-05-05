package cn.niit.ydkf.release.release.utils;

import android.util.Log;

/**
 * Created by Black_Jack on 2017/4/3.
 */

public class LogUtils {
    public static boolean isDebug = true;

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }


    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

}
