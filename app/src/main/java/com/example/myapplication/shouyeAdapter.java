package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class shouyeAdapter extends RecyclerView.Adapter<shouyeAdapter.MyViewHolder> {
    private final Context mContext;
    public List<Integer> mId;
    public List<Integer> mPic;
    public List<String> mTitle;
    public List<Integer> mLike;
    public List<Integer> mLove;

    //recyclerView的适配器
    public shouyeAdapter(
            List<Integer> id,
            List<String> title,
            List<Integer> pic,
            List<Integer> like,
            List<Integer> love,
            Context context
    ) {
        mId = id;
        mPic = pic;
        mTitle = title;
        mLike = like;
        mLove = love;
        this.mContext = context;
    }

    @NonNull
    @Override
    public shouyeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shouye_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shouyeAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mTitle.get(position));
        int id = mId.get(position);

        holder.imageView.setImageResource(mPic.get(position));

        holder.textView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, shouyeContent.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.page);
            imageView = itemView.findViewById(R.id.pic);
        }
    }
}
