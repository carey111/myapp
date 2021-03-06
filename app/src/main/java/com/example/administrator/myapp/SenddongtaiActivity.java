package com.example.administrator.myapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myapp.application.MyApplication;
import com.example.administrator.myapp.util.HttpUtils;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SenddongtaiActivity extends AppCompatActivity {

    @InjectView(R.id.tv_zuiai)
    TextView tvZuiai;
    @InjectView(R.id.tv_leixing)
    TextView tvLeixing;
    @InjectView(R.id.rb_shi)
    RadioButton rbShi;
    @InjectView(R.id.rb_fo)
    RadioButton rbFo;
    @InjectView(R.id.rg)
    RadioGroup rg;
    private File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" +
            getPhotoFileName());

    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PHOTO_CLIP = 3;

    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.iv_release_back)
    ImageView ivReleaseBack;
    @InjectView(R.id.iv_release_report)
    ImageView ivReleaseReport;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.iv_release_addimage)
    ImageView ivReleaseAddimage;
    @InjectView(R.id.iv_release_imgs)
    ImageView ivReleaseImgs;
    @InjectView(R.id.iv_release_place)
    ImageView ivReleasePlace;
    @InjectView(R.id.tv_release_place)
    TextView tvReleasePlace;
    @InjectView(R.id.iv_show_pet)
    ImageView ivShowPet;
    @InjectView(R.id.rl_release_dynamic)
    RelativeLayout rlReleaseDynamic;
    @InjectView(R.id.v_zhixian3)
    View vZhixian3;

    boolean flag = true;
    Integer tag1 = -1;
    Integer tag = -1;
    String path = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddongtai);
        ButterKnife.inject(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_shi){
                    tag1=1;
                }else if(checkedId==R.id.rb_fo){
                    tag1=0;
                }
            }
        });
    }


    @OnClick({R.id.iv_release_back, R.id.iv_release_report, R.id.iv_release_addimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_release_back:
                finish();
                break;
            case R.id.iv_release_report:
                uploadDynamic();
                if (tag != 0 && tag != 1 && tag != 2 && tag != 3) {
                    Toast.makeText(SenddongtaiActivity.this, "请选择我的最爱", Toast.LENGTH_SHORT).show();
                }else if(tag1==-1) {
                    Toast.makeText(SenddongtaiActivity.this, "是否为租房需求？", Toast.LENGTH_SHORT).show();
                }else{
                Toast.makeText(SenddongtaiActivity.this, "分享成功,刷新查看", Toast.LENGTH_SHORT).show();
                finish();}
                break;
            case R.id.iv_release_addimage:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择");
                EditText tv = new EditText(SenddongtaiActivity.this);
                builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            getPicFromCamera();
                            flag = true;
                        } else {
                            getPicFromPhoto();
                            flag = false;
                        }
                    }
                });
                builder.show();
                break;
        }
    }

    @OnClick(R.id.tv_zuiai)
    public void onClick1(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择");
        EditText tv = new EditText(SenddongtaiActivity.this);
        builder.setItems(new String[]{"音乐", "运动", "游戏", "休闲"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    tag = 0;
                } else if (which == 1) {
                    tag = 1;
                } else if (which == 2) {
                    tag = 2;
                } else if (which == 3) {
                    tag = 3;
                }
            }
        });
        builder.show();
    }


    //上传动态
    private void uploadDynamic() {

        String release_text = etContent.getText().toString().trim();

        RequestParams params = new RequestParams(HttpUtils.url + "UploadDongtai");

        Toast.makeText(SenddongtaiActivity.this, "xxxuserId", Toast.LENGTH_SHORT).show();
        Log.i("uploadDynamic: ", "uploadDynamic: ");
        try {
            params.addBodyParameter("userId", 1+"");
            params.addBodyParameter("release_text", URLEncoder.encode(release_text, "utf-8"));
            params.addBodyParameter("place", URLEncoder.encode("苏州", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("time", String.valueOf(System.currentTimeMillis()));

        if (flag) {
            params.addBodyParameter("file", file);
        } else {
            params.addBodyParameter("file", new File(path));
        }
        switch (tag) {
            case 0:
                try {
                    params.addBodyParameter("title", URLEncoder.encode("音乐","utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            case 1:
                try {
                    params.addBodyParameter("title", URLEncoder.encode("运动","utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            case 2:
                try {
                    params.addBodyParameter("title", URLEncoder.encode("游戏","utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            case 3:
                try {
                    params.addBodyParameter("title", URLEncoder.encode("休闲","utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        }

        if(tag1==1){
            params.addBodyParameter("type", true+"");
        }else if(tag1==0){
            params.addBodyParameter("type", false+"");
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("成功！！！！！！！！！");
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

    private static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + "_" + UUID.randomUUID() + ".png";
    }

    private void getPicFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        System.out.println("getPicFromCamera===========" + file.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功  固定
                        System.out.println("CAMERA_REQUEST" + file.getAbsolutePath());
                        ivReleaseImgs.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        break;
                    default:
                        break;
                }
                break;
            case PHOTO_REQUEST:
//
                if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
                    Log.e("TAG->onresult", "ActivityResult resultCode error");
                    return;
                }
                Bitmap bm = null;
                //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = getContentResolver();
                //此处的用于判断接收的Activity是不是你想要的那个
                if (requestCode == PHOTO_REQUEST) {
                    try {
                        Uri originalUri = data.getData();        //获得图片的uri
                        bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        //显得到bitmap图片
                        ivReleaseImgs.setImageBitmap(bm);
                        //这里开始的第二部分，获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        //好像是android多媒体数据库的封装接口，具体的看Android文档
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                        //按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        //将光标移至开头 ，这个很重要，不小心很容易引起越界
                        cursor.moveToFirst();
                        //最后根据索引值获取图片路径
                        path = cursor.getString(column_index);
                        System.out.println(path);

                    } catch (IOException e) {
                        Log.e("TAG-->Error", e.toString());
                    }
                }
                break;
            case PHOTO_CLIP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Log.w("test", "data");
                        Bitmap photo = extras.getParcelable("data");
                        saveImageToGallery(getApplication(), photo);//保存bitmap到本地
                        ivReleaseImgs.setImageBitmap(photo);
                    }
                }
                break;
            default:
                break;
        }

    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST);
    }



}

