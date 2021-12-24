package com.example.itnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StarActivity extends AppCompatActivity {

    private NewsAdapter starAdapter;
    private RecyclerView recyclerView;
    private List<Map<String,Object>> list =new ArrayList<>();
    private String tokenString;
    private int newsNumber;
    private Button starLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        list.clear();

        starLast = findViewById(R.id.starLast);

        recyclerView = findViewById(R.id.starRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StarActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        starAdapter = new NewsAdapter(StarActivity.this, list);
        recyclerView.setAdapter(starAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));



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


                    for (int i=0;i<newsNumber;i++){
                        Map<String,Object> map=new HashMap<>();
                        if (starJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                            map.put("news_pics_set",starJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                        else {
                            map.put("news_pics_set",null);
                        }
                        map.put("id",starJson.getData().getNews().get(i).getId());
                        map.put("title",starJson.getData().getNews().get(i).getTitle());
                        map.put("newsAuthor",starJson.getData().getNews().get(i).getAuthor().getNickname());
                        list.add(map);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            starAdapter.setData(list);
                            starAdapter.notifyDataSetChanged();

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        starLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Intent intent = new Intent(StarActivity.this,PersonalFragment.class);
                intent.putExtra("star",newsNumber);
                setResult(66,intent);
                finish();
            }
        });

        RefreshLayout refreshLayout = findViewById(R.id.starRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);
                list.clear();

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


                            for (int i=0;i<newsNumber;i++){
                                Map<String,Object> map=new HashMap<>();
                                if (starJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                                    map.put("news_pics_set",starJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                                else {
                                    map.put("news_pics_set",null);
                                }
                                map.put("id",starJson.getData().getNews().get(i).getId());
                                map.put("title",starJson.getData().getNews().get(i).getTitle());
                                map.put("newsAuthor",starJson.getData().getNews().get(i).getAuthor().getNickname());
                                list.add(map);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    starAdapter.setData(list);
                                    starAdapter.notifyDataSetChanged();

                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

            }
        });



    }
}