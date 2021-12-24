package com.example.itnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private Button last;
    private TextView detailTitle,detailName,detailContent,detailLikesNumber,detailCollectionNumber;
    private ImageView detailPicture,detailLikes,detailCollection;
    private String id,tokenString,title,content,nickname,avatar;
    private int author_id,like_num,star_num,isLike,isStar,pictureNumber;
    private RelativeLayout likeButton,starButton;
    private Boolean changeLike,changeStar;
    private GridView gridView;
    private String [] pics=new String[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gridView = findViewById(R.id.detailGridView);
        final List<HashMap<String,Object>> meumList = new ArrayList<HashMap<String, Object>>();


        //获得id
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //
        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        tokenString = sharedPreferences.getString("token", String.valueOf(false));

        changeLike = false;
        changeStar = false;

        last = findViewById(R.id.last);
        detailTitle = findViewById(R.id.detailTitle);
        detailName = findViewById(R.id.detailName);
        detailContent = findViewById(R.id.detailContent);
        detailPicture = findViewById(R.id.detailPicture);
        detailLikesNumber = findViewById(R.id.detailLikesNumber);
        detailCollectionNumber = findViewById(R.id.detailCollectionNumber);
        detailLikes = findViewById(R.id.detailLikes);
        detailCollection = findViewById(R.id.detailCollection);
        likeButton = findViewById(R.id.likeButton);
        starButton = findViewById(R.id.starButton);

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                finish();
            }
        });

        //内容显示

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://39.106.195.109/itnews/api/news/info/"+id+"/info-full")
                            .method("GET", null)
                            .addHeader("Authorization", tokenString)
                            .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                            .build();
                    Response response = client.newCall(request).execute();

                    Gson gson = new Gson();
                    DetailJson detailJson = gson.fromJson(response.body().string(),DetailJson.class);
                    title = detailJson.getData().getTitle();
                    content = detailJson.getData().getContent();
                    author_id = detailJson.getData().getAuthor_id();
                    like_num = detailJson.getData().getLike_num();
                    star_num = detailJson.getData().getStar_num();
                    isLike = detailJson.getData().getIsLike();
                    isStar = detailJson.getData().getIsStar();
                    pictureNumber = detailJson.getData().getPics().size();
                    if (detailJson.getData().getPics().size()!=0){
                        for (int i=0;i<detailJson.getData().getPics().size();i++){
                            pics[i]=detailJson.getData().getPics().get(i);
                        }
                    }



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            detailTitle.setText(title);
                            detailContent.setText(content);
                            detailLikesNumber.setText(String.valueOf(like_num));
                            detailCollectionNumber.setText(String.valueOf(star_num));

                            if (isLike == 0){
                                Glide.with(DetailActivity.this).load(R.drawable.likebefore).into(detailLikes);
                            }
                            else{
                                Glide.with(DetailActivity.this).load(R.drawable.likeafter).into(detailLikes);
                            }
                            if (isStar == 0){
                                Glide.with(DetailActivity.this).load(R.drawable.starbefore).into(detailCollection);
                            }
                            else{
                                Glide.with(DetailActivity.this).load(R.drawable.starafter).into(detailCollection);
                            }

                            //picture
                            if (pictureNumber!=0){
                                for(int i = 0; i < pictureNumber; i++){
                                    HashMap<String,Object> map = new HashMap<String, Object>();
                                    map.put("itemImage",pics[i]);
                                    meumList.add(map);}
                                PicAdapter picAdapter = new PicAdapter(DetailActivity.this,meumList);
                                // 应用适配器
                                gridView.setAdapter(picAdapter);
                            }
                        }
                    });


                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.start();
        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //作者详情
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url("http://39.106.195.109/itnews/api/users/"+author_id+"/info-alpha")
                            .method("GET", null)
                            .addHeader("Authorization", tokenString)
                            .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                            .build();
                    Response response = client.newCall(request).execute();
                    Gson gson = new Gson();
                    UserJson userJson = gson.fromJson(response.body().string(),UserJson.class);
                    nickname = userJson.getData().getNickname();
                    avatar = userJson.getData().getAvatar();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            detailName.setText(nickname);
                            Glide.with(DetailActivity.this).load(avatar).into(detailPicture);

                        }
                    });

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        detailTitle.setText(title);
//        detailContent.setText(content);
//        detailLikesNumber.setText(like_num);
//        detailCollectionNumber.setText(star_num);
//        detailName.setText(nickname);
//        Glide.with(DetailActivity.this).load(avatar).into(detailPicture);

        //点赞收藏
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike==0) {
                    if (changeLike.equals(false)) {
                        changeLike = true;
                        like_num=like_num+1;
                        Glide.with(DetailActivity.this).load(R.drawable.likeafter).into(detailLikes);
                    } else if (changeLike.equals(true)) {
                        changeLike = false;
                        like_num=like_num-1;
                        Glide.with(DetailActivity.this).load(R.drawable.likebefore).into(detailLikes);
                    }
                }
                if (isLike==1) {
                    if (changeLike.equals(false)) {
                        changeLike = true;
                        like_num=like_num-1;
                        Glide.with(DetailActivity.this).load(R.drawable.likebefore).into(detailLikes);
                    } else if (changeLike.equals(true)) {
                        changeLike = false;
                        like_num=like_num+1;
                        Glide.with(DetailActivity.this).load(R.drawable.likeafter).into(detailLikes);
                    }
                }
                detailLikesNumber.setText(String.valueOf(like_num));
            }
        });
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStar==0) {
                    if (changeStar.equals(false)) {
                        changeStar = true;
                        star_num=star_num+1;
                        Glide.with(DetailActivity.this).load(R.drawable.starafter).into(detailCollection);
                    } else if (changeStar.equals(true)) {
                        changeStar = false;
                        star_num=star_num-1;
                        Glide.with(DetailActivity.this).load(R.drawable.starbefore).into(detailCollection);
                    }
                }
                if (isStar==1) {
                    if (changeStar.equals(false)) {
                        changeStar = true;
                        star_num=star_num-1;
                        Glide.with(DetailActivity.this).load(R.drawable.starbefore).into(detailCollection);
                    } else if (changeStar.equals(true)) {
                        changeStar = false;
                        star_num=star_num+1;
                        Glide.with(DetailActivity.this).load(R.drawable.starafter).into(detailCollection);
                    }
                }
                detailCollectionNumber.setText(String.valueOf(star_num));

            }
        });

        //点击头像
        detailPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,AuthorActivity.class);
                intent.putExtra("author_id",author_id);
                startActivity(intent);
            }
        });

}
    protected void onDestroy () {
        super.onDestroy();
        //改变点赞收藏
        if (changeLike.equals(true)){
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create(mediaType, "");
                        Request request = new Request.Builder()
                                .url("http://39.106.195.109/itnews/api/news/operator/"+id+"/like")
                                .method("POST", body)
                                .addHeader("Authorization",tokenString)
                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                .build();
                        Response response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.start();
            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (changeStar.equals(true)){
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();
                        MediaType mediaType = MediaType.parse("text/plain");
                        RequestBody body = RequestBody.create(mediaType, "");
                        Request request = new Request.Builder()
                                .url("http://39.106.195.109/itnews/api/news/operator/"+id+"/star")
                                .method("POST", body)
                                .addHeader("Authorization", tokenString)
                                .addHeader("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                                .build();
                        Response response = client.newCall(request).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread2.start();
            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}