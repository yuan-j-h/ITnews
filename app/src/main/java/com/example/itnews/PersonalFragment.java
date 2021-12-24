package com.example.itnews;

import android.Manifest;
import android.app.AlertDialog;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.longsh.optionframelibrary.OptionBottomDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PersonalFragment extends Fragment {

    private RelativeLayout personalLikeButton,personalStarButton,personalPictureButton,personalNameButton,personalGenderButton,personalIntroduceButton,personalChange,personalNicknameButton,personalSignOut;
    private LinearLayout chooseGender,choosePhoto,personalMain;
    private Button genderLast,male,female,takePhoto,album,photoLast;
    private TextView personalLikeNumber,personalName,personalGender,personalIntroduce,personalNickname,personalStarNumber;
    private String name,introduction,path;
    private Uri imageUri,uri,cropUri;
    private ImageView personalPicture;
    private String tokenString;
    private String username,nickname,info,gender,avatar,like,star,img_url;
    private int like_num,star_num,genderInt,img_id,newsNumber;
    private File filePath,outputImage;
    private AlertDialog alertDialog,alertDialogGender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:if (resultCode==11){
                name = data.getStringExtra("name");
                personalNickname = getActivity().findViewById(R.id.personalNickname);
                personalNickname.setText(name);

            }
                break;
            case 2:if (resultCode==22){
                introduction = data.getStringExtra("introduction");
                personalIntroduce= getActivity().findViewById(R.id.personalIntroduce);
                personalIntroduce.setText(introduction);
            }
                break;
            case 3: {
                try {
                    Bitmap bitmapTemp = BitmapFactory.decodeStream( getActivity().getContentResolver().openInputStream( imageUri ) );
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String name = simpleDateFormat.format(new Date())+".jpg";
                    Log.i("",name);
                    String i = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),bitmapTemp,name,"");
                    imageUri = Uri.parse(i);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                startImageCrop( imageUri );
                break;
            }
            case 4:{
                try {
                    imageUri = data.getData(); //获取系统返回的照片的Uri
                    startImageCrop(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5:{
                if("file".equalsIgnoreCase(cropUri.getScheme())) {
                    path = cropUri.getPath();

                } else if("content".equalsIgnoreCase(cropUri.getScheme())) {
                    String [] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().managedQuery(cropUri,proj,null,null,null);
                    int column = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column);
                }


//                    // 通过图片URI拿到剪切图片
//                    bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageUri ) );
//                    //通过FileName拿到图片
                    Bitmap bitmap = BitmapFactory.decodeFile( path.toString() );
//                    //把裁剪后的图片展示出来
                    personalPicture.setImageBitmap( bitmap );
//                    //ImageUtils.Compress( bitmap );


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("text/plain");
                            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                    .addFormDataPart("img",path.toString(),
                                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                                    new File(path.toString())))
                                    .addFormDataPart("type", "1")
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
                            img_id = photoJson.getData().getImg_id();
                            img_url = photoJson.getData().getImg_url();

                            OkHttpClient client2 = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType2 = MediaType.parse("application/json");
                            RequestBody body2 = RequestBody.create(mediaType2, "{\n    \"img_id\": "+img_id+"\n}");
                            Request request2 = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/self/avatar-upload")
                                    .method("POST", body2)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            Response response2 = client2.newCall(request2).execute();

                        } catch (IOException e) {
                            e.printStackTrace();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "请检查网络状况", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
            case 6:if (resultCode==66){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/self/star-news-ids")
                                    .method("GET", null)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();

                            //JSON解析
                            Gson gson = new Gson();
                            StarJson starJson = gson.fromJson(response.body().string(),StarJson.class);
                            newsNumber = starJson.getData().getNews().size();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
//                star = data.getStringExtra("star");
                star = String.valueOf(newsNumber);
                personalStarNumber= getActivity().findViewById(R.id.personalStarNumber);
                personalStarNumber.setText(star);
            }
                break;
            case 7:if (resultCode==77){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/self/like-news-ids")
                                    .method("GET", null)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();
                            //JSON解析
                            Gson gson = new Gson();
                            StarJson starJson = gson.fromJson(response.body().string(),StarJson.class);
                            newsNumber = starJson.getData().getNews().size();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
//                like = data.getStringExtra("like");
                like = String.valueOf(newsNumber);
                personalLikeNumber= getActivity().findViewById(R.id.personalLikeNumber);
                personalLikeNumber.setText(like);
            }
                break;

            default:
                break;

        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        {
            chooseGender = getActivity().findViewById(R.id.chooseGender);
            genderLast = getActivity().findViewById(R.id.genderLast);
            male = getActivity().findViewById(R.id.male);
            female = getActivity().findViewById(R.id.female);
            takePhoto = getActivity().findViewById(R.id.takePhoto);
            album = getActivity().findViewById(R.id.album);
            photoLast = getActivity().findViewById(R.id.photoLast);
            choosePhoto = getActivity().findViewById(R.id.choosePhoto);
            personalGender = getActivity().findViewById(R.id.personalGender);
            personalMain = getActivity().findViewById(R.id.personalMain);
            personalPicture = getActivity().findViewById(R.id.personalPicture);
            personalName = getActivity().findViewById(R.id.personalName);
            personalNickname = getActivity().findViewById(R.id.personalNickname);
            personalIntroduce = getActivity().findViewById(R.id.personalIntroduce);
            personalStarNumber = getActivity().findViewById(R.id.personalStarNumber);
            personalLikeNumber = getActivity().findViewById(R.id.personalLikeNumber);

            personalPictureButton = getActivity().findViewById(R.id.personalPictureButton);
            personalNameButton = getActivity().findViewById(R.id.personalNameButton);
            personalNicknameButton = getActivity().findViewById(R.id.personalNicknameButton);
            personalGenderButton = getActivity().findViewById(R.id.personalGenderButton);
            personalIntroduceButton = getActivity().findViewById(R.id.personalIntroduceButton);
            personalChange = getActivity().findViewById(R.id.personalChange);
            personalSignOut = getActivity().findViewById(R.id.personalSignOut);
            personalStarButton = getActivity().findViewById(R.id.personalStarButton);
            personalLikeButton = getActivity().findViewById(R.id.personalLikeButton);

            //get
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
            tokenString = sharedPreferences.getString("token", String.valueOf(false));
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        Request request = new Request.Builder()
                                .url("http://39.106.195.109/itnews/api/self/info")
                                .method("GET", null)
                                .addHeader("Authorization", tokenString)
                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                .build();

                        Response response = client.newCall(request).execute();
                        Gson gson = new Gson();
                        PersonalJson personalJson = gson.fromJson(response.body().string(), PersonalJson.class);
                        username = personalJson.getData().getUsername();
                        nickname = personalJson.getData().getNickname();
                        info = personalJson.getData().getInfo();
                        gender = personalJson.getData().getGender();
                        avatar = personalJson.getData().getAvatar();
                        like_num = personalJson.getData().getLike_num();
                        star_num = personalJson.getData().getStar_num();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(getContext()).load(avatar).into(personalPicture);
                                personalName.setText(username);
                                personalNickname.setText(nickname);
                                personalGender.setText(gender);
                                personalIntroduce.setText(info);
                                personalStarNumber.setText(String.valueOf(star_num));
                                personalLikeNumber.setText(String.valueOf(like_num));
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "请检查网络状况", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //跳转
            personalNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "用户名好像不能改？？？", Toast.LENGTH_SHORT).show();
                }
            });
            personalNicknameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentName = new Intent(getActivity(), ChangeNameActivity.class);
                    startActivityForResult(intentName, 1);
                }
            });

            personalIntroduceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentIntroduction = new Intent(getActivity(), ChangeIntroductionActivity.class);
                    startActivityForResult(intentIntroduction, 2);
                }
            });
            personalChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentChange = new Intent(getActivity(), ChangePasswordActivity.class);
                    startActivity(intentChange);
                }
            });
            personalSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", null);
                    editor.putBoolean("isSign", false);
                    editor.commit();
                    Intent intentOut = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intentOut);
                    getActivity().finish();
                }
            });
            personalStarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentStar = new Intent(getActivity(), StarActivity.class);
                    startActivityForResult(intentStar, 6);
                }
            });
            personalLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentLike = new Intent(getActivity(), LikeActivity.class);
                    startActivityForResult(intentLike, 7);
                }
            });


            //gender
            personalGenderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogOutGender();
