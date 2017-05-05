package cn.niit.ydkf.release.release.view.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;
import java.util.Map;

import butterknife.ButterKnife;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.interfaces.IBaseActivity;
import cn.niit.ydkf.release.release.model.interfaces.IgetNetData;
import cn.niit.ydkf.release.release.utils.HttpUtils;
import cn.niit.ydkf.release.release.utils.SystemBarTintManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity,IgetNetData {
    private Toast mToast;
    private MaterialDialog.Builder builder;
    private MaterialDialog materialDialog;
    private View view;
    private long exitTime = 0;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private AlertDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
        view = this.getLayoutInflater().inflate(bindLayout(), null);
        setContentView(bindLayout());
        ButterKnife.bind(this);

        initView();
        initData();
        permissionApply();

    }

    private void permissionApply() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }
    }

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = this.getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                         .setTitle("存储权限不可用")
                         .setMessage("由于应用需要获取存储空间，为你存储个人信息；\n否则，您将无法正常使用应用")
                        .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                                       startRequestPermission();
                                     }
                  })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                                         finish();
                                    }
                  }).setCancelable(false).show();
    }

    // 开始提交请求权限
      private void startRequestPermission() {
                 ActivityCompat.requestPermissions(this, permissions, 321);
      }
    // 用户权限 申请 的回调方法
     @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                 super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                 if (requestCode == 321) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                                         // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                                         boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                                         if (!b) {
                                                 // 用户还是想用我的 APP 的
                                               // 提示用户去应用设置界面手动开启权限
                                                 showDialogTipUserGoToAppSettting();
                                             } else
                                                 finish();
                                     } else {
                                        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                                   }
                           }
                     }
          }
    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                      .setTitle("存储权限不可用")
                       .setMessage("请在-应用设置-权限-中，允许支付宝使用存储权限来保存用户数据")
                        .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                      @Override
                    public void onClick(DialogInterface dialog, int which) {
                                        // 跳转到应用设置界面
                                       goToAppSetting();
                                    }
                 })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                 }).setCancelable(false).show();
    }
    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

                 intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                 Uri uri = Uri.fromParts("package", getPackageName(), null);
                 intent.setData(uri);

                startActivityForResult(intent, 123);
    }
    //
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                 if (requestCode == 123) {

                         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                // 检查该权限是否已经获取
                                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                                 // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                                if (i != PackageManager.PERMISSION_GRANTED) {
                                        // 提示用户应该去应用设置界面手动开启权限
                                        showDialogTipUserGoToAppSettting();
                                    } else {
                                         if (dialog != null && dialog.isShowing()) {
                                                dialog.dismiss();
                                             }
                                         Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                                    }
                             }
                    }
             }

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
        if (mToast == null) {
            mToast = Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(str);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void goActivity(Class<?> goActivity) {
        startActivity(new Intent(this, goActivity));
    }
    public void snackbar(String str) {
        Snackbar.make(getRootView(), str, Snackbar.LENGTH_SHORT).show();
    }
    /**
     * 得到根视图
     *
     * @return 根布局视图
     */
    protected View getRootView() {
        return view;
    }

    @Override
    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    private void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }

    }

    protected void showProgressDialog(String str) {
        if (builder == null) {
            builder = new MaterialDialog.Builder(this);
            builder.cancelable(false);
            builder.title("数据加载中...");
            builder.content(str)
                    .progress(true, 0);
        }
        materialDialog = builder.show();
    }

    protected void dismisProgressDialog() {
        materialDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void doPost(String url, Map<String,String> map){
        HttpUtils.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final  String str=e.toString();
               runOnUiThread(new Runnable() {
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
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        logv(str);
                        onSuccessData(str);
                    }
                });

            }
        });
    }
}

