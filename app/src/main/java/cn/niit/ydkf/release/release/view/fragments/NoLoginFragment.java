package cn.niit.ydkf.release.release.view.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.view.activities.LoginActivity;

public class NoLoginFragment extends BaseFragment {
    @Bind(R.id.sdv_userinfo_icon)
    SimpleDraweeView sdvUserinfoIcon;
    @Bind(R.id.loginFast)
    Button loginFast;
    private static volatile NoLoginFragment noLoginFragment;
    public static NoLoginFragment getInstance() {
        if (noLoginFragment == null) {
            synchronized (HomeFragment.class) {
                noLoginFragment = new NoLoginFragment();
            }
        }
        return noLoginFragment;
    }
    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }

    @Override
    public int bindLayout() {
        return R.layout.frag_no_login;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        Uri uri=Uri.parse(Contants.DEFAULT_ICON);
        sdvUserinfoIcon.setImageURI(uri);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.loginFast)
    public void onViewClicked() {
        goActivity(LoginActivity.class);
    }
}
