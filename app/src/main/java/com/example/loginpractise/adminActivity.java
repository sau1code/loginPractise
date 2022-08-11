package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class adminActivity extends AppCompatActivity {

    private TextView textViewAdminTempshow;
    private mySQLiteContract.mySQLiteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();

        setTitle("activity_admin");
        Intent intent = getIntent();
        textViewAdminTempshow = (TextView)findViewById(R.id.textView_admin_tempshow);
        textViewAdminTempshow.setText("管理員帳號 : "+intent.getStringExtra("name"));

        dbHelper = new mySQLiteContract.mySQLiteDbHelper(adminActivity.this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = mySQLiteContract.mySQLiteEntry.TABLE_NAME;

        ((Button)findViewById(R.id.button_1)).setOnClickListener(v -> {
            Cursor cursor = db.rawQuery("select * from " + table + ";", null);
            int cursorCount = cursor.getCount();

            StringBuilder sb = new StringBuilder();
            cursor.moveToFirst();
            do{
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    sb.append(cursor.getString(i)+"\n");
                }
                sb.append("-----------------\n");
            } while(cursor.moveToNext());
            ((TextView)findViewById(R.id.textView_output)).setText(sb);
            cursor.close();
        });


    }
}