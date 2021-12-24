package com.example.itnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthorActivity extends AppCompatActivity {

    private int author_id,is_followed;
    private ImageView authorAvatar;
    private TextView authorNickname,authorGender,authorInfo;
    private Button authorLike,last;
    private String tokenString,nickname,avatar,info,gender;
    private Boolean changeLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        authorAvatar = findViewById(R.id.authorAvatar);
        authorNickname = findViewById(R.id.authorNickname);
        authorGender = findViewById(R.id.authorGender);
        authorInfo = findViewById(R.id.authorInfo);
        authorLike = findViewById(R.id.authorLike);
        last = findViewById(R.id.last);
        changeLike=false;

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        author_id = intent.getIntExtra("author_id",0);
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://39.106.195.109/itnews/api/users/"+author_id+"/info-full")
                            .method("GET", null)
                            .addHeader("Authorization", tokenString)
                            .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                            .build();
                    Response response = client.newCall(request).execute();
                    Gson gson = new Gson();
                    AuthorJson authorJson = gson.fromJson(response.body().string(),AuthorJson.class);
                    nickname = authorJson.getData().getNickname();
                    gender = authorJson.getData().getGender();
                    info = authorJson.getData().getInfo();
                    is_followed = authorJson.getData().getIs_followed();
                    avatar = authorJson.getData().getAvatar();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            authorNickname.setText(nickname);
                            authorGender.setText(gender);
                            authorInfo.setText(info);
                            if (is_followed==1){
                            authorLike.setText("已关注");
                            }
                            else if (is_followed==0){
                                authorLike.setText("关注作者");
                            }
                            Glide.with(AuthorActivity.this).load(avatar).into(authorAvatar);

                        }
                    });

                }catch (IOException e) {
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

        authorLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeLike.equals(true)){
                    changeLike=false;
                }
                else if (changeLike.equals(false)){
                    changeLike=true;
                }
                if (authorLike.getText().equals("已关注")){
                    authorLike.setText("关注作者");
                }
                else if (authorLike.getText().equals("关注作者")){
                    authorLike.setText("已关注");
                }
            }
        });

    }

    protected void onDestroy () {
        super.onDestroy();
        if (changeLike.equals(true)){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create(mediaType, "");
                        Request request = new Request.Builder()
                                .url("http://39.106.195.109/itnews/api/users/"+author_id+"/operator/follow")
                                .method("POST", body)
                                .addHeader("Authorization", tokenString)
                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                .build();
                        Response response = client.newCall(request).execute();

                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
    }
}