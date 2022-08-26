package com.example.loginpractise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class memberActivity extends AppCompatActivity {

    private TextView textViewMemberTempshow;
    private mySQLiteContract.mySQLiteDbHelper dbHelper;
    private EditText editTextPassword,editTextCheckPassword,editTextName,editTextBirth;
    private EditText editTextPhone,editTextAddress,editTextEmail;
    private String userpwd;
    private Button buttonDelete,buttonModify,buttonSubmit;
    private SQLiteDatabase db;
    private ImageButton imageButtonBirthday;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener datePicker;

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

        //get the user account data as String variable "user"
        String user = intent.getStringExtra("name");
        textViewMemberTempshow.setText("使用者帳號 : " + user);

//      disable all EditText widgets
        editTextPassword = (EditText) findViewById(R.id.editText_member_password);
        editTextPassword.setEnabled(false);
        editTextCheckPassword = (EditText) findViewById(R.id.editText_member_password2);
        editTextCheckPassword.setEnabled(false);
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
        String whereCondition = " where " + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER + "=" + "'" + user + "'";
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
        editTextCheckPassword.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD));
        editTextName.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME));
        editTextBirth.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH));
        editTextPhone.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE));
        editTextAddress.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS));
        editTextEmail.setText(alluserInfor.get(mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL));


        buttonDelete = (Button) findViewById(R.id.button_member_delete);
        buttonModify = (Button) findViewById(R.id.button_member_modify);
        buttonSubmit = (Button) findViewById(R.id.button_member_submit);
        imageButtonBirthday = (ImageButton)findViewById(R.id.imageButton_member_birthday);

//      set two buttons for submitting data and picking a date
        buttonSubmit.setVisibility(View.INVISIBLE);
        imageButtonBirthday.setVisibility(View.INVISIBLE);

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

        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  set the title of memberActivity
                    setTitle("會員資料修改頁面");
//                  enable EdiText
                    editTextPassword.setEnabled(true);
                    editTextCheckPassword.setEnabled(true);
                    editTextName.setEnabled(true);
//                    editTextBirth.setEnabled(true);
                    editTextPhone.setEnabled(true);
                    editTextAddress.setEnabled(true);
                    editTextEmail.setEnabled(true);
//                  set two buttons for submiting data and pick birthday to visible
//                  and the others set to invisible
                    buttonSubmit.setVisibility(View.VISIBLE);
                    imageButtonBirthday.setVisibility(View.VISIBLE);
                    buttonDelete.setVisibility(View.INVISIBLE);
                    buttonModify.setVisibility(View.INVISIBLE);
//                  set EditText for entering password
                    Log.d("main", "input-type=" + editTextPassword.getInputType());//input-type=129
                    editTextCheckPassword.setText("");
                    calendar = Calendar.getInstance(Locale.TAIWAN);
                    datePicker = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myformat = "yyyy/MM/dd";
                            SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.TAIWAN);
                            editTextBirth.setText(sdf.format(calendar.getTime()));
//                            avoid user change the format of day, EditText which input birthday set disabled
//                            editTextBirth.setEnabled(false);
                        }
                    };

                    imageButtonBirthday.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog dialog = new DatePickerDialog(memberActivity.this,
                                    datePicker,calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            );
                            dialog.show();
                        }
                    });
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String muserPassword = editTextPassword.getText().toString().trim();
                String muserName = editTextName.getText().toString().trim();
                String muserBirth = editTextBirth.getText().toString().trim();
                String muserPhone = editTextPhone.getText().toString().trim();
                String muserAddress = editTextAddress.getText().toString().trim();
                String muserEmail = editTextEmail.getText().toString().trim();

                //To check the format of the password , phone number and email,
//                set regular expression to match desirable input
                String passwordformat = "^.{6,12}$";
                String phoneformat = "^09\\d{2}-?\\d{3}-?\\d{3}$";
                String emailformat = "^.+@\\w+\\..*$";

//              put error message into List
                List<String> msg = new ArrayList<>();

