package cn.niit.ydkf.release.release.view.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.adapter.LifeAdapter;
import cn.niit.ydkf.release.release.view.activities.HealthActivity;
import cn.niit.ydkf.release.release.view.activities.HotListActivity;
import cn.niit.ydkf.release.release.view.activities.JestActivity;
import cn.niit.ydkf.release.release.view.activities.JokesActivity;
import cn.niit.ydkf.release.release.view.activities.VideoActivity;


public class LifeFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private int[] images = {R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private static final int MUSIC=0;
    private static final int JOKES=1;
    private static final int JEST=2;
    private static final int VIDEO=3;
    private static final int HEALTH=4;
    private String[] text = {"音乐","段子","笑话","视频","养生","天气"};
    private static volatile LifeFragment lifeFragment;
    @Bind(R.id.gv_life)
    GridView gv_life;
    private Intent intent;

    public static LifeFragment getInstance() {
        if (lifeFragment == null) {
            synchronized (HomeFragment.class) {
                lifeFragment = new LifeFragment();
            }
        }
        return lifeFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.frag_life;
    }

    @Override
    public void initView(View view) {
        gv_life.setOnItemClickListener(this);
    }

    @Override
    public void initData(View view) {
        gv_life.setAdapter(new LifeAdapter(getActivity(),images,text));
    }


    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case MUSIC:
               intent=new Intent(getActivity(), HotListActivity.class);
                break;
            case JOKES:
                intent=new Intent(getActivity(), JokesActivity.class);
                break;
            case JEST:
                intent=new Intent(getActivity(), JestActivity.class);
                break;
            case VIDEO:
                intent=new Intent(getActivity(), VideoActivity.class);
                break;
            case HEALTH:
                intent=new Intent(getActivity(), HealthActivity.class);
                break;
        }
        getActivity().startActivity(intent);
    }
}
