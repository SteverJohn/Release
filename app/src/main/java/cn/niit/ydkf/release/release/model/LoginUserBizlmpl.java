package cn.niit.ydkf.release.release.model;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import cn.niit.ydkf.release.release.model.entities.User;
import cn.niit.ydkf.release.release.model.interfaces.LoginUserBiz;
import cn.niit.ydkf.release.release.model.interfaces.OnLoginListener;


public class LoginUserBizlmpl implements LoginUserBiz {
    private OnLoginListener onLoginListener;
    private Context context;

    public LoginUserBizlmpl(Context context, OnLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
        this.context = context;
    }
    @Override
    public void login(User user) {
        if (user.getEmail().equals("") || user.getPassword().equals("")) {
            onLoginListener.login(3);
        } else {
            AVUser.logInInBackground(user.getEmail(), user.getPassword(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        onLoginListener.login(0);
                        return;
                    }
                    if (e.getCode() == 211) {
                        onLoginListener.login(1);
                        return;
                    }
                    if (e.getCode() == 210) {
                        onLoginListener.login(2);
                        return;
                    }
                    return;
                }
            });

        }
    }


}