package com.example.itnews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OutPutActivity extends AppCompatActivity {

    private Button outPutLast,outPutSave,outPutImg;
    private EditText outPutTitle,outPutContent;
    private GridView outPutGridView;
    private String title,content,tokenString,imgId;
    private android.app.AlertDialog alertDialog;
    private File outPutPhoto;
    private Uri outPutUri;
    private String [] pics=new String[10];
    private String [] img_url=new String[10];
    private int [] img_id=new int[10];
    private String [] path = new String[10];
    private int pathNum = 0;
    private Bitmap bitmap;
    private List<HashMap<String,Object>> meumList;
    private OutPutAdapter outPutAdapter;
    private HashMap<String,Object> map = new HashMap<String, Object>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap bitmapTemp = BitmapFactory.decodeStream( getContentResolver().openInputStream( outPutUri ) );
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String name = simpleDateFormat.format(new Date())+".jpg";
                            Log.i("",name);
                            String i = MediaStore.Images.Media.insertImage(getContentResolver(),bitmapTemp,name,"");
                            outPutUri = Uri.parse(i);

                            if("file".equalsIgnoreCase(outPutUri.getScheme())) {
                                path[pathNum] = outPutUri.getPath();
                            } else if("content".equalsIgnoreCase(outPutUri.getScheme())) {
                                String[] proj = {MediaStore.Images.Media.DATA};
                                Cursor cursor = managedQuery(outPutUri, proj, null, null, null);
                                int column = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                                cursor.moveToFirst();
                                path[pathNum] = cursor.getString(column);
                                String p = path[pathNum];
                            }


                            bitmap = BitmapFactory.decodeFile( path[pathNum].toString() );

                            map.put("Img",path[pathNum]);
                            meumList.add(map);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    outPutAdapter.setData(meumList);
                                    outPutAdapter.notifyDataSetChanged();
                                }
                            });

                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("img",path[pathNum].toString(),
                                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                                    new File(path[pathNum].toString())))
                                    .addFormDataPart("type", "2")
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/img-upload")
                                    .method("POST", body)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();
                            Gson gson = new Gson();
                            PhotoJson photoJson = gson.fromJson(response.body().string(),PhotoJson.class);
                            img_id[pathNum] = photoJson.getData().getImg_id();
                            img_url[pathNum] = photoJson.getData().getImg_url();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




                pathNum=pathNum+1;

                break;

            case 2:
                outPutUri = data.getData();

                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if("file".equalsIgnoreCase(outPutUri.getScheme())) {
                                path[pathNum] = outPutUri.getPath();
                            } else if("content".equalsIgnoreCase(outPutUri.getScheme())) {
                                String[] proj = {MediaStore.Images.Media.DATA};
                                Cursor cursor = managedQuery(outPutUri, proj, null, null, null);
                                int column = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                                cursor.moveToFirst();
                                path[pathNum] = cursor.getString(column);
                            }
                            bitmap = BitmapFactory.decodeFile( path[pathNum].toString() );



                            map.put("Img",path[pathNum]);
                            meumList.add(map);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                outPutAdapter.setData(meumList);
                                outPutAdapter.notifyDataSetChanged();
                                }
                            });

                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("img",path[pathNum].toString(),
                                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                                    new File(path[pathNum].toString())))
                                    .addFormDataPart("type", "2")
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/img-upload")
                                    .method("POST", body)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();
                            Gson gson = new Gson();
                            PhotoJson photoJson = gson.fromJson(response.body().string(),PhotoJson.class);
                            img_id[pathNum] = photoJson.getData().getImg_id();
                            img_url[pathNum] = photoJson.getData().getImg_url();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                pathNum=pathNum+1;


                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_put);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));

        outPutLast = findViewById(R.id.outPutLast);
        outPutSave = findViewById(R.id.outPutSave);
        outPutImg = findViewById(R.id.outPutImg);
        outPutTitle = findViewById(R.id.outPutTitle);
        outPutContent = findViewById(R.id.outPutContent);
        outPutGridView = findViewById(R.id.outPutGridView);
        meumList = new ArrayList<HashMap<String, Object>>();

        outPutAdapter = new OutPutAdapter(OutPutActivity.this,meumList);
        // 应用适配器
        outPutGridView.setAdapter(outPutAdapter);


        outPutLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        outPutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogOut();
            }
        });

        outPutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = outPutTitle.getText().toString();
                content = outPutContent.getText().toString();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            imgId=String.valueOf(img_id[0]);
                            for (int i=1;i<pathNum;i++){
                                imgId=imgId+","+String.valueOf(img_id[i]);
                            }

                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                            RequestBody body = RequestBody.create(mediaType, "title="+title+"&content="+content+"&tag=2&img_ids="+imgId);
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/news/release")
                                    .method("POST", body)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();
                            Gson gson = new Gson();
//                            String r = response.body().string();
//                            OutPutResponseJson outPutResponseJson = gson.fromJson(response.body().string(),OutPutResponseJson.class);
//                            String msg = outPutResponseJson.getMsg();
//
//                            Log.i("???",msg);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                finish();

            }
        });


    }
    private void alertDialogOut (){
        final String[] item = new String[]{"拍照","从相册选择","取消"};
        alertDialog = new android.app.AlertDialog.Builder(this)
                .setSingleChoiceItems(item, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                outPutPhoto=new File(getExternalCacheDir(),"outPutImage.jpg");
                                try {
                                    if (outPutPhoto.exists()){
                                        outPutPhoto.delete();
                                    }
                                    outPutPhoto.createNewFile();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(Build.VERSION.SDK_INT>=24){
                                    outPutUri= FileProvider.getUriForFile(OutPutActivity.this,
                                            "com.example.cameraalbumtest.fileprovider",outPutPhoto);
                                }else{
                                    outPutUri= Uri.fromFile(outPutPhoto);
                                }
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,outPutUri);
                                startActivityForResult(intent,1);
                                alertDialog.dismiss();
                                break;
                            case 1:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int REQUEST_CODE_CONTACT = 101;
                                    String[] permissions = {
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    //验证是否许可权限
                                    for (String str : permissions) {
                                        if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                                            //申请权限
                                            requestPermissions(permissions, REQUEST_CODE_CONTACT);
                                            return;
                                        } else {
                                            //这里就是权限打开之后自己要操作的逻辑
                                            Intent intent_album = new Intent(Intent.ACTION_PICK, null);
                                            intent_album.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                            startActivityForResult(intent_album, 2);

                                        }
                                    }
                                }
                                alertDialog.dismiss();
                                break;
                            case 2:
                                alertDialog.dismiss();

                        }
                        alertDialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }






}

