package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.myapplication.Fragment.shequFragment;
import com.example.myapplication.Fragment.gameFragment;
import com.example.myapplication.Fragment.shouyeFragment;
import com.example.myapplication.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class home_page extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.nav_view);
        //底部导航栏的实现
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new shouyeFragment()).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.recommend_1){
                fragment = new shouyeFragment();
            } else if (id == R.id.classify_2) {
                fragment = new shequFragment();
            } else if (id == R.id.collection_3) {
                fragment = new gameFragment();
            }else if(id == R.id.user_4){
                fragment = new UserFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            return true;
        });
    }
}