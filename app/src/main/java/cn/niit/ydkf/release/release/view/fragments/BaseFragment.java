package cn.niit.ydkf.release.release.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;

import java.io.IOException;
import java.util.Map;

import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.interfaces.IBaseFragment;
import cn.niit.ydkf.release.release.model.interfaces.IgetNetData;
import cn.niit.ydkf.release.release.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseFragment extends Fragment implements IBaseFragment,IgetNetData {
    private Bundle bundle;
    private Context mContext;
    private ProgressDialog progressDialog;

    public void logd(String str) {
        Log.d(getClass().getSimpleName(), str);
    }

    public void loge(String str) {
        Log.e(getClass().getSimpleName(), str);
    }

    public void logv(String str) {
        Log.v(getClass().getSimpleName(), str);
    }

    public void toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Context mGetContext() {

        if (mContext == null) {
            mContext = getActivity();
        }

        return mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(bindLayout(), null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData(view);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void goActivity(Class<?> goActivity) {
        startActivity(new Intent(getActivity(), goActivity));
    }

    public void doGet(String url){
        HttpUtils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final  String str=e.toString();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logv(str);
                            onFailureData(str);

                        }
                    });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str=response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logv(str);
                        onSuccessData(str);
                    }
                });

            }
        });
    }
    public void doPost(String url, Map<String,String> map){
    HttpUtils.doPost(url, map, new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            final  String str=e.toString();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logv(str);
                    onFailureData(str);
                }
            });
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
                final String str=response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logv(str);
                        onSuccessData(str);
                    }
                });

        }
    });
    }
    protected void showProgressDialog(String str) {
        progressDialog = new ProgressDialog(getActivity(), R.style
                .AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(str);
        progressDialog.show();
    }

    protected void dismisProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }


    }