//                    final TranslateAnimation animation = new TranslateAnimation(
//                            TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
//                            TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0);
//                    animation.setDuration(50);
//                    chooseGender.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            chooseGender.setVisibility(View.VISIBLE);
//                            chooseGender.startAnimation(animation);
//                            genderLast.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    chooseGender.setVisibility(View.GONE);
//                                }
//                            });
//                            male.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    personalGender.setText("男");
//                                    gender = "男";
//                                    chooseGender.setVisibility(View.GONE);
//                                    if (gender.equals("女")) {
//                                        genderInt = 0;
//                                    } else {
//                                        genderInt = 1;
//                                    }
//                                    Thread thread1 = new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                //post
//                                                OkHttpClient client = new OkHttpClient().newBuilder()
//                                                        .build();
//                                                MediaType mediaType = MediaType.parse("application/json");
//                                                RequestBody body = RequestBody.create(mediaType, "{\n    \"info\": \"" + info + "\",\n    \"nickname\": \"" + nickname + "\",\n    \"gender\": " + genderInt + "\n}");
//                                                Request request = new Request.Builder()
//                                                        .url("http://39.106.195.109/itnews/api/self/info-refresh")
//                                                        .method("POST", body)
//                                                        .addHeader("Authorization", tokenString)
//                                                        .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
//                                                        .addHeader("Content-Type", "application/json")
//                                                        .build();
//
//                                                Response response = client.newCall(request).execute();
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    });
//                                    thread1.start();
//                                }
//                            });
//                            female.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    personalGender.setText("女");
//                                    gender = "女";
//                                    chooseGender.setVisibility(View.GONE);
//                                    if (gender.equals("女")) {
//                                        genderInt = 0;
//                                    } else {
//                                        genderInt = 1;
//                                    }
//                                    Thread thread2 = new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//                                                //post
//                                                OkHttpClient client = new OkHttpClient().newBuilder()
//                                                        .build();
//                                                MediaType mediaType = MediaType.parse("application/json");
//                                                RequestBody body = RequestBody.create(mediaType, "{\n    \"info\": \"" + info + "\",\n    \"nickname\": \"" + nickname + "\",\n    \"gender\": " + genderInt + "\n}");
//                                                Request request = new Request.Builder()
//                                                        .url("http://39.106.195.109/itnews/api/self/info-refresh")
//                                                        .method("POST", body)
//                                                        .addHeader("Authorization", tokenString)
//                                                        .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
//                                                        .addHeader("Content-Type", "application/json")
//                                                        .build();
//
//                                                Response response = client.newCall(request).execute();
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    });
//                                    thread2.start();
//                                }
//                            });
//                            personalMain.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    chooseGender.setVisibility(View.GONE);
//                                }
//                            });
//
//                        }
//                    }, 500);
                }
            });


        }

        //avater
        personalPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogOut();
