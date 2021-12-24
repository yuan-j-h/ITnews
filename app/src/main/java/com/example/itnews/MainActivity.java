package com.example.itnews;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    NewsFragment newsFragment = new NewsFragment();
    ArticleFragment articleFragment = new ArticleFragment();
    PersonalFragment personalFragment = new PersonalFragment();
    private TextView mainArticle,mainNews,mainPersonal;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainArticle = findViewById(R.id.mainArticle);
        mainNews = findViewById(R.id.mainNews);
        mainPersonal = findViewById(R.id.mainPersonal);


        //默认加载
        getSupportFragmentManager().beginTransaction().replace(R.id.mainPage,newsFragment).commit();

        mainNews.setTextColor(getResources().getColor(R.color.blue));
        mainArticle.setTextColor(getResources().getColor(R.color.black));
        mainPersonal.setTextColor(getResources().getColor(R.color.black));

        //三个页面跳转
        RelativeLayout newsButton = findViewById(R.id.newsButton);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage,newsFragment).commit();
                mainNews.setTextColor(getResources().getColor(R.color.blue));
                mainArticle.setTextColor(getResources().getColor(R.color.black));
                mainPersonal.setTextColor(getResources().getColor(R.color.black));

            }
        });
        RelativeLayout articleButton = findViewById(R.id.articleButton);
        articleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage,articleFragment).commit();
                mainNews.setTextColor(getResources().getColor(R.color.black));
                mainArticle.setTextColor(getResources().getColor(R.color.blue));
                mainPersonal.setTextColor(getResources().getColor(R.color.black));
            }
        });
        RelativeLayout personalButton = findViewById(R.id.personalButton);
        personalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainPage,personalFragment).commit();
                mainNews.setTextColor(getResources().getColor(R.color.black));
                mainArticle.setTextColor(getResources().getColor(R.color.black));
                mainPersonal.setTextColor(getResources().getColor(R.color.blue));
            }
        });

    }
}