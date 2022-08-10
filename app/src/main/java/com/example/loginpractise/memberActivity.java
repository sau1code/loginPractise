package com.example.loginpractise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.loginpractise.mySQLiteContract;

public class memberActivity extends AppCompatActivity {

    private TextView textViewMemberTempshow;
    private mySQLiteContract.mySQLiteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
//      create table
        dbHelper = new mySQLiteContract.mySQLiteDbHelper(memberActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 以下 "可"隨意刪除
        setTitle("activity_member");
        Intent intent = getIntent();
        String username=intent.getStringExtra("name");
//        db.execSQL("insert into customer values ('A001','"+username+"','123456','apple','1986/3/28','0958499577','test@gmail.com','桃園市');");
        textViewMemberTempshow = (TextView)findViewById(R.id.textView_member_tempshow);
        textViewMemberTempshow.setText("使用者帳號 : " + intent.getStringExtra("name"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}