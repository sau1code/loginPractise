package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button buttonRegister, buttonLogin;
    private EditText editTextAccount, editTextPassword;
    public static SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteInit();

        /*
            findViewById (予馨)
        */

        // 註冊 (予馨)
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 登入 (予馨)
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = "";
                getPasswordFromSqlite(account);

            }
        });

    }

    // 取出SQLite密碼 (翔雲)
    protected String getPasswordFromSqlite(String s){
        String password = "";

        return password;
    }

    // 初始化SQLite (祥雲)
    protected void sqliteInit() {

    }
}