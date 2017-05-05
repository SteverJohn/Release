package cn.niit.ydkf.release.release.view.view_interface;

public interface LoginView {
	String getEmail();

	String getPassword();

	void showMessage(String msg);

	void goActivity(Class<?> goActivity);

	void finishActivity();

	void Log(String msg);
}
