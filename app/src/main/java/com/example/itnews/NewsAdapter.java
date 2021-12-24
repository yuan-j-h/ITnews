package com.example.itnews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Map<String,Object>> list;

    public NewsAdapter(Context context, List<Map<String,Object>> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).newsTitle.setText(Objects.requireNonNull(list.get(position).get("title")).toString());
        ((ViewHolder) holder).newsAuthor.setText(Objects.requireNonNull(list.get(position).get("newsAuthor")).toString());
        if (list.get(position).get("news_pics_set")!=null){
        String imagesUrl = Objects.requireNonNull(list.get(position).get("news_pics_set")).toString();
        Glide.with(context).load(imagesUrl).into(((ViewHolder) holder).newsPicture);}
        else {
            Glide.with(context).load((Bitmap) null).into(((ViewHolder) holder).newsPicture);
        }
        //跳转
        ((ViewHolder) holder).newsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("id",list.get(position).get("id").toString());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Map<String, Object>> list) {
        this.list=list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle,newsAuthor;
        ImageView newsPicture;
        RelativeLayout newsItem;
        public ViewHolder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsAuthor = itemView.findViewById(R.id.newsAuthor);
            newsPicture = itemView.findViewById(R.id.newsPicture);
            newsItem = itemView.findViewById(R.id.newsItem);
        }
    }
}