//              When user incomplete data input :
                if(muserPassword.isEmpty()||muserName.isEmpty()||muserBirth.isEmpty()||muserPhone.isEmpty()
                        ||muserAddress.isEmpty()||muserEmail.isEmpty()){
                    msg.add("請完整輸入會員資料");
                }else{
//                    When user complete data input :
                    //check the format of the password
                    Log.d("main","muserPassword.matches="+muserPassword.matches("^.{6,12}$"));
                    if(muserPassword.matches(passwordformat)==false){
                        msg.add("請輸入6到12個文字作為密碼");
                    }else if(editTextCheckPassword.getText().toString().equals(muserPassword)==false){
                        msg.add("請再輸入相同密碼作為驗證");
                    }
//                  check the format of the phone number and email,
                    if(muserPhone.matches(phoneformat)==false){
                        msg.add("請輸入完整且正確格式的手機號碼");
                    }
                    if(muserEmail.matches(emailformat)==false){
                        msg.add("請輸入完整email");
                    }

//                  行動電話不重複---------------------------------------------
                    String whereConditionPhone = " where " + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE + "='" + muserPhone+"'";
                    Cursor phoneCheckQuery = db.rawQuery("select * from " + table + whereConditionPhone, null);
                    Log.d("main","SQL : "+"select * from " + table + whereConditionPhone+"["+phoneCheckQuery.getCount()+"]");
                    if(phoneCheckQuery.moveToFirst()) {
                        Log.d("main", "column 1 =" + phoneCheckQuery.getString(1));
                        String userPhoneCheck = phoneCheckQuery.getString(1);
                        Log.d("main", "phone query=" + phoneCheckQuery.getCount() + " " + userPhoneCheck);
                        if(phoneCheckQuery.getCount()>0 && userPhoneCheck.equals(user)==false){
                            msg.add("此電話號碼已使用\n請重新輸入電話號碼");
                        }
                    }
                    phoneCheckQuery.close();
//                  email不重複-----------------------------------------------
                    String whereConditionEmail = " where " + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL + "='" + muserEmail+"'";
                    Cursor emailCheckQuery = db.rawQuery("select * from " + table + whereConditionEmail, null);
                    Log.d("main","SQL : "+"select * from " + table + whereConditionEmail+"["+emailCheckQuery.getCount()+"]");
                    if(emailCheckQuery.moveToFirst()){
                        String userEmailCheck = emailCheckQuery.getString(1);
                        Log.d("main", "email query=" + emailCheckQuery.getCount() + " " + userEmailCheck);
                        if(emailCheckQuery.getCount()>0 && userEmailCheck.equals(user)==false){
                            msg.add("此email已使用\n請重新輸入email");
                        }
                    }
                    emailCheckQuery.close();
//              -----------------------------------------------
                }// the end of input check
//              msg.size()>0 : need to print error message because users enter wrong information or incomplete input.
                if(msg.size()>0) {
                    Log.d("main", "msg=" + msg.toString().replaceAll("\\[|\\]", ""));
//                  clear the "[" ,"]" and "," character -----
                    String message = msg.toString().replaceAll("\\[|\\]", "");
                    message = message.replaceAll(" *, *", "\n");
//                  ------------------------------------------
                    Toast.makeText(memberActivity.this, message, Toast.LENGTH_SHORT).show();
                }else{
//                    When users complete input correctly :
//                    String muserPassword = editTextPassword.getText().toString().trim();
//                    String muserName = editTextName.getText().toString().trim();
//                    String muserBirth = editTextBirth.getText().toString().trim();
//                    String muserPhone = editTextPhone.getText().toString().trim();
//                    String muserAddress = editTextAddress.getText().toString().trim();
//                    String muserEmail = editTextEmail.getText().toString().trim();
//                    update customers set where;
                    Log.d("main","SQL UPDATE:\n"+"update " + table + " set "
                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD + "='" + muserPassword + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME + "='" + muserName + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH + "='" + muserBirth + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE + "='" + muserPhone + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS + "='" + muserAddress + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL + "='" + muserEmail + "'"
                            + whereCondition);
                    db.execSQL("update " + table + " set "
                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD + "='" + muserPassword + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME + "='" + muserName + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH + "='" + muserBirth + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE + "='" + muserPhone + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS + "='" + muserAddress + "'"
                            + "," + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL + "='" + muserEmail + "'"
                            + whereCondition);
                    Toast.makeText(memberActivity.this,"會員資料修改成功!!",Toast.LENGTH_LONG).show();
                    finish();
                }


            }
        });
//      -----------auto create user id start with "A00" --------------
//     get the number of total rows of records in table customers and return a integer variable
//      1. SQLiteDatabase.rawQuery function can execute SQL command and return output as Cursor object
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

//      5. SQLiteDatabase.execSQL function can execute SQL commands which don't return data .
//        5.1 The SQL command parameter in db.execSQL function : insert new user data to table "customers"
//        db.execSQL("insert into " + table + " values ('" + userid + "','" + user + "','123456','apple','1986/3/28','0958499577','test@gmail.com','桃園市');");



    }


//  set ActionBar function : return to front page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//    stop using database
    @Override
    protected void onDestroy() {
        db.close();
        dbHelper.close();
        super.onDestroy();
    }
}