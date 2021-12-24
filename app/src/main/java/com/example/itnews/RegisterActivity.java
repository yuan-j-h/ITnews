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

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName,registerPassword,registerPasswordAgain,registerEmail,registerEmailIdentify;
    private Button registerButton,registerLast;
    private String registerNameString,registerPasswordString,registerPasswordAgainString,registerEmailString,registerEmailIdentifyString;
    private TextView registerGetIdentifyCode;
    private  SignJson signJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerPasswordAgain = findViewById(R.id.registerPasswordAgain);
        registerEmail = findViewById(R.id.registerEmail);
        registerEmailIdentify = findViewById(R.id.registerEmailIdentify);
        registerLast = findViewById(R.id.registerLast);

        registerLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //邮箱验证
        registerGetIdentifyCode = findViewById(R.id.registerGetIdentifyCode);
        registerGetIdentifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEmailString = registerEmail.getText().toString();
                if (registerEmailString.equals("")){
                    Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                            OkHttpClient clientIdentify = new OkHttpClient().newBuilder()
                                    .build();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, "{\n    \"email\": \""+registerEmailString+"\",\n    \"usage\": 1\n}");
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



        //注册
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNameString = registerName.getText().toString();
                registerPasswordString = registerPassword.getText().toString();
                registerPasswordAgainString = registerPasswordAgain.getText().toString();
                registerEmailString = registerEmail.getText().toString();
                registerEmailIdentifyString = registerEmailIdentify.getText().toString();


                Intent intent = new Intent(RegisterActivity.this,SignInActivity.class);
                if (registerNameString.equals("")){
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (registerPasswordString.equals("")||registerPasswordAgainString.equals("")){
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (registerEmailString.equals("")){
                    Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                }else if (registerEmailIdentifyString.equals("")){
                    Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (registerPasswordString.equals(registerPasswordAgainString)) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient().newBuilder()
                                            .build();
                                    MediaType mediaType = MediaType.parse("application/json");
                                    RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \""+registerNameString+"\",\n    \"password\": \""+registerPasswordAgainString+"\",\n    \"email\": \""+registerEmailString+"\",\n    \"verify\": \""+registerEmailIdentifyString+"\"\n}");
                                    Request request = new Request.Builder()
                                            .url("http://39.106.195.109/itnews/api/reglog/all-reg")
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
                                        editor.putString("username",registerNameString);
                                        editor.putString("password",registerPasswordAgainString);
                                        editor.commit();

                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                        Toast.makeText(RegisterActivity.this, signJson.msg, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(RegisterActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                        thread.start();
                    } else {
                        Toast.makeText(RegisterActivity.this, "请两次输入相同密码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}