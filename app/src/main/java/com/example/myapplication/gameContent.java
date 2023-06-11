package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class gameContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_content);
        TextView title_content = findViewById(R.id.title_content);
        TextView text_content = findViewById(R.id.text_content);
        TextView pingfen_content = findViewById(R.id.pingfen_content);
        ImageView pic_content = findViewById(R.id.pic_content);
        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        //获取传入的id，再到数据库里面查询对应id的内容，并且显示到布局里
        DataHelper dbHelper = new DataHelper(gameContent.this, "Database.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int viewCount = 0;
        try {
            Cursor cursor = db.query("News_Data",null,"id=?",new String[]{id},null, null, null);
            //遍历获取
            if(cursor.moveToFirst()){
                do {
                    title_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                    text_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                    pingfen_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("pingfen")));
                    pic_content.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow("img_src")));
                    viewCount = cursor.getInt(cursor.getColumnIndexOrThrow("view_count"));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (NullPointerException e){
            e.printStackTrace();  // 打印异常信息
        }

        TextView textView1 = findViewById(R.id.text1);
        TextView textView2 = findViewById(R.id.text2);
        TextView textView3 = findViewById(R.id.text3);
        TextView textView4 = findViewById(R.id.text4);

        textView1.setText((int)(Math.random()*10) + "万\n当前在线");
        textView2.setText("" + (int)(Math.random()*100) + "元\n 最低价格");
        textView3.setText((int)(Math.random()*500) + "万\n玩家数");
        textView4.setText((int)(Math.random()*100) + "h\n平均游戏时间");

    }




}