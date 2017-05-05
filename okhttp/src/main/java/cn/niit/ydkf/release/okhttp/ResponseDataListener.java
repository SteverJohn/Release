package cn.niit.ydkf.release.okhttp;

/**
 * Created by Black_Jack on 2017/4/2.
 */

public interface ResponseDataListener {
    public void onSuccess(Object responseObj);

    public void onFailure(Object reasonObj);
}
