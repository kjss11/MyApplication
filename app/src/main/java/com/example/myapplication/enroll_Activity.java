package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class enroll_Activity extends AppCompatActivity {
    private DataHelper dbHelper;
    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        //定义控件以及数据库初始化
        dbHelper = new DataHelper(this,"Database.db",null,1);
        EditText enroll_password1 = findViewById(R.id.password_enroll);
        EditText enroll_password2 = findViewById(R.id.password2_enroll);
        EditText enroll_account = findViewById(R.id.account_enroll);

        //注册按钮绑定以及点击事件
        Button enroll_enroll = findViewById(R.id.enroll_enroll);
        enroll_enroll.setOnClickListener(view -> {
            //定义两个密码输入框
            String password1 = enroll_password1.getText().toString();
            String password2 = enroll_password2.getText().toString();
            //账号
            String account = enroll_account.getText().toString();
            //数据库
            Cursor cursor;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //数据库的账号
            String account_db = "";
            //查询的字段
            String[] columns = {"account"};
            // 定义查询条件
            String selection = "account=?";
            // 定义查询条件参数
            String[] selectionArgs = {account};

            if (account.length() == 6 && password1.equals(password2) && (password1.length() == 6)){
                //查询
                cursor = db.query("User",columns,selection,selectionArgs,null,null,null);

                //遍历获取
                if(cursor.moveToFirst()){
                    do {
                        account_db = cursor.getString(cursor.getColumnIndexOrThrow("account"));
                    }while (cursor.moveToNext());
                }

                //关闭cursor对象
                cursor.close();

                //判断是否有相同账号，没有就把数据插入数据库
                if (!account.equals(account_db)){
                    ContentValues values = new ContentValues();
                    values.put("account",account);
                    values.put("password",password1);
                    db.insert("User",null,values);
                    Toast.makeText(enroll_Activity.this,"注册成功,返回登陆页面",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(enroll_Activity.this,"注册失败，已经有相同帐号了",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(enroll_Activity.this,"账号密码等于6位数且两次输入的密码必须一致！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}