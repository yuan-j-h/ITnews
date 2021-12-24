package com.example.itnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {
    private String tokenString,userName,password;
    private Boolean sign;
    private SignJson signJson;

    @SuppressLint("HandlerLeak")
    Handler mHandler=new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
            tokenString = sharedPreferences.getString("token", String.valueOf(false));
            userName = sharedPreferences.getString("username", String.valueOf(false));
            password = sharedPreferences.getString("password", String.valueOf(false));
            sign = sharedPreferences.getBoolean("isSign",false);

            if (tokenString.equals(null)||sign.equals(false)){
                Intent intent = new Intent(StartActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }else {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \""+userName+"\",\n    \"password\": \""+password+"\"\n}");
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/reglog/all-log")
                                    .method("POST", body)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            Response response = client.newCall(request).execute();
                            Gson gson = new Gson();
                            signJson = gson.fromJson(response.body().string().replace("\"{","{").replace("}\"","}"),SignJson.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token",signJson.data.token);
                            editor.putBoolean("isSign",true);
                            editor.commit();

                            Intent intent = new Intent(StartActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Intent intent = new Intent(StartActivity.this,DisconnectionActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
                thread.start();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mHandler.sendEmptyMessageDelayed(1,2000);//2秒后发送消息

    }
}