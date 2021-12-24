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

public class ChangePasswordActivity extends AppCompatActivity {
    private Button changePasswordLast,changePasswordSave;
    private EditText changePasswordEmail,changePasswordEmailIdentify,changePasswordOldPassword,changePasswordNewPassword,changePasswordPasswordAgain;
    private TextView changePasswordGetIdentifyCode;
    private String password,changePasswordEmailString,changePasswordEmailIdentifyString,changePasswordOldPasswordString,changePasswordNewPasswordString,changePasswordPasswordAgainString;
    private SignJson signJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changePasswordLast = findViewById(R.id.changePasswordLast);
        changePasswordSave = findViewById(R.id.changePasswordSave);
        changePasswordEmail = findViewById(R.id.changePasswordEmail);
        changePasswordEmailIdentify = findViewById(R.id.changePasswordEmailIdentify);
        changePasswordOldPassword = findViewById(R.id.changePasswordOldPassword);
        changePasswordNewPassword = findViewById(R.id.changePasswordNewPassword);
        changePasswordPasswordAgain = findViewById(R.id.changePasswordPasswordAgain);
        changePasswordGetIdentifyCode = findViewById(R.id.changePasswordGetIdentifyCode);

        changePasswordLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        password = sharedPreferences.getString("password", String.valueOf(false));

        //邮箱验证
        changePasswordGetIdentifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordEmailString = changePasswordEmail.getText().toString();
                if (changePasswordEmailString.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                OkHttpClient clientIdentify = new OkHttpClient().newBuilder()
                                        .build();
                                MediaType mediaType = MediaType.parse("application/json");
                                RequestBody body = RequestBody.create(mediaType, "{\n    \"email\": \""+changePasswordEmailString+"\",\n    \"usage\": 2\n}");
                                Request request = new Request.Builder()
                                        .url("http://39.106.195.109/itnews/api/reglog/code-reg")
                                        .method("POST", body)
                                        .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                        .addHeader("Content-Type", "application/json")
                                        .build();

                                Response response = clientIdentify.newCall(request).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ChangePasswordActivity.this, "请检查网络状况", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                    thread.start();
                }
            }
        });


//        private Button changePasswordLast,changePasswordSave;
//        private EditText changePasswordEmail,changePasswordEmailIdentify,changePasswordOldPassword,changePasswordNewPassword,changePasswordPasswordAgain;
//        private TextView changePasswordGetIdentifyCode;
//        private String password,changePasswordEmailString,changePasswordEmailIdentifyString,changePasswordOldPasswordString,changePasswordNewPasswordString,changePasswordPasswordAgainString;


        //修改密码
        changePasswordSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordEmailString = changePasswordEmail.getText().toString();
                changePasswordEmailIdentifyString = changePasswordEmailIdentify.getText().toString();
                changePasswordOldPasswordString = changePasswordOldPassword.getText().toString();
                changePasswordNewPasswordString = changePasswordNewPassword.getText().toString();
                changePasswordPasswordAgainString = changePasswordPasswordAgain.getText().toString();
                if (changePasswordEmailString.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                } else if (changePasswordEmailIdentifyString.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else if (changePasswordOldPasswordString.equals("") ||changePasswordNewPasswordString.equals("") || changePasswordPasswordAgainString.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (changePasswordOldPasswordString.equals(password)) {
                        if (changePasswordNewPasswordString.equals(changePasswordPasswordAgainString)) {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        OkHttpClient client = new OkHttpClient().newBuilder()
                                                .build();
                                        MediaType mediaType = MediaType.parse("application/json");
                                        RequestBody body = RequestBody.create(mediaType, "{\n    \"email\": \"" + changePasswordEmailString + "\",\n    \"verify\": \"" + changePasswordEmailIdentifyString + "\",\n    \"password\": \"" + changePasswordNewPasswordString + "\"\n}");
                                        Request request = new Request.Builder()
                                                .url("http://39.106.195.109/itnews/api/reglog/pwd-recall")
                                                .method("POST", body)
                                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                                .addHeader("Content-Type", "application/json")
                                                .build();
                                        Response response = client.newCall(request).execute();
                                        Gson gson = new Gson();
                                        signJson = gson.fromJson(response.body().string().replace("\"{", "{").replace("}\"", "}"), SignJson.class);

                                        if (signJson.msg.equals("一切正常")) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("token", signJson.data.token);
                                            editor.putString("password", changePasswordNewPasswordString);
                                            editor.commit();

                                            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(ChangePasswordActivity.this, signJson.msg, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ChangePasswordActivity.this, "请检查网络状况", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                            thread.start();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "请两次输入相同密码", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ChangePasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}