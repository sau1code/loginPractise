package com.example.loginpractise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class memberActivity extends AppCompatActivity {

    private TextView textViewMemberTempshow;
    private mySQLiteContract.mySQLiteDbHelper dbHelper;
    private EditText editTextPassword,editTextPassword2,editTextName,editTextBirth;
    private EditText editTextPhone,editTextAddress,editTextEmail;
    private String userpwd;
    private Button buttonDelete,buttonModify;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
//      set the title of memberActivity
        setTitle("會員資料檢視頁面");
//      create ActionBar to return to front page
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        Drawable imageBackground = getResources().getDrawable(R.drawable.night_sky, null);
        actBar.setBackgroundDrawable(imageBackground);

//      receive intent: contain the data of user name and password
        Intent intent = getIntent();
        textViewMemberTempshow = (TextView) findViewById(R.id.textView_member_tempshow);
        textViewMemberTempshow.setText("使用者帳號 : " + intent.getStringExtra("name"));
        //get the user name data as String variable "username"
        String username = intent.getStringExtra("name");

//      disable all EditText widgets
        editTextPassword = (EditText) findViewById(R.id.editText_member_password);
        editTextPassword.setEnabled(false);
        editTextPassword2 = (EditText) findViewById(R.id.editText_member_password2);
        editTextPassword2.setEnabled(false);
        editTextName=(EditText) findViewById(R.id.editText_member_name);
        editTextName.setEnabled(false);
        editTextBirth = (EditText) findViewById(R.id.editText_member_birth);
        editTextBirth.setEnabled(false);
        editTextPhone = (EditText) findViewById(R.id.editText_member_phone);
        editTextPhone.setEnabled(false);
        editTextAddress=(EditText) findViewById(R.id.editText_member_address);
        editTextAddress.setEnabled(false);
        editTextEmail=(EditText) findViewById(R.id.editText_member_email);
        editTextEmail.setEnabled(false);

//      class mySQLiteContract.mySQLiteDbHelper :
//      create database "Demo.db" and table "customers"
        dbHelper = new mySQLiteContract.mySQLiteDbHelper(memberActivity.this);

//      set database to writable mode and
//      return SQLiteDatabase object "db" to execute SQL command
        db = dbHelper.getWritableDatabase();

//      Note: define the name of table and column as attributes in  class mySQLiteContract.mySQLiteEntry
//      get table name : "customers"
        String table = mySQLiteContract.mySQLiteEntry.TABLE_NAME;

//      select user information in database
        String whereCondition = " where " + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER + "=" + "'" + username + "'";
        Cursor userInfor = db.rawQuery("select * from " + table + whereCondition + ";", null);
        int rowCount = userInfor.getCount();
        Log.d("main","rowCount="+rowCount);
//      -------------------put user information from Cursor object to Map-------------------
        Map<String, String> alluserInfor = new HashMap<>();
        //    資料表名稱 : customers
//    欄位中文名稱 欄位名稱    Cursor Index
//    id           userid       0
//    帳號 	       user         1
//    密碼 	       password     2
//    姓名 	       username     3
//    生日 	       userbirth    4
//    手機 	       cellphone    5
//    Email 	   useremail    6
//    住址 	       useraddress  7
        if(userInfor.moveToFirst()){
            alluserInfor.put(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD,userInfor.getString(2));
            alluserInfor.put(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME,userInfor.getString(3));
            alluserInfor.put(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH,userInfor.getString(4));
            alluserInfor.put(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE,userInfor.getString(5));
            alluserInfor.put(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS,userInfor.getString(7));
            alluserInfor.put(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL,userInfor.getString(6));
        }
        Log.d("main","Map size="+alluserInfor.size());
//      close Cursor
        userInfor.close();
//     -----------------------------------------------------------------------------------------------
//      set text in EditText widgets for displaying user information to user
        editTextPassword.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD));
        editTextPassword2.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD));
        editTextName.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME));
        editTextBirth.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH));
        editTextPhone.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE));
        editTextAddress.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS));
        editTextEmail.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL));


        buttonDelete = (Button) findViewById(R.id.button_member_delete);
        buttonModify = (Button) findViewById(R.id.button_member_modify);
//      monitoring button (delete user account)
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(memberActivity.this);
                builder.setTitle("刪除會員帳號");
                builder.setMessage("請您確認是否需刪除本帳號?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.execSQL("delete from "+table+" "+whereCondition+";");
                        finish();
                    }
                });
                builder.show();
            }
        });
//      -----------auto create user id start with "A00" --------------
//     get the number of total rows of records in table customers and return a integer variable
//      1. SQLiteDatabase.rawQuery function can exacute SQL command and return output as Cursor object
//        1.1 The SQL command parameter in db.rawQuery function : select all of data in table "customers"
        Cursor output = db.rawQuery("select * from " + table + ";", null);
//      2. Cursor.getCount function  can return the number of total rows of records in Cursor object as integer variable
        int outputStr = output.getCount();
//      close Cursor
        output.close();
//      3. check the output of Cursor.getCount function
//        Log.d("main", "select output=" + outputStr);
//      4. create new userid which start with "A00"
//      and combine a number which is the number of total rows of records plus one
        String userid = "A00".concat(String.valueOf(outputStr + 1));

//      5. SQLiteDatabase.execSQL function can exacute SQL commands which don't return data .
//        5.1 The SQL command parameter in db.execSQL function : insert new user data to table "customers"
//        db.execSQL("insert into " + table + " values ('" + userid + "','" + username + "','123456','apple','1986/3/28','0958499577','test@gmail.com','桃園市');");



    }


//  set ActionBar function : return to front page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//    stop
    @Override
    protected void onDestroy() {
        db.close();
        dbHelper.close();
        super.onDestroy();
    }
}