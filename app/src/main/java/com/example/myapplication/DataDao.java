package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataDao {

    private DataHelper dbHelper;

    public DataDao(Context context) {
        dbHelper = new DataHelper(context, "Database.db", null, 1);
    }

    public boolean isUserDataInserted(long userId) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase(); //.getWritableDatabase()创建或打开已经创建过的数据库，writableDatabase已经创建过，所以在这里是打开

        boolean result = false;
        Cursor cursor = writableDatabase.query("News_Data", null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor.moveToNext()) {
            result = true;
        }
        //关闭cursor
        cursor.close();
        //关闭数据库
        writableDatabase.close();
        return result;
    }

    public void updateViewCount(long newsId, int newCount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("view_count", newCount);
        db.update("News_Data",values,"id=?",new String[]{String.valueOf(newsId)});
    }
}
