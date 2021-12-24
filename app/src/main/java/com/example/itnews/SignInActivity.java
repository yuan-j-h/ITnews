package com.example.itnews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class SignInActivity extends AppCompatActivity {
    private EditText signInAccount,signInPassword;
    private Button signInButton;
    private TextView signInRegister,signInForget;
    private String signInAccountString,signInPasswordString;
    private SignJson signJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //跳转注册账号
        signInRegister = findViewById(R.id.signInRegister);
        signInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(register);
            }
        });
        //跳转忘记密码
        signInForget = findViewById(R.id.signInForget);
        signInForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget = new Intent(SignInActivity.this,ForgetActivity.class);
                startActivity(forget);
            }
        });
        //登录
        signInAccount = findViewById(R.id.signInAccount);
        signInPassword = findViewById(R.id.signInPassword);
        signInPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAccountString = signInAccount.getText().toString();
                signInPasswordString = signInPassword.getText().toString();
                if (signInAccountString.equals("")){
                    Toast.makeText(SignInActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (signInPasswordString.equals("")){
                    Toast.makeText(SignInActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \""+signInAccountString+"\",\n    \"password\": \""+signInPasswordString+"\"\n}");
                        Request request = new Request.Builder()
                                .url("http://39.106.195.109/itnews/api/reglog/all-log")
                                .method("POST", body)
                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                .addHeader("Content-Type", "application/json")
                                .build();
                        Response response = client.newCall(request).execute();

                        Gson gson = new Gson();
                        signJson = gson.fromJson(response.body().string().replace("\"{","{").replace("}\"","}"),SignJson.class);
                        if (signJson.msg.equals("用户名或密码错误")||signJson.data.token.equals("")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignInActivity.this, signJson.msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if (signJson.msg.equals("一切正常")){
                            SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token",signJson.data.token);
                            editor.putBoolean("isSign",true);
                            editor.putString("username",signInAccountString);
                            editor.putString("password",signInPasswordString);
                            editor.commit();

                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignInActivity.this, signJson.msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignInActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                        }
                    });
                    thread.start();
                }
            }
        });

    }
}