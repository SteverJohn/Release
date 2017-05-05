package cn.niit.ydkf.release.release.view.activities;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;

import butterknife.Bind;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.view.fragments.ChoicenessFragment;
import cn.niit.ydkf.release.release.view.fragments.HomeFragment;
import cn.niit.ydkf.release.release.view.fragments.LifeFragment;
import cn.niit.ydkf.release.release.view.fragments.MineFragment;
import cn.niit.ydkf.release.release.view.fragments.NoLoginFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    @Bind(R.id.rg)
    public RadioGroup rg;
    @Bind(R.id.rs_main_title)
    public TextView mTitle;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    private FragmentTransaction ft;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;



    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initToolBar();
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        rg.setOnCheckedChangeListener(this);
        ft = fragmentManager.beginTransaction();

        homeFragment=HomeFragment.getInstance();
        if (rg.getCheckedRadioButtonId() == R.id.rb_home) {
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            setBarTitle(R.string.home);
            ft.replace(R.id.fl_main, homeFragment);
            ft.commit();
        }
    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        switch(checkedId){
            case R.id.rb_home:
                setBarTitle(R.string.home);
                ft.replace(R.id.fl_main, homeFragment);
                break;
            case R.id.rb_choiceness:
                setBarTitle(R.string.choiceness);
                ft.replace(R.id.fl_main, ChoicenessFragment.getInstance());
                break;
            case R.id.rb_life:
                setBarTitle(R.string.life);
                ft.replace(R.id.fl_main, LifeFragment.getInstance());
                break;
            case R.id.rb_mine:
                setBarTitle(R.string.mine);
                if (AVUser.getCurrentUser()!=null){
                    ft.replace(R.id.fl_main, MineFragment.getInstance());
                }else {
                    ft.replace(R.id.fl_main, NoLoginFragment.getInstance());
                }
                break;
        }
        ft.commit();
    }
    private void setBarTitle(int title) {
        mTitle.setText(title);
    }


    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
