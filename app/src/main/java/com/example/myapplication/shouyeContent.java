package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class shouyeContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        TextView title_content = findViewById(R.id.title_content);
        TextView text_content = findViewById(R.id.text_content);
        ImageView pic_content = findViewById(R.id.pic_content);
        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        //获取传入的id，再到数据库里面查询对应id的内容，并且显示到布局里
        DataHelper dbHelper = new DataHelper(shouyeContent.this, "Database.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int viewCount = 0;
        try {
            Cursor cursor = db.query("News_Data",null,"id=?",new String[]{id},null, null, null);
            //遍历获取
            if(cursor.moveToFirst()){
                do {
                    title_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                    text_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                    pic_content.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow("img_src")));
                    viewCount = cursor.getInt(cursor.getColumnIndexOrThrow("view_count"));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (NullPointerException e){
            e.printStackTrace();  // 打印异常信息
        }

        // 更新浏览次数
        new DataDao(this).updateViewCount(Long.parseLong(id), viewCount+1);
    }
}