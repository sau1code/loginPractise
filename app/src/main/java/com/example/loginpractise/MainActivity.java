package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextAccount, editTextPassword;
    private TextView textViewRegister;
    private Map<String, String> adminMap, memberMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        buttonLogin = (Button)findViewById(R.id.button_login);
        textViewRegister = (TextView)findViewById(R.id.textView_register);
        editTextAccount = (EditText)findViewById(R.id.EditText_account);
        editTextPassword = (EditText)findViewById(R.id.EditText_password);
        ((TextView)findViewById(R.id.textView_temp))
                .setText("暫時-管理者帳號: admin1~3\n暫時-使用者帳號: member1~99\n\n(密碼同帳號)");


        // 暫時-管理者帳密 Map<帳號, 密碼>
        adminMap = new HashMap<>();
        adminMap.put("admin1", "admin1");
        adminMap.put("admin2", "admin2");
        adminMap.put("admin3", "admin3");

        // 暫時-使用者帳密 Map<帳號, 密碼>
        memberMap = new HashMap<>();
        for (int i = 1; i < 100; i++) {
            memberMap.put("member" + i, "member" + i);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editTextAccount.getText().toString();
                String password = editTextPassword.getText().toString();

                if (account.length() * password.length() == 0) {
                    Toast.makeText(MainActivity.this, "請輸入完整帳號密碼", Toast.LENGTH_SHORT).show();
                } else if (adminMap.get(account) != null && adminMap.get(account).equals(password)) {
                    Intent i = new Intent(MainActivity.this, adminActivity.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "管理員登入", Toast.LENGTH_SHORT).show();
                } else if (memberMap.get(account) != null && memberMap.get(account).equals(password)) {
                    Intent i = new Intent(MainActivity.this, memberActivity.class);
                    startActivity(i);
                    Toast.makeText(MainActivity.this, "使用者登入", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, registerActivity.class);
                startActivity(i);
            }
        });

    }
}