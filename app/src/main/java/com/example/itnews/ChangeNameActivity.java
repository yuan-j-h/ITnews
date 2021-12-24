package com.example.itnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeNameActivity extends AppCompatActivity {

    private Button changeNameLast,changeNameSave;
    private EditText changeName;
    private String tokenString,nickname,info,gender;
    private int genderInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        changeName = findViewById(R.id.changeName);
        changeNameLast = findViewById(R.id.changeNameLast);
        changeNameSave = findViewById(R.id.changeNameSave);

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));


        changeNameLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeNameSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/self/info")
                                    .method("GET", null)
                                    .addHeader("Authorization",tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();

                            Response response = client.newCall(request).execute();
                            Gson gson = new Gson();
                            PersonalJson personalJson = gson.fromJson(response.body().string(),PersonalJson.class);
                            info = personalJson.getData().getInfo();
                            gender = personalJson.getData().getGender();
                            if (gender.equals("女")){
                                genderInt = 0;
                            }
                            else{
                                genderInt=1;
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeNameActivity.this, "请检查网络状况", Toast.LENGTH_SHORT).show();
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

                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //post
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, "{\n    \"info\": \""+info+"\",\n    \"nickname\": \""+changeName.getText().toString()+"\",\n    \"gender\": "+genderInt+"\n}");
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/self/info-refresh")
                                    .method("POST", body)
                                    .addHeader("Authorization",tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .addHeader("Content-Type", "application/json")
                                    .build();

                            Response response = client.newCall(request).execute();

                            //跳转
                            Intent intent = new Intent(ChangeNameActivity.this,PersonalFragment.class);
                            intent.putExtra("name",changeName.getText().toString());
                            setResult(11,intent);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeNameActivity.this, "请检查网络状况", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                thread2.start();


            }
        });

    }
}