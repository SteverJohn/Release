package cn.niit.ydkf.release.release.view.view_interface;

public interface RegisterView {
	String getEmail();
	String getPassword();
	String getRepeatPassword();
	void showMessage(String msg);
	void Log(String msg);
}
