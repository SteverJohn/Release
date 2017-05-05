package cn.niit.ydkf.release.release;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;

import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.utils.SPUtils;


public class App extends Application {
    public static Boolean isLoadingOver=false;
    private static String userid;
    private static String username;
    private static String iconurl;
    public static int musicPosition=0;
    public static boolean isPlaying=false;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"PPz4bcEVxMOUtCMkJE4fzh3b-gzGzoHsz","fuwjdmqFqDgTviJtnxxQP7OU");
        AVOSCloud.setDebugLogEnabled(true);
    }
    public static void flushUserInfo(Context context) {
        userid = (String) SPUtils.get(context, "userid", "");
        username = (String) SPUtils.get(context, "username", "");
        iconurl = (String) SPUtils.get(context, "iconurl", Contants.DEFAULT_ICON);
    }
}