//                final TranslateAnimation animation = new TranslateAnimation(
//                        TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,0,
//                        TranslateAnimation.RELATIVE_TO_SELF,1,TranslateAnimation.RELATIVE_TO_SELF,0);
//                animation.setDuration(50);
//                //拍照
//                choosePhoto.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        choosePhoto.setVisibility(View.VISIBLE);
//                        choosePhoto.startAnimation(animation);
//                        photoLast.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                choosePhoto.setVisibility(View.GONE);
//
//                            }
//                        });
//                        takePhoto.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                outputImage = new File(getActivity().getExternalCacheDir(),"image.jpg");
//                                try {
//                                    if (outputImage.exists()){
//                                        outputImage.delete();
//                                    }
//                                    outputImage.createNewFile();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                                if(Build.VERSION.SDK_INT>=24){
//                                    imageUri= FileProvider.getUriForFile(getActivity(),
//                                            "com.example.cameraalbumtest.fileprovider",outputImage);
//                                }else{
//                                    imageUri= Uri.fromFile(outputImage);
//                                }
//                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
//                                startActivityForResult(intent,3);
//                                choosePhoto.setVisibility(View.GONE);
//
////                                Intent intent_photo = new Intent( "android.media.action.IMAGE_CAPTURE" );
////                                imageUri = ImageUtils.getImageUri( getActivity());
////                                intent_photo.putExtra( MediaStore.EXTRA_OUTPUT, imageUri );
////                                startActivityForResult( intent_photo, 3 );
//
//
//                            }
//                        });
//                        //相册
//                        album.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                if (Build.VERSION.SDK_INT >= 23) {
//                                    int REQUEST_CODE_CONTACT = 101;
//                                    String[] permissions = {
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                                    //验证是否许可权限
//                                    for (String str : permissions) {
//                                        if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                                            //申请权限
//                                            getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                                            return;
//                                        } else {
//                                            //这里就是权限打开之后自己要操作的逻辑
//                                        Intent intent_album = new Intent( Intent.ACTION_PICK, null);
//                                        intent_album.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//                                        startActivityForResult( intent_album, 4 );
//
//                                        }
//                                    }
//                                }
//                                choosePhoto.setVisibility(View.GONE);
//
//                            }
//                        });
//                        personalMain.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                choosePhoto.setVisibility(View.GONE);
//                            }
//                        });
//                    }
//                },500);
            }
        });




    }

    //拍照相册
    private void alertDialogOut (){
        final String[] item = new String[]{"拍照","从相册选择","取消"};
        alertDialog = new android.app.AlertDialog.Builder(getContext())
                .setSingleChoiceItems(item, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                outputImage = new File(getActivity().getExternalCacheDir(),"image.jpg");
                                try {
                                    if (outputImage.exists()){
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if(Build.VERSION.SDK_INT>=24){
                                    imageUri= FileProvider.getUriForFile(getActivity(),
                                            "com.example.cameraalbumtest.fileprovider",outputImage);
                                }else{
                                    imageUri= Uri.fromFile(outputImage);
                                }
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                                startActivityForResult(intent,3);
                                alertDialog.dismiss();
                                break;

                            case 1:
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int REQUEST_CODE_CONTACT = 101;
                                    String[] permissions = {
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                    //验证是否许可权限
                                    for (String str : permissions) {
                                        if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                                            //申请权限
                                            getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                                            return;
                                        } else {
                                            //这里就是权限打开之后自己要操作的逻辑
                                            Intent intent_album = new Intent( Intent.ACTION_PICK, null);
                                            intent_album.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                                            startActivityForResult( intent_album, 4 );

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

    //选择性别
    private void alertDialogOutGender (){
        final String[] item = new String[]{"男","女","取消"};
        alertDialogGender = new android.app.AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(item, 2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                personalGender.setText("男");
                                gender = "男";
                                alertDialogGender.dismiss();
                                if (gender.equals("女")) {
                                    genderInt = 0;
                                } else {
                                    genderInt = 1;
                                }
                                Thread thread1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            //post
                                            OkHttpClient client = new OkHttpClient().newBuilder()
                                                    .build();
                                            MediaType mediaType = MediaType.parse("application/json");
                                            RequestBody body = RequestBody.create(mediaType, "{\n    \"info\": \"" + info + "\",\n    \"nickname\": \"" + nickname + "\",\n    \"gender\": " + genderInt + "\n}");
                                            Request request = new Request.Builder()
                                                    .url("http://39.106.195.109/itnews/api/self/info-refresh")
                                                    .method("POST", body)
                                                    .addHeader("Authorization", tokenString)
                                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                                    .addHeader("Content-Type", "application/json")
                                                    .build();

                                            Response response = client.newCall(request).execute();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(), "请检查网络状况", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                                thread1.start();
                                alertDialogGender.dismiss();

                                break;
                            case 1:
                                personalGender.setText("女");
                                gender = "女";
                                alertDialogGender.dismiss();
                                if (gender.equals("女")) {
                                    genderInt = 0;
                                } else {
                                    genderInt = 1;
                                }
                                Thread thread2 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            //post
                                            OkHttpClient client = new OkHttpClient().newBuilder()
                                                    .build();
                                            MediaType mediaType = MediaType.parse("application/json");
                                            RequestBody body = RequestBody.create(mediaType, "{\n    \"info\": \"" + info + "\",\n    \"nickname\": \"" + nickname + "\",\n    \"gender\": " + genderInt + "\n}");
                                            Request request = new Request.Builder()
                                                    .url("http://39.106.195.109/itnews/api/self/info-refresh")
                                                    .method("POST", body)
                                                    .addHeader("Authorization", tokenString)
                                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                                    .addHeader("Content-Type", "application/json")
                                                    .build();

                                            Response response = client.newCall(request).execute();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(), "请检查网络状况", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                                thread2.start();
                                alertDialogGender.dismiss();
                                break;
                            case 2:
                                alertDialogGender.dismiss();


                        }
                        alertDialogGender.dismiss();
                    }
                })
                .create();
        alertDialogGender.show();
    }

    //剪切图片
    private void startImageCrop(Uri uri) {
        if(uri == null){
            return ;
        }
        File cropImage = new File(Environment.getExternalStorageDirectory(),"crop_image.png");
//        String path = cropImage.getAbsolutePath();
        try {
            if (cropImage.exists()){
                cropImage.delete();
            }
            cropImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cropUri = Uri.fromFile(cropImage);
        Intent intent = new Intent( "com.android.camera.action.CROP" );
        intent.setDataAndType( imageUri, "image/*" );//设置Uri及类型
        //uri权限，如果不加的话，   会产生无法加载图片的问题
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra( "crop", "true" );
        intent.putExtra( "aspectX", 1 );//X方向上的比例
        intent.putExtra( "aspectY", 1 );//Y方向上的比例
        intent.putExtra( "outputX", 150 );//裁剪区的X方向宽
        intent.putExtra( "outputY", 150 );//裁剪区的Y方向宽
        intent.putExtra( "scale", true );//是否保留比例
        intent.putExtra(MediaStore.EXTRA_OUTPUT,cropUri);
        intent.putExtra( "outputFormat", Bitmap.CompressFormat.PNG.toString() );
        intent.putExtra( "return-data", false );//是否将数据保留在Bitmap中返回dataParcelable相应的Bitmap数据，防止造成OOM，置位false
        intent.putExtra("noFaceDetection",true);
        //判断文件是否存在
        //File saveToFile = ImageUtils.getTempFile();
//        if (!saveToFile.getParentFile().exists()) {
//            saveToFile.getParentFile().mkdirs();
//        }
        startActivityForResult( intent, 5 );
    }





}



