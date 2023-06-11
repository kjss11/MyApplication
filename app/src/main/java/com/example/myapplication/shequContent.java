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

public class shequContent extends AppCompatActivity {

    TextView type1;
    TextView type2;
    int displayType = 0;
    int colorTheme;
    int colorGray;
    LinearLayout zhengwen;
    LinearLayout pinglun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shequ_content);
        TextView title_content = findViewById(R.id.title_content);
        TextView text_content = findViewById(R.id.text_content);
        TextView zuozhe_content = findViewById(R.id.zuozhe);
        ImageView pic_content = findViewById(R.id.pic_content);
        zhengwen = findViewById(R.id.zhengwen);
        pinglun = findViewById(R.id.pinglun);
        Intent intent = getIntent();
        String id = String.valueOf(intent.getIntExtra("id",0));
        //获取传入的id，再到数据库里面查询对应id的内容，并且显示到布局里
        DataHelper dbHelper = new DataHelper(shequContent.this, "Database.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int viewCount = 0;
        try {
            Cursor cursor = db.query("News_Data",null,"id=?",new String[]{id},null, null, null);
            //遍历获取
            if(cursor.moveToFirst()){
                do {
                    title_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                    text_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("content")));
                    zuozhe_content.setText(cursor.getString(cursor.getColumnIndexOrThrow("zuozhe")));
                    pic_content.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow("img_src")));
                    viewCount = cursor.getInt(cursor.getColumnIndexOrThrow("view_count"));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (NullPointerException e){
            e.printStackTrace();  // 打印异常信息
        }

        colorTheme = getResources().getColor(R.color.black);
        colorGray = getResources().getColor(R.color.gray);

        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);

        updateTypeView(displayType);
    }

    private void updateTypeView(int selection) {
        type1.setTextColor(selection == 0 ? colorTheme : colorGray);
        type2.setTextColor(selection == 1 ? colorTheme : colorGray);
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayType = 0;
                updateTypeView(displayType);
                zhengwen.setVisibility(View.VISIBLE);
                pinglun.setVisibility(View.GONE);
            }
        });
        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayType = 1;
                updateTypeView(displayType);
                zhengwen.setVisibility(View.GONE);
                pinglun.setVisibility(View.VISIBLE);
            }
        });

    }


}