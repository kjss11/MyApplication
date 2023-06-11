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

public class gameAdapter extends RecyclerView.Adapter<gameAdapter.MyViewHolder> {
    private final Context mContext;
    public List<Integer> mId;
    public List<Integer> mPic;
    public List<String> mTitle;
    public List<String> mpingfen;
    public List<Integer> mLove;

    //recyclerView的适配器
    public gameAdapter(
            List<Integer> id,
            List<String> title,
            List<Integer> pic,
            List<String> pingfen,
            List<Integer> love,
            Context context
    ) {
        mId = id;
        mPic = pic;
        mTitle = title;
        mpingfen = pingfen;
        mLove = love;
        this.mContext = context;
    }

    @NonNull
    @Override
    public gameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull gameAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mTitle.get(position));
        holder.pingfen.setText(mpingfen.get(position));
        int id = mId.get(position);

        holder.imageView.setImageResource(mPic.get(position));

        holder.textView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, gameContent.class);
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
        public TextView pingfen;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.page);
            imageView = itemView.findViewById(R.id.pic);
            pingfen = itemView.findViewById(R.id.pingfen);
        }
    }
}
