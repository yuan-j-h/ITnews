package com.example.itnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

public class OutPutAdapter extends BaseAdapter {
    private Context context;
    List<HashMap<String, Object>> list;

    public OutPutAdapter(Context context, List<HashMap<String, Object>> list){

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_picture, parent, false);
        ImageView imageView = view.findViewById(R.id.itemPicture);
        if (getCount()!=0){
            String path = list.get(position).get("Img").toString();//.replace("{itemImage=","").replace("}","");
            Log.i("path",path);
//            Glide.with(context).load(url).into(imageView);
            Bitmap bitmap = BitmapFactory.decodeFile( path.toString() );

            imageView.setImageBitmap(bitmap);
        }
        return view;

    }

    public void setData(List<HashMap<String, Object>> meumList) {
        this.list = list;
    }
}
