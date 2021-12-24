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

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity {
    private Button forgetLast,forgetFind;
    private EditText forgetEmail,forgetEmailIdentify,forgetNewPassword,forgetPasswordAgain;
    private TextView forgetGetIdentifyCode;
    private String forgetEmailString,forgetEmailIdentifyString,forgetNewPasswordString,forgetPasswordAgainString;
    private SignJson signJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        forgetLast=findViewById(R.id.forgetLast);
        forgetFind=findViewById(R.id.forgetFind);
        forgetEmail=findViewById(R.id.forgetEmail);
        forgetEmailIdentify=findViewById(R.id.forgetEmailIdentify);
        forgetNewPassword=findViewById(R.id.forgetNewPassword);
        forgetPasswordAgain=findViewById(R.id.forgetPasswordAgain);
        forgetGetIdentifyCode=findViewById(R.id.forgetGetIdentifyCode);

        forgetLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //email
        forgetGetIdentifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetEmailString = forgetEmail.getText().toString();
                if (forgetEmailString.equals("")){
                    Toast.makeText(ForgetActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                OkHttpClient clientIdentify = new OkHttpClient().newBuilder()
                                        .build();
                                MediaType mediaType = MediaType.parse("application/json");
                                RequestBody body = RequestBody.create(mediaType, "{\n    \"email\": \""+forgetEmailString+"\",\n    \"usage\": 2\n}");
                                Request request = new Request.Builder()
                                        .url("http://39.106.195.109/itnews/api/reglog/code-reg")
                                        .method("POST", body)
                                        .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                        .addHeader("Content-Type", "application/json")
                                        .build();

                                Response response = clientIdentify.newCall(request).execute();




                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
            }
        });

        //
        forgetFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    forgetEmailString = forgetEmail.getText().toString();
                    forgetEmailIdentifyString = forgetEmailIdentify.getText().toString();
                    forgetNewPasswordString = forgetNewPassword.getText().toString();
                    forgetPasswordAgainString = forgetPasswordAgain.getText().toString();
                    if (forgetEmailString.equals("")) {
                        Toast.makeText(ForgetActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    } else if (forgetEmailIdentifyString.equals("")) {
                        Toast.makeText(ForgetActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (forgetNewPasswordString.equals("") || forgetPasswordAgainString.equals("")) {
                        Toast.makeText(ForgetActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        if (forgetNewPasswordString.equals(forgetPasswordAgainString)) {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        OkHttpClient client = new OkHttpClient().newBuilder()
                                                .build();
                                        MediaType mediaType = MediaType.parse("application/json");
                                        RequestBody body = RequestBody.create(mediaType, "{\n    \"email\": \"" + forgetEmailString + "\",\n    \"verify\": \"" + forgetEmailIdentifyString + "\",\n    \"password\": \"" + forgetPasswordAgainString + "\"\n}");
                                        Request request = new Request.Builder()
                                                .url("http://39.106.195.109/itnews/api/reglog/pwd-recall")
                                                .method("POST", body)
                                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                                .addHeader("Content-Type", "application/json")
                                                .build();
                                        Response response = client.newCall(request).execute();
                                        Gson gson = new Gson();
                                        signJson = gson.fromJson(response.body().string().replace("\"{","{").replace("}\"","}"),SignJson.class);

                                        if (signJson.msg.equals("一切正常")){
                                            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("token",signJson.data.token);
                                            editor.putBoolean("isSign",true);
                                            editor.putString("password",forgetPasswordAgainString);
                                            editor.commit();

                                            Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                Toast.makeText(ForgetActivity.this, signJson.msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();
                        }
                        else{
                            Toast.makeText(ForgetActivity.this, "请两次输入相同密码", Toast.LENGTH_SHORT).show();
                        }

                    }
            }
        });
    }
}