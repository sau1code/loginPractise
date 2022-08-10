package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class memberActivity extends AppCompatActivity {

    private TextView textViewMemberTempshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        // 以下 "可"隨意刪除
        setTitle("activity_member");
        Intent intent = getIntent();
        textViewMemberTempshow = (TextView)findViewById(R.id.textView_member_tempshow);
        textViewMemberTempshow.setText("使用者帳號 : " + intent.getStringExtra("name"));
    }
}