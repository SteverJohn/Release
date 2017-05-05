package cn.niit.ydkf.release.release.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	public boolean isEMail(String email) {
		// return Patterns.EMAIL_ADDRESS.matcher(email).matches();
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
