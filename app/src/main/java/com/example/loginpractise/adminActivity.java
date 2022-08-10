package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class adminActivity extends AppCompatActivity {

    private TextView textViewAdminTempshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();

        setTitle("activity_admin");
        Intent intent = getIntent();
        textViewAdminTempshow = (TextView)findViewById(R.id.textView_admin_tempshow);
        textViewAdminTempshow.setText("管理員帳號 : "+intent.getStringExtra("name"));

    }
}