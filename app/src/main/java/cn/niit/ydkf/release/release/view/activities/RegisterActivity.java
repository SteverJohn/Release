package cn.niit.ydkf.release.release.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.present.RegisterPresenterImp;
import cn.niit.ydkf.release.release.present.RegisterPreseter;
import cn.niit.ydkf.release.release.view.view_interface.RegisterView;


public class RegisterActivity extends AppCompatActivity implements RegisterView {
	EditText et_email, et_password, et_repeatInput;
	Button btn_ok, btn_back;
	RegisterPreseter registerPreseter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		registerPreseter = new RegisterPresenterImp(this, this);

		registerPreseter = new RegisterPresenterImp(this, this);

		btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				registerPreseter.register();
			}
		});
		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RegisterActivity.this.finish();
			}
		});
	}

	private void initView() {
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		et_repeatInput = (EditText) findViewById(R.id.et_repeatInput);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_back = (Button) findViewById(R.id.btn_back);
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
	public String getRepeatPassword() {
		return et_repeatInput.getText().toString();
	}

	@Override
	public void showMessage(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void Log(String msg) {
		Log.i("error",msg);
	}
}
