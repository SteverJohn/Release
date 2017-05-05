package cn.niit.ydkf.release.release.adapter;

import android.media.tv.TvView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.niit.ydkf.release.release.view.fragments.NewsFragment;


public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    public MyViewPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        this.titles= titles;
    }

    @Override
    public Fragment getItem(int position) {
        NewsFragment newsFragment=new NewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",titles[position]);
        bundle.putInt("position",position);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
