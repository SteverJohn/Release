package cn.niit.ydkf.release.okhttp;

import android.util.Log;

/**
 * Created by Black_Jack on 2017/4/1.
 */

public class L {
    private static final String TAG="Imooc_okhttp";
    private static boolean debug=true;
    public static void e(String msg){
        if (debug){
            Log.e(TAG,msg);
        }
    }
}
