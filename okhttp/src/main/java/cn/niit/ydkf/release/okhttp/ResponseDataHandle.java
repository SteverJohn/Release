package cn.niit.ydkf.release.okhttp;

/**
 * Created by Black_Jack on 2017/4/2.
 */

public class ResponseDataHandle {
    public ResponseDataListener mListener=null;
    public Class<?> mClass=null;


    public ResponseDataHandle(ResponseDataListener listener){
        mListener=listener;

    }
    public ResponseDataHandle(ResponseDataListener listener,Class<?> clazz){
        mListener=listener;
        mClass=clazz;
    }
}
