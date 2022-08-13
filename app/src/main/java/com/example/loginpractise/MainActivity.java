package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextAccount, editTextPassword;
    private TextView textViewRegister, textViewMainSqlite, textViewMainShow2;
    private Map<String, String> adminMap, memberMap;
    private ImageView imageViewAicon;
    private mySQLiteContract.mySQLiteDbHelper SQLiteHelper;
    private SQLiteDatabase SQLiteDb;;
    private final String tableSQL = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
    private final String userSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER;
    private final String pwdSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD;

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
        textViewMainSqlite = (TextView)findViewById(R.id.textView_main_sqlite);
        textViewMainShow2 = (TextView)findViewById(R.id.textView__main_show2);
        textViewMainSqlite = (TextView)findViewById(R.id.textView_main_sqlite);
        editTextAccount = (EditText)findViewById(R.id.EditText_account);
        editTextPassword = (EditText)findViewById(R.id.EditText_password);
        imageViewAicon = (ImageView)findViewById(R.id.imageView_xicon);
        SQLiteHelper = new mySQLiteContract.mySQLiteDbHelper(MainActivity.this);
        SQLiteDb = SQLiteHelper.getWritableDatabase();;

        textViewMainShow2.setText("暫時-管理者帳號: admin1~3\n暫時-使用者帳號: member1~99\n\n(密碼同帳號)");
        textViewMainSqlite.append("     [ -連點這清空SQLite!- ]");

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
                String inputAccount = editTextAccount.getText().toString();
                String inputPassword = editTextPassword.getText().toString();
                if (inputAccount.length() * inputPassword.length() == 0) {
                    Toast.makeText(MainActivity.this, "請輸入完整帳號密碼", Toast.LENGTH_SHORT).show();
                } else if (adminMap.get(inputAccount) != null && adminMap.get(inputAccount).equals(inputPassword)) {
                    Toast.makeText(MainActivity.this, "管理員登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, adminActivity.class);
                    intent.putExtra("name", inputAccount);
                    startActivity(intent);
                } else if (memberMap.get(inputAccount) != null && memberMap.get(inputAccount).equals(inputPassword)) {
                    Toast.makeText(MainActivity.this, "假-使用者登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, memberActivity.class);
                    intent.putExtra("name", inputAccount);
                    startActivity(intent);
                } else if (inputPassword.equals(getPasswordFromSQLite(inputAccount))) {
                    Toast.makeText(MainActivity.this, "真-使用者登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, memberActivity.class);
                    intent.putExtra("name", inputAccount);
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

        textViewMainSqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((System.currentTimeMillis() - exitTime) > 500) {
                    exitTime = System.currentTimeMillis();
                } else {
                    int version = mySQLiteContract.mySQLiteDbHelper.DATABASE_VERSION;
                    SQLiteHelper.onUpgrade(SQLiteDb, version,version);
                    showAllAccountAndPasswordfromSQLite();
                    Toast.makeText(MainActivity.this, "清空SQLite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewAicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Cursor cursorPassword = SQLiteDb.rawQuery("SELECT "+pwdSQL+" FROM "+tableSQL+" WHERE "+userSQL+" = '"+account+"' ;", null);
        if (cursorPassword.getCount() > 0){
            cursorPassword.moveToFirst();
            password = cursorPassword.getString(0);
        }
        cursorPassword.close();
        return password;
    }

    // 登入畫面提示全部SQLite帳密
    public void showAllAccountAndPasswordfromSQLite(){;
        Cursor AllTable = SQLiteDb.rawQuery("select * from " + tableSQL + ";", null);
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

