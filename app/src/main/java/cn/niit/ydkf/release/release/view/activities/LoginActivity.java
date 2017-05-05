package cn.niit.ydkf.release.release.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.present.LoginPresenter;
import cn.niit.ydkf.release.release.present.LoginPresenterImp;
import cn.niit.ydkf.release.release.view.view_interface.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    LoginPresenter loginPresenter;
    @Bind(R.id.et_email)
    EditText et_email;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_ok)
    Button btn_ok;
    @Bind(R.id.tv_register)
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImp(this, this);

    }

    @Override
    public String getEmail() {
        return et_email.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goActivity(Class<?> goActivity) {
        startActivity(new Intent(this, goActivity));
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void Log(String msg) {
        Log.d("error", msg);
    }

    @OnClick({R.id.btn_ok, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                loginPresenter.login();
                break;
            case R.id.tv_register:
                goActivity(RegisterActivity.class);
                break;
        }
    }


}
