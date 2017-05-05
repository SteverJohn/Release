package cn.niit.ydkf.release.release.view.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.niit.ydkf.release.release.R;
import cn.niit.ydkf.release.release.model.entities.Contants;
import cn.niit.ydkf.release.release.view.activities.AboutActivity;
import cn.niit.ydkf.release.release.view.activities.FeedBackActivity;
import cn.niit.ydkf.release.release.view.activities.LoginActivity;


public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final int PHOTO_REQUEST_CAREMA = 1;
    private static final String PHOTO_FILE_NAME = "head_img.jpg";
    @Bind(R.id.sdv_userinfo_icon)
    public SimpleDraweeView userinfo_icon;
    private static volatile MineFragment mineFragment;
    @Bind(R.id.tv_usercenter_username)
    TextView tvUsercenterUsername;
    @Bind(R.id.rl_usercenter_user)
    RelativeLayout rlUsercenterUser;

    @Bind(R.id.btn_usercenter_logout)
    Button btnUsercenterLogout;
    @Bind(R.id.tb_about)
    TableLayout tbAbout;
    @Bind(R.id.tb_feedback)
    TableLayout tbFeedback;
    private Dialog dialog;
    private File tempFile;
    private Uri uri;

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (HomeFragment.class) {
                mineFragment = new MineFragment();
            }
        }
        return mineFragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.frag_mine;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        if (AVUser.getCurrentUser().getString("headImgUrl").isEmpty()) {
            uri = Uri.parse(Contants.DEFAULT_ICON);
        } else {
            uri = Uri.parse(AVUser.getCurrentUser().getString("headImgUrl"));
        }
        userinfo_icon.setImageURI(uri);
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

    @OnClick({R.id.sdv_userinfo_icon, R.id.btn_usercenter_logout,R.id.tb_feedback,R.id.tb_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdv_userinfo_icon:
                changeIcon();
                break;
            case R.id.btn_usercenter_logout:
                showLogoutDialog("确定退出？");
                break;
            case R.id.tb_feedback:
                goActivity(FeedBackActivity.class);
                break;
            case R.id.tb_about:
                goActivity(AboutActivity.class);
                break;
        }
    }

    private void showLogoutDialog(String content) {
        dialog = new AlertDialog.Builder(getActivity())
                .setMessage(content)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AVUser.logOut();
                        toast("注销成功");
                        goActivity(LoginActivity.class);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false).show();
    }

    private void changeIcon() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        (view.findViewById(R.id.btn_photo_choose_dialog_frommobile)).setOnClickListener(this);
        (view.findViewById(R.id.btn_photo_choose_dialog_takephoto)).setOnClickListener(this);
        (view.findViewById(R.id.btn_photo_choose_dialog_cancel)).setOnClickListener(this);
        dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (dialog != null)
            dialog.dismiss();
        if (resultCode == 0)
            return;
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        }
        if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                toast("未找到存储卡，无法存储照片");
            }
        }
        if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (saveBitmap2file(bitmap, PHOTO_FILE_NAME)) {
                    try {
                        String fileUrl = Environment
                                .getExternalStorageDirectory() +
                                "/" + PHOTO_FILE_NAME;
                        final AVFile file = AVFile.withAbsoluteLocalPath(PHOTO_FILE_NAME, fileUrl);
                        showProgressDialog("正在上传头像...");
                        file.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                logd(e == null ? "成功" : "失败");
                                dismisProgressDialog();
                                AVObject post = AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId());
                                post.put("headImgUrl", file.getUrl());
                                post.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        logd(e == null ? "成功" : "失败");
                                        Uri headImgUri = Uri.parse(AVUser.getCurrentUser().getString("headImgUrl"));
                                        userinfo_icon.setImageURI(headImgUri);
                                        toast("头像设置成功");
                                    }
                                });
                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean saveBitmap2file(Bitmap bitmap, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(Environment
                    .getExternalStorageDirectory() + "/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap.compress(format, quality, stream);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_photo_choose_dialog_cancel:
                if (dialog != null)
                    dialog.dismiss();
                break;
            case R.id.btn_photo_choose_dialog_frommobile:
                Intent localintent = new Intent(Intent.ACTION_PICK);
                localintent.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(localintent, PHOTO_REQUEST_GALLERY);
                break;
            case R.id.btn_photo_choose_dialog_takephoto:
                // 激活相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                // 判断存储卡是否可以用，可用进行存储
                if (hasSdcard()) {
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                    // 从文件中创建uri
                    Uri uri = Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                }
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);

                break;
        }
    }

    /*
     * 判断sdcard是否被挂载
     */
    private boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
