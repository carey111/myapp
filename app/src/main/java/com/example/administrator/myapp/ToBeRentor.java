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

import com.example.administrator.myapp.pojo.User;
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

public class ToBeRentor extends AppCompatActivity {
    @InjectView(R.id.tb_bar)
    TitleBar tbBar;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.tv_shiming)
    TextView tvShiming;
    @InjectView(R.id.et_shiming)
    EditText etShiming;
    @InjectView(R.id.tv_zhengjianxinxi)
    TextView tvZhengjianxinxi;
    @InjectView(R.id.rl_shimingkuang)
    RelativeLayout rlShimingkuang;
    @InjectView(R.id.tv_shenfen)
    TextView tvShenfen;
    @InjectView(R.id.view1)
    View view1;
    @InjectView(R.id.tv_zhengfan)
    TextView tvZhengfan;
    @InjectView(R.id.iv_jiahao1)
    ImageView ivJiahao1;
    @InjectView(R.id.iv_jiahao2)
    ImageView ivJiahao2;
    @InjectView(R.id.bt_tijiao)
    Button btTijiao;
    @InjectView(R.id.rl_shenfenkuang)
    RelativeLayout rlShenfenkuang;
    @InjectView(R.id.et_shenfen)
    EditText etShenfen;
    private TitleBar tv_bar;
    String phoneNum1 = null;
    private EditText et_shiming;
    private EditText et_shenfen;
    private Button bt_tijiao;
    String realName;
    String cardNum;
    private ImageView cardZheng;
    private ImageView cardFan;
    User user = null;
    String imgUrl1 = null;
    String imgUrl2 = null;
    //相机拍摄照片和视频的标准目录
    private File file;
    private Uri imageUri;
    String items[] = {"相册选择", "拍照"};
    public static final int SELECT_PIC = 11;
    public static final int TAKE_PHOTO = 12;
    public static final int CROP = 13;
    int uploadIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_rentor);
        ButterKnife.inject(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SP",
                Activity.MODE_PRIVATE);
        phoneNum1 = sharedPreferences.getString("phone", "");
        tv_bar = ((TitleBar) findViewById(R.id.tb_bar));
        cardZheng = ((ImageView) findViewById(R.id.iv_jiahao1));
        cardFan = ((ImageView) findViewById(R.id.iv_jiahao2));
        tv_bar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }


    private void initView() {
        et_shiming = ((EditText) findViewById(R.id.et_shiming));
        et_shenfen = ((EditText) findViewById(R.id.et_shenfen));
        bt_tijiao = ((Button) findViewById(R.id.bt_tijiao));

        realName = et_shiming.getText() + "";
        cardNum = et_shenfen.getText() + "";

    }

    private void initData() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "toberentorservlet");
        requestParams.addQueryStringParameter("phoneNum", phoneNum1);
        requestParams.addQueryStringParameter("realName", realName);
        requestParams.addQueryStringParameter("cardNum", cardNum);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("true")) {
                    Toast.makeText(ToBeRentor.this, "等待审核", Toast.LENGTH_LONG).show();

                } else {

                }
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
        RequestParams requestParams = new RequestParams(NetUtil.url + "userinfoqueryservlet");
        requestParams.addQueryStringParameter("phoneNum", phoneNum1);
       x.http().get(requestParams, new Callback.CommonCallback<String>() {
           @Override
           public void onSuccess(String result) {
               Gson gson = new Gson();
               user = gson.fromJson(result, User.class);
               imgUrl2 = user.getCardFan();
               x.image().bind(cardFan, NetUtil.url + imgUrl2);
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
        RequestParams requestParams = new RequestParams(NetUtil.url + "userinfoqueryservlet");
        requestParams.addQueryStringParameter("phoneNum", phoneNum1);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                user = gson.fromJson(result, User.class);
                imgUrl1 = user.getCardZheng();
                x.image().bind(cardZheng, NetUtil.url + imgUrl1);
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

    @OnClick(R.id.bt_tijiao)
    public void onClick() {
        initView();
        initData();

    }

    @OnClick(R.id.iv_jiahao1)
    public void onClick1() {
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
    //显示图片，上传服务器
    public void showImage(Bitmap bitmap) {
        if(uploadIndex==0){
            ivJiahao1.setImageBitmap(bitmap);//iv显示图片
            saveImage(bitmap);//保存文件
            uploadImage();//上传服务器
        }else if(uploadIndex==1) {
            ivJiahao2.setImageBitmap(bitmap);
            saveImage(bitmap);//保存文件
            uploadImage1();//上传服务器
        }
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
     public void uploadImage1() {
        RequestParams requestParams = new RequestParams(NetUtil.url + "uploadsecondjiahaoservlet");
        requestParams.addBodyParameter("phoneNum", phoneNum1);
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                initData2();
                Log.i("ModifyPersonInfo", "onSuccess: ");
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
    //上传图片
    public void uploadImage(){
        RequestParams requestParams = new RequestParams(NetUtil.url + "uploadjiahaoservlet");
        requestParams.addBodyParameter("phoneNum", phoneNum1);
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ModifyPersonInfo", "onSuccess: ");
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

    @OnClick(R.id.iv_jiahao2)
    public void onClick2() {
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
    }
}
