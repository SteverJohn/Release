package cn.niit.ydkf.release.release.view.fragments;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import cn.niit.ydkf.release.release.App;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.MyViewPagerAdapter;


public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener{
    @Bind(R.id.news_tabs)
    public TabLayout news_tabs;
    @Bind(R.id.news_viewpager)
    public ViewPager news_viewpager;
    private String[] titles = { "推荐", "社会", "国内", "国际", "娱乐","体育","军事","科技","财经","时尚" };
    private static volatile HomeFragment homeFragment;

    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            synchronized (HomeFragment.class) {
                homeFragment = new HomeFragment();
            }
        }
        return homeFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.frag_home;
    }

    @Override
    public void initView(View view) {
        news_tabs.setOnTabSelectedListener(this);
        news_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(news_tabs));
    }

    @Override
    public void initData(View view) {
        MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter(getActivity().getSupportFragmentManager(),titles);
        news_tabs.setTabsFromPagerAdapter(myViewPagerAdapter);
        news_viewpager.setAdapter(myViewPagerAdapter);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        news_viewpager.setCurrentItem(tab.getPosition(), true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
