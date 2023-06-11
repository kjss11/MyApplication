package com.example.myapplication.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.setting.SettingsActivity;

public class UserFragment extends Fragment {
    Button exit;
    TextView username;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取控件并且修改

        View v = inflater.inflate(R.layout.user_fragment, container, false);
        Intent intent = getActivity().getIntent();
        username = v.findViewById(R.id.username);

        if (intent != null) {
            String value = intent.getStringExtra("user_name");
            username.setText(value);
        }

        requireActivity().setTitle("我的");

        exit = v.findViewById(R.id.exit);
        exit.setOnClickListener(view -> {
            Activity activity = getActivity();
            if (activity != null) {
                // 返回上一个Activity
                activity.finish();
                Toast.makeText(getActivity(), "已返回登录", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout setting = v.findViewById(R.id.ll_setting);
        setting.setOnClickListener(view -> {
            Intent intent1 = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent1);
        });
        return v;
    }
}
