package cn.niit.ydkf.release.release.present;

import android.content.Context;

import cn.niit.ydkf.release.release.model.RegisterUserBizlmpl;
import cn.niit.ydkf.release.release.model.entities.User;
import cn.niit.ydkf.release.release.model.interfaces.OnRegisterListener;
import cn.niit.ydkf.release.release.model.interfaces.RegisterUserBiz;
import cn.niit.ydkf.release.release.view.view_interface.RegisterView;


public class RegisterPresenterImp implements RegisterPreseter, OnRegisterListener {
    private RegisterView registerView;
    private User user = new User();
    private RegisterUserBiz userBiz;

    public RegisterPresenterImp(Context context, RegisterView registerView) {
        this.registerView = registerView;
        userBiz = new RegisterUserBizlmpl(this);
    }

    @Override
    public void register() {
        user.setEmail(registerView.getEmail());
        user.setPassword(registerView.getPassword());
        user.setRepeatPassword(registerView.getRepeatPassword());
        userBiz.register(user);
    }

    @Override
    public void register(int statu) {
        switch (statu) {
            case 0:
                registerView.showMessage("输入框不能为空!");
                break;
            case 1:
                registerView.showMessage("两次输入的密码不一致!");
                break;
            case 2:
                registerView.showMessage("注册成功!请返回登录");
                break;
            case 3:
                registerView.showMessage("邮箱格式不正确!");
                break;
            case 4:
                registerView.showMessage("邮箱已被注册!");
            case 5:
                registerView.showMessage("出现未知的异常，有可能是网络连接失败!");
                break;
        }

    }

    @Override
    public void Log(String msg) {
        registerView.Log(msg);
    }

}
