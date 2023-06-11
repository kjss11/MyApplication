package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public List<Integer> mId;
    public List<Integer> mPic;
    public List<String> mTitle;
    public List<Integer> mLike;
    public List<Integer> mLove;
    private final Context mContext;

    //recyclerView的适配器
    public MyAdapter(
            List<Integer> id,
            List<String> title,
            List<Integer> pic,
            List<Integer> like,
            List<Integer> love,
            Context context
            ){
        mId = id;
        mPic = pic;
        mTitle = title;
        mLike = like;
        mLove = love;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mTitle.get(position));
        int id = mId.get(position);

        if (mLike.get(position) == 1){
            holder.imageButton0.setImageResource(R.drawable.like_);
            holder.imageButton0.setImageTintList(ColorStateList.valueOf(Color.RED));
        }else {
            holder.imageButton0.setImageResource(R.drawable.like);
            holder.imageButton0.setImageTintList(ColorStateList.valueOf(Color.GRAY));
        }

        if (mLove.get(position) == 1){
            holder.imageButton1.setImageResource(R.drawable.collection_);
            holder.imageButton1.setImageTintList(ColorStateList.valueOf(Color.YELLOW));
        }else {
            holder.imageButton1.setImageResource(R.drawable.collection);
            holder.imageButton1.setImageTintList(ColorStateList.valueOf(Color.GRAY));
        }
        holder.imageView.setImageResource(mPic.get(position));

        holder.imageButton0.setOnClickListener(view -> {
            DataHelper dbHelper = new DataHelper(mContext,"Database.db",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.clear();
            if (mLike.get(position) == 1){
                values.put("is_like", 0);
                mLike.set(position,0);
            }else{
                values.put("is_like", 1);
                mLike.set(position,1);
            }
            db.update("News_Data",values,"id=?",new String[]{String.valueOf(id)});
            notifyItemChanged(position);
        });

        holder.imageButton1.setOnClickListener(view -> {
            DataHelper dbHelper = new DataHelper(mContext,"Database.db",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.clear();
            if (mLove.get(position) == 1){
                values.put("is_love", 0);
                mLove.set(position,0);
            }else{
                values.put("is_love", 1);
                mLove.set(position,1);
            }
            db.update("News_Data",values,"id=?",new String[]{String.valueOf(id)});
            notifyItemChanged(position);
        });

        holder.textView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, shouyeContent.class);
            intent.putExtra("id",id);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public ImageButton imageButton0;
        public ImageButton imageButton1;
        public MyViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.page);
            imageView = itemView.findViewById(R.id.pic);
            imageButton0 = itemView.findViewById(R.id.like);
            imageButton1 = itemView.findViewById(R.id.love);
        }
    }
}
