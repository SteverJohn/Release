package cn.niit.ydkf.release.release.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import cn.niit.ydkf.release.release.model.entities.User;
import cn.niit.ydkf.release.release.model.interfaces.OnRegisterListener;
import cn.niit.ydkf.release.release.model.interfaces.RegisterUserBiz;
import cn.niit.ydkf.release.release.utils.Validation;


public class RegisterUserBizlmpl implements RegisterUserBiz {
    private OnRegisterListener onRegisterListener;
    private Validation validation = new Validation();

    public RegisterUserBizlmpl(OnRegisterListener onRegisterListener) {
        this.onRegisterListener = onRegisterListener;
    }


    @Override
    public void register(User userBean) {
        if (userBean.getEmail().equals("") || userBean.getPassword().equals("")
                || userBean.getRepeatPassword().equals("")) {
            onRegisterListener.register(0);
        } else {
            if (!validation.isEMail(userBean.getEmail())) {
                onRegisterListener.register(3);
            } else {
                if (!userBean.getPassword().equals(userBean.getRepeatPassword())) {
                    onRegisterListener.register(1);
                } else {
                    //网络数据库
                    AVUser user = new AVUser();// 新建 AVUser 对象实例
                    user.setUsername(userBean.getEmail());// 设置用户名
                    user.setPassword(userBean.getPassword());// 设置密码
                    user.setEmail(userBean.getEmail());// 设置邮箱
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                // 注册成功
                                onRegisterListener.register(2);
                                return;
                            } else {
                                // 失败的原因可能有多种，常见的是用户名已经存在。
                                if (e.getCode() == 203) {
                                    onRegisterListener.register(4);
                                    onRegisterListener.Log(e.toString());
                                    return;
                                }
                                onRegisterListener.register(5);
                                //本地数据库

                            }
                        }
                    });


                }
            }
        }
    }
}