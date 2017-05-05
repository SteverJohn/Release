package cn.niit.ydkf.release.recycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.my_recycleView)
    RecyclerView myRecycleView;

    private MyRecycleViewAdapter mAdapter;
    private List mListData ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mListData = new ArrayList();
        for(int i =1;i<15;i++){
            mListData.add("这是第"+i+"个Item");
        }
    }


    private void initView() {
        //设置显示方式
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycleView.setLayoutManager(llManager);
        //添加分割线
        myRecycleView.addItemDecoration(new ListItemDecoration(this,ListItemDecoration.VERTICAL_LIST));
        //设置Adapter
        mAdapter = new MyRecycleViewAdapter();
        mAdapter.setListData(mListData);
        myRecycleView.setAdapter(mAdapter);

    }
}
