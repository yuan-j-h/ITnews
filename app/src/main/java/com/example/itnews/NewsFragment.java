package com.example.itnews;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsFragment extends Fragment {

    private String tokenString,news_pics_setString,titleString;
    private int pageCount,id;
    private RecyclerView recyclerView;
    private List<Map<String,Object>> list =new ArrayList<>();
    private int newsNumber,pageNumber;
    private NewsAdapter news;
    private RefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.clear();

        recyclerView = getActivity().findViewById(R.id.newsRecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        news = new NewsAdapter(getActivity(), list);
        recyclerView.setAdapter(news);

        //get
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));

        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://39.106.195.109/itnews/api/news/recommend/v4?page=1&size=7")
                            .method("GET", null)
                            .addHeader("Authorization", tokenString)
                            .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                            .build();
                    Response response = client.newCall(request).execute();

                    //JSON解析
                    Gson gson = new Gson();
                    NewsIDJson newsIDJson = gson.fromJson(response.body().string(),NewsIDJson.class);
                    //id = newsIDJson.getData().getNews().get(0).getId();
                    pageCount = newsIDJson.getData().getCount();
                    //news_pics_setString = newsIDJson.getData().getNews().get(0).getNews_pics_set().get(0);
                    //titleString = newsIDJson.getData().getNews().get(0).getTitle();
                    newsNumber = newsIDJson.getData().getNews().size();
                    pageNumber=1;


                    for (int i=0;i<newsNumber;i++){
                        Map<String,Object> map=new HashMap<>();
                        if (newsIDJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                        map.put("news_pics_set",newsIDJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                        else {
                            map.put("news_pics_set",null);
                        }
                        map.put("id",newsIDJson.getData().getNews().get(i).getId());
                        map.put("title",newsIDJson.getData().getNews().get(i).getTitle());
                        map.put("newsAuthor",newsIDJson.getData().getNews().get(i).getAuthor().getNickname());
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

        refreshLayout = getActivity().findViewById(R.id.newsRefreshLayout);
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);



                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            pageNumber=1;
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://39.106.195.109/itnews/api/news/recommend/v4?page=1&size=7")
                                    .method("GET", null)
                                    .addHeader("Authorization", tokenString)
                                    .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                    .build();
                            Response response = client.newCall(request).execute();

                            //JSON解析
                            Gson gson = new Gson();
                            NewsIDJson newsIDJson = gson.fromJson(response.body().string(),NewsIDJson.class);
                            pageCount = newsIDJson.getData().getCount();
                            //news_pics_setString = newsIDJson.getData().getNews().get(0).getNews_pics_set().get(0);
                            //titleString = newsIDJson.getData().getNews().get(0).getTitle();
                            newsNumber = newsIDJson.getData().getNews().size();

                            list.clear();
                            for (int i=0;i<newsNumber;i++){
                                Map<String,Object> map=new HashMap<>();
                                map.put("id",newsIDJson.getData().getNews().get(i).getId());
                                if (newsIDJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                                map.put("news_pics_set",newsIDJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                                else {
                                    map.put("news_pics_set",null);
                                }
                                map.put("title",newsIDJson.getData().getNews().get(i).getTitle());
                                map.put("newsAuthor",newsIDJson.getData().getNews().get(i).getAuthor().getNickname());
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
                            refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
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

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/);

                if (pageNumber<pageCount) {
                    pageNumber = pageNumber + 1;
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                OkHttpClient client = new OkHttpClient().newBuilder()
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://39.106.195.109/itnews/api/news/recommend/v4?page="+pageNumber+"&size=7")
                                        .method("GET", null)
                                        .addHeader("Authorization", tokenString)
                                        .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                        .build();
                                Response response = client.newCall(request).execute();

                                //JSON解析
                                Gson gson = new Gson();
                                NewsIDJson newsIDJson = gson.fromJson(response.body().string(), NewsIDJson.class);
                                pageCount = newsIDJson.getData().getCount();
                                //news_pics_setString = newsIDJson.getData().getNews().get(0).getNews_pics_set().get(0);
                                //titleString = newsIDJson.getData().getNews().get(0).getTitle();
                                newsNumber = newsIDJson.getData().getNews().size();
                                Log.i("why", String.valueOf(newsNumber));

                                for (int i = 0; i < newsNumber; i++) {
                                    Map<String, Object> map = new HashMap<>();
                                    if (newsIDJson.getData().getNews().get(i).getNews_pics_set().size()!=0){
                                        map.put("news_pics_set", newsIDJson.getData().getNews().get(i).getNews_pics_set().get(0));}
                                    else {
                                        map.put("news_pics_set",null);
                                    }
                                        map.put("id",newsIDJson.getData().getNews().get(i).getId());
                                        map.put("title", newsIDJson.getData().getNews().get(i).getTitle());
                                        map.put("newsAuthor",newsIDJson.getData().getNews().get(i).getAuthor().getNickname());
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
                                refreshLayout.finishLoadMore(false);//结束加载（加载失败）
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
                else {
                    Toast.makeText(getActivity(), "已经到底了", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}