package com.example.itnews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ArticleFragment extends Fragment {

    private Button outPutArticle;
    private String tokenString;
    private RecyclerView articleRecyclerview;
    private List<Map<String,Object>> list =new ArrayList<>();
    private int newsNumber,pageNumber;
    private NewsAdapter news;
    private RefreshLayout articleRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        outPutArticle=getActivity().findViewById(R.id.outPutArticle);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));

        list.clear();

        articleRecyclerview = getActivity().findViewById(R.id.articleRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        articleRecyclerview.setLayoutManager(linearLayoutManager);
        news = new NewsAdapter(getActivity(), list);
        articleRecyclerview.setAdapter(news);

        outPutArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OutPutActivity.class);
                startActivity(intent);
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://39.106.195.109/itnews/api/self/news-ids")
                            .method("GET", null)
                            .addHeader("Authorization", tokenString)
                            .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                            .build();
                    Response response = client.newCall(request).execute();

                    //JSON解析
                    Gson gson = new Gson();
                    MyArticleJson myArticleJson = gson.fromJson(response.body().string(),MyArticleJson.class);
                    newsNumber = myArticleJson.getData().getNews().size();


                    for (int i=0;i<newsNumber;i++){
                        Map<String,Object> map=new HashMap<>();
                        if (myArticleJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                            map.put("news_pics_set",myArticleJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                        else {
                            map.put("news_pics_set",null);
                        }
                        map.put("id",myArticleJson.getData().getNews().get(i).getId());
                        map.put("title",myArticleJson.getData().getNews().get(i).getTitle());
                        map.put("newsAuthor",myArticleJson.getData().getNews().get(i).getAuthor().getNickname());
                        list.add(map);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            news.setData(list);
                            news.notifyDataSetChanged();

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

        articleRefreshLayout = getActivity().findViewById(R.id.articleRefreshLayout);
        articleRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        articleRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
        articleRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);
                list.clear();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/self/news-ids")
                                    .method("GET", null)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();

                            //JSON解析
                            Gson gson = new Gson();
                            MyArticleJson myArticleJson = gson.fromJson(response.body().string(),MyArticleJson.class);
                            newsNumber = myArticleJson.getData().getNews().size();


                            for (int i=0;i<newsNumber;i++){
                                Map<String,Object> map=new HashMap<>();
                                if (myArticleJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                                    map.put("news_pics_set",myArticleJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                                else {
                                    map.put("news_pics_set",null);
                                }
                                map.put("id",myArticleJson.getData().getNews().get(i).getId());
                                map.put("title",myArticleJson.getData().getNews().get(i).getTitle());
                                map.put("newsAuthor",myArticleJson.getData().getNews().get(i).getAuthor().getNickname());

                                list.add(map);
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    news.setData(list);
                                    news.notifyDataSetChanged();

                                }
                            });


                        } catch (IOException e) {
                            e.printStackTrace();
                            articleRefreshLayout.finishRefresh(false);//结束刷新（刷新失败）
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

            }
        });



    }
}