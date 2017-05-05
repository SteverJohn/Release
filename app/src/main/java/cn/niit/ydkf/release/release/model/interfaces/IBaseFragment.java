package cn.niit.ydkf.release.release.model.interfaces;

import android.view.View;


public interface IBaseFragment {
    /**
     * 绑定布局文件
     *
     * @return R.layout.xxx布局文件的id
     */
    int bindLayout();

    /**
     * 初始化视图
     *
     * @param view 根view
     */
    void initView(View view);

    /**
     * 初始化行为
     *
     * @param view 根view
     */
    void initData(View view);


}
