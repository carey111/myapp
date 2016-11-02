package com.example.administrator.myapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.pojo.RentInfo;
import com.example.administrator.myapp.util.NetUtil;
import com.example.administrator.myapp.widget.TitleBar;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ReleaseActivity extends AppCompatActivity {

    @InjectView(R.id.tb_bar)
    TitleBar tbBar;
    @InjectView(R.id.iv_zhaopian)
    ImageView ivZhaopian;
    @InjectView(R.id.rl_zhaopian)
    RelativeLayout rlZhaopian;
    @InjectView(R.id.tv_1)
    TextView tv1;
    @InjectView(R.id.tv_2)
    EditText tv2;
    @InjectView(R.id.tv_3)
    TextView tv3;
    @InjectView(R.id.tv_4)
    EditText tv4;
    @InjectView(R.id.tv_5)
    TextView tv5;
    @InjectView(R.id.tv_6)
    EditText tv6;
    @InjectView(R.id.tv_7)
    TextView tv7;
    @InjectView(R.id.tv_8)
    EditText tv8;
    @InjectView(R.id.rl_fang)
    RelativeLayout rlFang;
    @InjectView(R.id.rl_xiangqing)
    RelativeLayout rlXiangqing;
    @InjectView(R.id.bt_fabu)
    Button btFabu;
    @InjectView(R.id.iv_zhaopian1)
    ImageView ivZhaopian1;
    @InjectView(R.id.iv_zhaopian2)
    ImageView ivZhaopian2;
    @InjectView(R.id.tv_a)
    TextView tvA;
    @InjectView(R.id.tv_b)
    EditText tvB;
    @InjectView(R.id.tv_10)
    EditText tv10;
    private TitleBar tv_bar;
    String phoneNum1 = null;
    private EditText zujin;
    private EditText mianji;
    private EditText huxing;
    private EditText dizhi;
    private EditText xiangqing;
    String zhenzujin;
    String zhenmianji;
    String zhenhuxing;
    String zhendizhi;
    String zhenxiangqing;
    String zhentype;
    private EditText type;
    String roomId=null;
    //相机拍摄照片和视频的标准目录
    private File file;
    private Uri imageUri;
    String items[] = {"相册选择", "拍照"};
    public static final int SELECT_PIC = 11;
    public static final int TAKE_PHOTO = 12;
    public static final int CROP = 13;
    private ImageView zhaopian;
    int uploadIndex = -1;
RentInfo rentInfo=null;
    String imgUrl1=null;
    String imgUrl2=null;
    String imgUrl3=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        ButterKnife.inject(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        phoneNum1 = sharedPreferences.getString("phone", "");
        tv_bar = ((TitleBar) findViewById(R.id.tb_bar));

        tv_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
            imageUri = Uri.fromFile(file);
            Log.e("s", imageUri.getPath());
        }
    }
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }
    public void crop(Uri uri) {
        //  intent.setType("image/*");
        //裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PIC:
                //相册选择
                if (data != null) {
                    crop(data.getData());
                    Log.i("onActivityResult", "onActivityResult: " + data.getData().getPath());
                    Log.i("onAct", Environment.getExternalStorageDirectory() + "");
                }
                break;
            case TAKE_PHOTO:
                crop(Uri.fromFile(file));
                break;
            case CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        showImage(bitmap);
                    }
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //将bitmap保存在文件中
    public void saveImage(Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
    }
    //显示图片，上传服务器
    public void showImage(Bitmap bitmap) {
 if(uploadIndex==0) {
    ivZhaopian.setImageBitmap(bitmap);//iv显示图片
    saveImage(bitmap);//保存文件
     uploadImage1();
  }else if(uploadIndex==1) {
    ivZhaopian1.setImageBitmap(bitmap);
    saveImage(bitmap);//保存文件
     uploadImage2();
    }else if(uploadIndex==2){
    ivZhaopian2.setImageBitmap(bitmap);//iv显示图片
    saveImage(bitmap);//保存文件
     uploadImage3();
}
    }
    private void initData3() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "rentinfoqueryservlet1");
        requestParams.addQueryStringParameter("roomId", roomId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                rentInfo=gson.fromJson(result, RentInfo.class);
                imgUrl3 =rentInfo.getRoomImg2();
                x.image().bind(ivZhaopian2,NetUtil.url+imgUrl3);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    private void initData2() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "rentinfoqueryservlet1");
        requestParams.addQueryStringParameter("roomId", roomId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                rentInfo=gson.fromJson(result, RentInfo.class);
                imgUrl2 =rentInfo.getRoomImg1();
                x.image().bind(ivZhaopian1,NetUtil.url+imgUrl2);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void initData1() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "rentinfoqueryservlet");
        requestParams.addQueryStringParameter("roomId", roomId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
    @Override
    public void onSuccess(String result) {
        Gson gson =new Gson();
        rentInfo=gson.fromJson(result, RentInfo.class);
        imgUrl1 =rentInfo.getPhotoImg();
        x.image().bind(ivZhaopian,NetUtil.url+imgUrl1);
    }
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
});
    }
    public void uploadImage1(){
        RequestParams requestParams = new RequestParams(NetUtil.url + "uploadjiahaoservlet1");
        requestParams.addBodyParameter("roomId",roomId);
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               initData1();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    public void uploadImage2(){
        RequestParams requestParams = new RequestParams(NetUtil.url + "uploadjiahaoservlet2");
        requestParams.addBodyParameter("roomId",roomId);
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                initData2();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void uploadImage3(){
        RequestParams requestParams = new RequestParams(NetUtil.url + "uploadjiahaoservlet3");
        requestParams.addBodyParameter("roomId",roomId);
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                initData3();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void initView() {
        zujin = ((EditText) findViewById(R.id.tv_2));
        mianji = ((EditText) findViewById(R.id.tv_4));
        huxing = ((EditText) findViewById(R.id.tv_6));
        dizhi = ((EditText) findViewById(R.id.tv_8));
        xiangqing = ((EditText) findViewById(R.id.tv_10));
        type = ((EditText) findViewById(R.id.tv_b));
        zhenzujin = zujin.getText() + "";
        zhenmianji = mianji.getText() + "";
        zhenhuxing = huxing.getText() + "";
        zhendizhi = dizhi.getText() + "";
        zhenxiangqing = xiangqing.getText() + "";
        zhentype = type.getText() + "";
    }
    private void initData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "inserthouseservlet");
        requestParams.addQueryStringParameter("phoneNum", phoneNum1);
        requestParams.addQueryStringParameter("roomArea", zhenmianji);
        requestParams.addQueryStringParameter("roomRent", zhenzujin);
        requestParams.addQueryStringParameter("roomContent", zhenxiangqing);
        requestParams.addQueryStringParameter("roomAdress", zhendizhi);
        requestParams.addQueryStringParameter("rongliang", zhenhuxing);
        requestParams.addQueryStringParameter("roomType", zhentype);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("onSuccess: ", "onSuccess: "+result);
                String[] strs=result.split(",");
                if (strs[0].equals("true")) {
                    Toast.makeText(ReleaseActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                }
                roomId=strs[1];
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @OnClick(R.id.bt_fabu)
    public void onClick() {
        initView();
        initData();
    }

    @OnClick({R.id.iv_zhaopian, R.id.iv_zhaopian1, R.id.iv_zhaopian2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_zhaopian:
                uploadIndex = 0;
                new AlertDialog.Builder(this).setTitle("请选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //相册选择
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent, SELECT_PIC);
                                break;
                            case 1:
                                //拍照:
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //直接裁剪
                                //  crop(intent2);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent2, TAKE_PHOTO);
                                break;
                        }

                    }
                }).show();

                break;
            case R.id.iv_zhaopian1:
                uploadIndex = 1;
                new AlertDialog.Builder(this).setTitle("请选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //相册选择
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent, SELECT_PIC);
                                break;
                            case 1:
                                //拍照:
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //直接裁剪
                                //  crop(intent2);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent2, TAKE_PHOTO);
                                break;
                        }

                    }
                }).show();

                break;
            case R.id.iv_zhaopian2:
                uploadIndex = 2;
                new AlertDialog.Builder(this).setTitle("请选择").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //相册选择
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent, SELECT_PIC);
                                break;
                            case 1:
                                //拍照:
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //直接裁剪
                                //  crop(intent2);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent2, TAKE_PHOTO);
                                break;
                        }

                    }
                }).show();

                break;
        }
    }
}
