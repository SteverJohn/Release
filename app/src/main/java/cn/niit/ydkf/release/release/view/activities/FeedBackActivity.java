package cn.niit.ydkf.release.release.view.activities;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import butterknife.Bind;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.R;

public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.feedback_content)
    EditText et_feedback;
    @Bind(R.id.btn_ok)
    Button btnOk;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int bindLayout() {
        return R.layout.feedback;
    }

    @Override
    public void initView() {
initToolBar();
    }

    @Override
    public void initData() {

    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @OnClick(R.id.btn_ok)
    public void sendFeedback() {
        if (TextUtils.isEmpty(et_feedback.getText().toString())) {
            snackbar("内容不能为空");
            return;
        }
        AVObject post = new AVObject("FeedBack");
        post.put("content", et_feedback.getText().toString());
        post.put("id", AVUser.getCurrentUser().getObjectId());
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    toast("反馈提交成功");
                    finish();
                }
            }
        });
    }

    @Override
    public void onFailureData(String response) {

    }

    @Override
    public void onSuccessData(String response) {

    }
}
