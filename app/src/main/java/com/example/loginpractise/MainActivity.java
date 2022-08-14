package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String tableSQL = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
    private final String userSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER;
    private final String pwdSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD;
    private TextView textViewRegister, textViewForget, textViewSqlclean, textViewShow1, textViewShow2;
    private EditText editTextAccount, editTextPassword;
    private ImageView imageViewEyes;
    private Button buttonLogin;
    private mySQLiteContract.mySQLiteDbHelper SQLiteHelper;
    private SQLiteDatabase SQLiteDb;
    private Map<String, String> adminMap, memberMap;
    private String inputAccount, inputPassword;

    @SuppressLint("ClickableViewAccessibility")     // 伴隨setOnTouchListener出現的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------------------------------------------
        // findView
        // init set
        // 暫時-管理者帳密 Map<帳號, 密碼>
        // 暫時-使用者帳密 Map<帳號, 密碼>
        //---------------------------------------------
        // 帳號輸入 監聽 (當改變時)
        // 密碼輸入 監聽 (當改變時)
        // 登入鍵 監聽
        // 註冊文字 監聽
        // 顯示密碼圖 監聽
        // 忘記密碼字 監聽
        // 清空SQL字 監聽
        //--------------------------- (以下為外函式) --
        // 再按一次退出程序
        // 回主畫面呼叫showAllAccountAndPasswordfromSQLite() 更新show1
        // 用editText輸入的帳號至SQLite "撈密碼"
        // 登入畫面提示全部SQLite帳密
        //---------------------------------------------

        // findView
        textViewRegister = (TextView)findViewById(R.id.textView_main_register);
        textViewSqlclean = (TextView)findViewById(R.id.textView_main_sqlclean);
        textViewShow1 =(TextView)findViewById(R.id.textView_main_show1);
        textViewShow2 = (TextView)findViewById(R.id.textView_main_show2);
        textViewForget = (TextView)findViewById(R.id.textView_main_forget);
        editTextAccount = (EditText)findViewById(R.id.editText_main_account);
        editTextPassword = (EditText)findViewById(R.id.editText_main_password);
        imageViewEyes = (ImageView)findViewById(R.id.imageView_main_eyes);
        buttonLogin = (Button)findViewById(R.id.button_main_login);

        // init set
        getSupportActionBar().hide();                   // 隱藏ActionBar
        getWindow().setStatusBarColor(0xffffffff);      // 最上面StatusBar白色底
        getWindow().setNavigationBarColor(0xaaffffff);  // 最下面NavigationBar白色底
        getWindow().getDecorView()                      // 上面字設黑 | 下面虛擬按鈕深色
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        SQLiteHelper = new mySQLiteContract.mySQLiteDbHelper(MainActivity.this);
        SQLiteDb = SQLiteHelper.getWritableDatabase();
        textViewSqlclean.append("     [ - 連點這清空SQLite! - ]");
        textViewShow2.setText("暫時-管理者帳號: admin1~3\n暫時-使用者帳號: member1~99\n\n(密碼同帳號)");
        buttonLogin.setBackgroundColor(0xFFEEEEEE);
        buttonLogin.setTextColor(0xFFAAAAAA);
        inputAccount = "";
        inputPassword = "";

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

        // 帳號輸入 監聽 (當改變時)
        editTextAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputAccount = editTextAccount.getText().toString();
                if (inputAccount.length() * inputPassword.length() == 0) {
                    buttonLogin.setBackgroundColor(0xFFEEEEEE);
                    buttonLogin.setTextColor(0xFFAAAAAA);
                } else {
                    buttonLogin.setBackgroundColor(0xFFF44336);
                    buttonLogin.setTextColor(0xFFFFFFFF);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // 密碼輸入 監聽 (當改變時)
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputPassword = editTextPassword.getText().toString();
                if (inputAccount.length() * inputPassword.length() == 0) {
                    buttonLogin.setBackgroundColor(0xFFEEEEEE);
                    buttonLogin.setTextColor(0xFFAAAAAA);
                } else {
                    buttonLogin.setBackgroundColor(0xFFF44336);
                    buttonLogin.setTextColor(0xFFFFFFFF);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // 登入鍵 監聽
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // (帳號或密碼輸入為空)
                if (inputAccount.length() * inputPassword.length() == 0) {
                    Toast.makeText(MainActivity.this, "請輸入完整帳號密碼", Toast.LENGTH_SHORT).show();
                // (以輸入帳號當key進管理者Map取密碼 有值 且Map取出值與輸入密碼相同)
                } else if (adminMap.get(inputAccount) != null && adminMap.get(inputAccount).equals(inputPassword)) {
                    Toast.makeText(MainActivity.this, "管理員登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, adminActivity.class);
                    intent.putExtra("name", inputAccount);
                    startActivity(intent);
                // (以輸入帳號當key進使用者Map取密碼 有值 且Map取出值與輸入密碼相同)
                } else if (memberMap.get(inputAccount) != null && memberMap.get(inputAccount).equals(inputPassword)) {
                    Toast.makeText(MainActivity.this, "假-使用者登入", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, memberActivity.class);
                    intent.putExtra("name", inputAccount);
                    startActivity(intent);
                // (輸入密碼 等於 用輸入帳號去SQL取對應密碼)
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

        // 註冊文字 監聽
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });

        // 顯示密碼圖 監聽
        imageViewEyes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        imageViewEyes.setSelected(true);
                        editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        imageViewEyes.setSelected(false);
                        editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        break;
                }
                return true;
            }
        });

        // 忘記密碼字 監聽
        textViewForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "一定會想起來的！", Toast.LENGTH_SHORT).show();
            }
        });

        // 清空SQL字 監聽
        textViewSqlclean.setOnClickListener(new View.OnClickListener() {
            private long cleanSqlTime;
            @Override
            public void onClick(View view) {
                if ((System.currentTimeMillis() - cleanSqlTime) > 400) {
                    cleanSqlTime = System.currentTimeMillis();
                } else {
                    int version = mySQLiteContract.mySQLiteDbHelper.DATABASE_VERSION;
                    SQLiteHelper.onUpgrade(SQLiteDb, version,version);
                    madeShowFromSQL();
                    Toast.makeText(MainActivity.this, "清空SQLite", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // 再按一次退出程序
    private long exitTime;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else finish();
    }

    // 回主畫面呼叫showAllAccountAndPasswordfromSQLite() 更新show1
    @Override
    protected void onStart() {
        super.onStart();
        madeShowFromSQL();
    }

    // 用editText輸入的帳號至SQLite "撈密碼"
    public String getPasswordFromSQLite(String account) {
        String password = "";
        Cursor cursorPassword = SQLiteDb.rawQuery("SELECT "+pwdSQL+" FROM "+tableSQL+" WHERE "+userSQL+" = '"+account+"';", null);
        if (cursorPassword.getCount() > 0){
            cursorPassword.moveToFirst();
            password = cursorPassword.getString(0);
        }
        cursorPassword.close();
        return password;
    }

    // 登入畫面提示全部SQLite帳密
    public void madeShowFromSQL(){;
        Cursor tableAll = SQLiteDb.rawQuery("select * from " + tableSQL + ";", null);
        StringBuilder stringBuilder = new StringBuilder();
        if (tableAll.getCount() > 0) {
            tableAll.moveToFirst();
            do {

                stringBuilder.append("帳/密 : " + tableAll.getString(1) + " / " + tableAll.getString(2) + "\n");
            } while (tableAll.moveToNext());
        }
        tableAll.close();
        textViewShow1.setText(stringBuilder);
    }

}

