package cn.niit.ydkf.release.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private Request.Builder builder;
    private TextView mTvResult;
    private ImageView mIvResult;
    private String mbaseUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mIvResult= (ImageView) findViewById(R.id.im_result);
        okHttpClient = new OkHttpClient();
    }

    public void doGet(View view) {
        //1.拿到okHttpClient(全局执行者)
//            OkHttpClient okHttpClient=  okHttpClient =new OkHttpClient();
        //2.构造Request
        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url(mbaseUrl).build();
        //将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure:" + e.getMessage());
                e.printStackTrace();
            }
            //非ui线程
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // L.e("OnResponse:"+response.body().string());
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText(str);
                    }
                });
            }
        });
    }

    public void doPost(View view) {
        //1.拿到okHttpClient(全局执行者)
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.构造Request
        FormBody.Builder formBodyBuilder=new FormBody.Builder();
        formBodyBuilder.add("mb","18752028717");
        formBodyBuilder.add("pwd","1234556");
        Request request=new Request.Builder().url("").post(formBodyBuilder.build()).build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            //非ui线程
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }

    public void doPostString(View view) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "username:lcx,password=123");
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(mbaseUrl + "login").post(requestBody).build();
        //将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // L.e("OnResponse:"+response.body().string());
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText(str);
                    }
                });
            }
        });
    }

    public void doPostFile(View view) {
        File file=new File(Environment.getExternalStorageDirectory(),"banner2.jpg");
        if (!file.exists()){
            L.e(file.getAbsolutePath()+"not exit!");
            return;
        }
    }
    public void doPostDownLoad(View view) {
        Request.Builder builder = new Request.Builder();
        final Request request = builder
                .get()//
                .url(mbaseUrl + "file./lcx.jpg")//
                .build();
        Call call = okHttpClient.newCall(request);
        //执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // L.e("OnResponse:"+response.body().string());
                InputStream is=response.body().byteStream();
                int len=0;
                File file=new File(Environment.getExternalStorageDirectory(),"lcx12306.jpg");
                byte[] buf=new byte[128];
                FileOutputStream fos=new FileOutputStream(file);
                while ((len=is.read(buf))!=-1){
                    fos.write(buf,0,len);
                }
                fos.flush();
                fos.close();
                is.close();
                L.e("download success!");
            }
        });
    }
    public void doPostDownLoadImageView(View view) {
        Request.Builder builder = new Request.Builder();
        final Request request = builder
                .get()//
                .url(mbaseUrl + "file./lcx.jpg")//
                .build();
        Call call = okHttpClient.newCall(request);
        //执行call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // L.e("OnResponse:"+response.body().string());
                InputStream is=response.body().byteStream();
              final Bitmap bitmap= BitmapFactory.decodeStream(is);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            mIvResult.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }
}