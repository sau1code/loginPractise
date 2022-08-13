package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
        getSupportActionBar().hide();                   // 隱藏ActionBar
        getWindow().setStatusBarColor(0xffffffff);      // 最上面StatusBar白色底
        getWindow().setNavigationBarColor(0xaaffffff);  // 最下面NavigationBar白色底
        getWindow().getDecorView()                      // 上面字設黑 | 下面虛擬按鈕深色
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        buttonLogin = (Button)findViewById(R.id.button_login);
        textViewRegister = (TextView)findViewById(R.id.textView_register);
        editTextAccount = (EditText)findViewById(R.id.EditText_account);
        editTextPassword = (EditText)findViewById(R.id.EditText_password);
        ((TextView)findViewById(R.id.textView__main_show2))
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

        // 登入
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editTextAccount.getText().toString();
                String password = editTextPassword.getText().toString();

                if (account.length() * password.length() == 0) {
                    Toast.makeText(MainActivity.this, "請輸入完整帳號密碼", Toast.LENGTH_SHORT).show();
                } else if (adminMap.get(account) != null && adminMap.get(account).equals(password)) {
                    Toast.makeText(MainActivity.this, "管理員登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, adminActivity.class);
                    intent.putExtra("name", account);
                    startActivity(intent);
                } else if (memberMap.get(account) != null && memberMap.get(account).equals(password)) {
                    Toast.makeText(MainActivity.this, "假-使用者登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, memberActivity.class);
                    intent.putExtra("name", account);
                    startActivity(intent);
                } else if (password.equals(getPasswordFromSQLite(account))) {
                    Toast.makeText(MainActivity.this, "真-使用者登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, memberActivity.class);
                    intent.putExtra("name", account);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 註冊
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });

    }

    // 再按一次退出程序
    long exitTime;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else finish();
    }

    // 回主畫面呼叫showAllAccountAndPasswordfromSQLite()更新ALL SQLite帳密
    @Override
    protected void onStart() {
        super.onStart();
        showAllAccountAndPasswordfromSQLite();
    }

    // 用editText輸入的帳號至SQLite "撈密碼"
    public String getPasswordFromSQLite(String account) {
        String password = "";
        mySQLiteContract.mySQLiteDbHelper SQLiteHelper = new mySQLiteContract.mySQLiteDbHelper(MainActivity.this);
        SQLiteDatabase SQLiteDb = SQLiteHelper.getWritableDatabase();
        String tableSQL = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
        String userSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER;
        String pwdSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD;
        Cursor cursorPassword = SQLiteDb.rawQuery("SELECT "+pwdSQL+" FROM "+tableSQL+" WHERE "+userSQL+" = '"+account+"' ;", null);
        if (cursorPassword.getCount() > 0){
            cursorPassword.moveToFirst();
            password = cursorPassword.getString(0);
        }
        cursorPassword.close();
        return password;
    }

    // 登入畫面提示全部SQLite帳密
    public void showAllAccountAndPasswordfromSQLite(){
        mySQLiteContract.mySQLiteDbHelper SQLiteHelper = new mySQLiteContract.mySQLiteDbHelper(MainActivity.this);
        SQLiteDatabase SQLiteDb = SQLiteHelper.getWritableDatabase();
        String table = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
        Cursor AllTable = SQLiteDb.rawQuery("select * from " + table + ";", null);
        StringBuilder sb = new StringBuilder();
        if (AllTable.getCount() > 0) {
            AllTable.moveToFirst();
            do {

                sb.append("帳/密 : " + AllTable.getString(1) + " / " + AllTable.getString(2) + "\n");
            } while (AllTable.moveToNext());
        }
        AllTable.close();
        ((TextView)findViewById(R.id.textView__main_show1)).setText(sb);
    }

}

