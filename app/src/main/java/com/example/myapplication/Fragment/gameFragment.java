package com.example.myapplication.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.DataHelper;
import com.example.myapplication.MyAdapter;
import com.example.myapplication.PreferenceUtils;
import com.example.myapplication.R;
import com.example.myapplication.gameAdapter;

import java.util.ArrayList;
import java.util.List;

public class gameFragment extends Fragment {
    List<String> data = new ArrayList<>();
    List<Integer> pic = new ArrayList<>();
    List<Integer> id = new ArrayList<>();
    List<String> pingfen = new ArrayList<>();
    List<Integer> love = new ArrayList<>();
    RecyclerView recyclerView;
    long userId = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_fragment, container, false);
        SwipeRefreshLayout swipeRefreshLayout = v.findViewById(R.id.srl);
        recyclerView = v.findViewById(R.id.Recommend_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requireActivity().setTitle("游戏库");
        // 获取当前用户id
        userId = PreferenceUtils.get(requireActivity()).getLong("current_user");

        refresh();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            refresh();
            swipeRefreshLayout.setRefreshing(false);
        });

        return v;
    }

    private void refresh() {
        Data();
    }

    private void Data() {
        data.clear();
        pic.clear();
        id.clear();
        pingfen.clear();
        love.clear();

        DataHelper dbHelper = new DataHelper(getContext(), "Database.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("SELECT * FROM News_Data WHERE classify=? ORDER BY RANDOM() LIMIT 10", new String[]{"game"});

        if (cursor1.moveToFirst()) {
            do {
                data.add(cursor1.getString(cursor1.getColumnIndexOrThrow("title")));
                pic.add(cursor1.getInt(cursor1.getColumnIndexOrThrow("img_src")));
                id.add(cursor1.getInt(cursor1.getColumnIndexOrThrow("id")));
                pingfen.add(cursor1.getString(cursor1.getColumnIndexOrThrow("pingfen")));
                love.add(cursor1.getInt(cursor1.getColumnIndexOrThrow("is_love")));
            } while (cursor1.moveToNext());
        }

        cursor1.close();
        db.close();
        gameAdapter adapter = new gameAdapter(id, data, pic, pingfen, love, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
