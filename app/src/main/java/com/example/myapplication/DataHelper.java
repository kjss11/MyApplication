package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//数据库建表，初始化
public class DataHelper extends SQLiteOpenHelper {
    public static final String User = "create table User ("
            + "id integer primary key autoincrement, "
            + "account char, "
            + "password char)";

    public static final String News_Data = "create table News_Data("
            + "id integer primary key autoincrement, "
            + "user_id integer, "
            + "classify char, "
            + "title text,"
            + "content text default ' ',"
            + "img_src integer default 0,"
            + "view_count integer default 0,"
            + "is_like integer default 0,"
            + "zuozhe text default ' ',"
            + "pingfen text default ' ',"
            + "is_love integer default 0)";

    public static final String View_Count = "create table View_Count("
            + "id integer primary key autoincrement, "
            + "user_id integer, "
            + "news_id integer, "
            + "classify char, "
            + "view_count integer default 0,"
            + "is_like integer default 0,"
            + "is_love integer default 0)";

    public DataHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(User);
        sqLiteDatabase.execSQL(News_Data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

