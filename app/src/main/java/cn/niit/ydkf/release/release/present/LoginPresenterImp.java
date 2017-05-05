package cn.niit.ydkf.release.release.present;

import android.content.Context;
import cn.niit.ydkf.release.release.model.interfaces.LoginUserBiz;
import cn.niit.ydkf.release.release.model.LoginUserBizlmpl;
import cn.niit.ydkf.release.release.model.entities.User;
import cn.niit.ydkf.release.release.model.interfaces.OnLoginListener;
import cn.niit.ydkf.release.release.view.activities.MainActivity;
import cn.niit.ydkf.release.release.view.view_interface.LoginView;


public class LoginPresenterImp implements LoginPresenter, OnLoginListener {
    private LoginView loginView;
    private User user = new User();
    private LoginUserBiz userBiz;

    public LoginPresenterImp(Context context, LoginView loginView) {
        this.loginView = loginView;
        userBiz = new LoginUserBizlmpl(context, this);
    }




    @Override
    public void login() {
        user.setEmail(loginView.getEmail());
        user.setPassword(loginView.getPassword());
        userBiz.login(user);
    }

    @Override
    public void login(int statu) {
        switch (statu) {
            case 0:
                loginView.goActivity(MainActivity.class);
                loginView.finishActivity();
                break;
            case 1:
                loginView.showMessage("该用户邮箱未注册!");
                break;
            case 2:
                loginView.showMessage("密码不正确!");
                break;
            case 3:
                loginView.showMessage("输入框不能为空!");
                break;
            case 4:
                loginView.showMessage("出现未知的异常，有可能是网络连接失败!");
                break;
            default:
                break;
        }
    }

    @Override
    public void Log(String msg) {
        loginView.Log(msg);
    }

}
