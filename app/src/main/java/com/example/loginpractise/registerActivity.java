package com.example.loginpractise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.example.loginpractise.mySQLiteContract;

public class registerActivity extends AppCompatActivity {

    TextView textViewBirthday;
    Button buttonBirthday;

    DatePickerDialog.OnDateSetListener datePicker; //日歷的監聽，獲得選擇的日期
    Calendar calendar = Calendar.getInstance(); //日期的格式
    private Button button_registerOK;
    private EditText editText_registerName,editText_registerAccount,editText_registerPassword,editText_registerCheckPassword,editText_registerEmail,editText_registerPhone,editText_registerAddress;
    private TextView textView_registerBirthday;
    private Spinner spinnerCity,spinnerArea;
    private String cityName;
    private int[] cityCode={R.array.taipei,R.array.keelung,R.array.newtaipei,R.array.yilan,R.array.lienchiang,R.array.hsinchu,R.array.hsinchu_city,
            R.array.taoyuan,R.array.miaoli,R.array.taichung,R.array.changhua,R.array.nantou,R.array.chiayi_city,R.array.chiayi,R.array.yunlin,
            R.array.tainan,R.array.kaohsiung,R.array.pingtung,R.array.penghu,R.array.kinmen,R.array.taitung,R.array.hualien};
    private ArrayAdapter<CharSequence> spinnerAdapterArea;
    private Dialog registerDialog;
    private String cityArea;
    private mySQLiteContract.mySQLiteDbHelper dbHelper;
    private String account,phone,email;
    List<String> accountList = new ArrayList<>();
    List<String> phoneList = new ArrayList<>();
    List<String> emailList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("註冊帳號");


        //---------------以下是日期Dialog選單----------------------------------------
        textViewBirthday = (TextView)findViewById(R.id.textView_register_birthday);
        buttonBirthday = (Button)findViewById(R.id.button_birthday);

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String myformat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.TAIWAN);
                textViewBirthday.setText(sdf.format(calendar.getTime()));

            }
        };

        buttonBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(registerActivity.this,
                        datePicker,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });
        //------------------------以上是日期Dialog選單--------------------------------------------------------//

        //-----------------------------------以下是actionbar-----------------------------//
        ActionBar actBar = getSupportActionBar();
        actBar.setBackgroundDrawable(new ColorDrawable(0xFFF44336));
        actBar.setDisplayHomeAsUpEnabled(true);
        //-----------------------------------以上是actionbar-----------------------------//
        
        button_registerOK = (Button)findViewById(R.id.button_registerOK) ;
        editText_registerName = (EditText)findViewById(R.id.editText_register_Name);
        editText_registerAccount = (EditText)findViewById(R.id.editText_register_account);
        editText_registerPassword = (EditText)findViewById(R.id.editText_register_Password);
        editText_registerCheckPassword = (EditText)findViewById(R.id.editText_register_CheckPassword);
        editText_registerEmail = (EditText)findViewById(R.id.editText_register_Email);
        editText_registerPhone = (EditText)findViewById(R.id.editText_register_Phone);
        editText_registerAddress = (EditText)findViewById(R.id.editText_register_address);

        textView_registerBirthday = (TextView)findViewById(R.id.textView_register_birthday);

        //----------------------------------以下是地址spinner的監聽-----------------------//
        spinnerCity = (Spinner)findViewById(R.id.spinner_city);
        spinnerArea = (Spinner)findViewById(R.id.spinner_area);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityName = parent.getItemAtPosition(position).toString();
                Log.d("main","cityName = "+cityName+"position="+position);
                for(int i=0;i<22;i++){
                    if(i==position){
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[i],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityArea = parent.getItemAtPosition(position).toString();
                Log.d("main","cityArea = "+cityArea+"position="+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //--------------------以上是地址spinner的監聽-----------------------------------------------------------------//

        //------------------以下是手機號碼、Email的規定格式-------------------------------------------//
        String str = new String();
        String phoneformat = "^(09)(\\d{2})(-)?(\\d{3})(-)?(\\d{3})";
        String emailformat = "(.+)(@){1}(\\w+)(\\.){1}(.*)";

        //-------------------以上是手機號碼、Email的規定格式------------------------------------------//



        dbHelper = new mySQLiteContract.mySQLiteDbHelper(registerActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
        Intent intent = getIntent();
        String username = intent.getStringExtra("name");
        Cursor output = db.rawQuery("select * from " + table + ";", null);
        if (output.getCount()>0){    // 若有資料
            output.moveToFirst();    // 移到第 1 筆資料
            do{        // 逐筆讀出資料
               account = output.getString(3);
               phone = output.getString(5);
               email = output.getString(6);
               accountList.add(account);
               phoneList.add(phone);
               emailList.add(email);
               Log.d("main","cursor = "+account+phone+email);
            } while(output.moveToNext());    // 有一下筆就繼續迴圈

        }
        output.close();
        int outputStr = output.getCount();

//        2022-08-26 update : replace userid with _id as primary key, the type of column _id is auto-increment.
//        String userid = "A00".concat(String.valueOf(outputStr + 1));
        //-----------------------------以下是OK按鍵的監聽--------------------------------------//
        button_registerOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_registerName.length()==0){
                    Toast.makeText(registerActivity.this,"請輸入姓名",Toast.LENGTH_SHORT).show();

                }else if(editText_registerAccount.length()==0){
                    Toast.makeText(registerActivity.this,"請輸入帳號",Toast.LENGTH_SHORT).show();

                }else if(editText_registerPassword.length()==0 || editText_registerPassword.length()>12 || editText_registerPassword.length()<6){
                    Toast.makeText(registerActivity.this,"請輸入6-12位密碼",Toast.LENGTH_SHORT).show();

                }else if(editText_registerCheckPassword.length()==0){
                    Toast.makeText(registerActivity.this,"請再次輸入密碼",Toast.LENGTH_SHORT).show();

                }else if(textView_registerBirthday.length()==0){
                    Toast.makeText(registerActivity.this,"請選擇生日",Toast.LENGTH_SHORT).show();

                }else if(editText_registerPhone.length()==0){
                    Toast.makeText(registerActivity.this,"請輸入行動電話",Toast.LENGTH_SHORT).show();

                } else if(editText_registerAddress.length()==0){
                    Toast.makeText(registerActivity.this,"請輸入地址",Toast.LENGTH_SHORT).show();

                }else if(editText_registerEmail.length()==0){
                    Toast.makeText(registerActivity.this,"請輸入Email",Toast.LENGTH_SHORT).show();

                }else if(editText_registerPassword.getText().toString().equals(editText_registerCheckPassword.getText().toString())==false){
                    Toast.makeText(registerActivity.this,"再次入密碼與密碼不相同",Toast.LENGTH_SHORT).show();

                }else if(editText_registerPhone.getText().toString().matches(phoneformat)==false){
                    Toast.makeText(registerActivity.this,"手機號碼輸入錯誤",Toast.LENGTH_SHORT).show();

                }else if(editText_registerEmail.getText().toString().matches(emailformat)==false){
                    Toast.makeText(registerActivity.this,"Email格式錯誤",Toast.LENGTH_SHORT).show();

                } else if(accountList.contains(editText_registerAccount.getText().toString())){
                    Toast.makeText(registerActivity.this,"此帳號已被使用",Toast.LENGTH_SHORT).show();

                }else if(phoneList.contains(editText_registerPhone.getText().toString())){
                    Toast.makeText(registerActivity.this,"此手機號碼已註冊過",Toast.LENGTH_SHORT).show();

                }else if(emailList.contains(editText_registerEmail.getText().toString())){
                    Toast.makeText(registerActivity.this,"此Email已註冊過",Toast.LENGTH_SHORT).show();

                }
                else{
                    //-------------------------------這裡是建立按下OK後的Dialog--------------------------------------//
                    registerDialog = new Dialog(registerActivity.this);
                    registerDialog.setContentView(R.layout.register_ok_dialog);
                    registerDialog.setCancelable(false);

                    //--------------------以下是隱藏密碼-------------------------------------------------------//
                    String password = editText_registerPassword.getText().toString();
                    int password_length =password.length();
                    String hide_password =password.substring(0,3);
                    for(int i=0;i<(password_length-3);i++){
                        hide_password +="*" ;
                    }
                    //---------------------以上是隱藏密碼-----------------------------------------------------//

                    TextView textViewRegisterDialog = (TextView) registerDialog.findViewById(R.id.textView_registerDialog);
                    textViewRegisterDialog.setText("");
                    textViewRegisterDialog.append("姓名 : "+editText_registerName.getText().toString()+"\n");
                    textViewRegisterDialog.append("帳號 : "+editText_registerAccount.getText().toString()+"\n");
                    textViewRegisterDialog.append("密碼 : "+hide_password+"\n");
                    textViewRegisterDialog.append("生日 : "+textView_registerBirthday.getText().toString()+"\n");
                    textViewRegisterDialog.append("行動電話 : "+editText_registerPhone.getText().toString()+"\n");
                    textViewRegisterDialog.append("通訊地址 :\n"+cityName+cityArea+"\n"+editText_registerAddress.getText().toString()+"\n");
                    textViewRegisterDialog.append("Email : "+editText_registerEmail.getText().toString()+"\n");

                    //------------------------------以下是監聽Dialog中的Button-------------------------------------------//
                    Button registerDialogOK = (Button) registerDialog.findViewById(R.id.button_registerDialog_ok);
                    Button registerDialogCancel = (Button) registerDialog.findViewById(R.id.button_registerDialog_cancel);

                    registerDialogOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //----------------------------------以下是把資料傳進資料庫--------------------------------//

//                            db.execSQL("insert into " + table + " values ('" + userid + "','" +
//                                    editText_registerAccount.getText().toString()+ "','"+
//                                    editText_registerPassword.getText().toString()+"','"+
//                                    editText_registerName.getText().toString()+"','"+
//                                    textViewBirthday.getText().toString()+"','"+
//                                    editText_registerPhone.getText().toString()+"','"+
//                                    editText_registerEmail.getText().toString()+"','"+
//                                    cityName+cityArea+editText_registerAddress.getText().toString()+"');");

//                            Log.d("main","insert ="+"insert into " + table + " values ('" + userid + "','" +
//                                    editText_registerAccount.getText().toString()+ "','"+
//                                    editText_registerPassword.getText().toString()+ "','"+
//                                    editText_registerName.getText().toString()+"','"+
//                                    textViewBirthday.getText().toString()+"','"+
//                                    editText_registerPhone.getText().toString()+"','"+
//                                    editText_registerEmail.getText().toString()+"','"+
//                                    cityName+cityArea+editText_registerAddress.getText().toString()+"');");

//                          2022-08-26 update : replace userid with _id as primary key
                            db.execSQL("insert into " + table + "("
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER + ","
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD + ","
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME + ","
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH + ","
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE + ","
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL + ","
                                            + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS
                                            + ")" + " values ('" +
                                            editText_registerAccount.getText().toString()+ "','"+
                                            editText_registerPassword.getText().toString()+ "','"+
                                            editText_registerName.getText().toString()+"','"+
                                            textViewBirthday.getText().toString()+"','"+
                                            editText_registerPhone.getText().toString()+"','"+
                                            editText_registerEmail.getText().toString()+"','"+
                                            cityName+cityArea+editText_registerAddress.getText().toString()+"');");

                            Log.d("main","insert ="+"insert into " + table + "("
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER + ","
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD + ","
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME + ","
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH + ","
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE + ","
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL + ","
                                    + mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS
                                    + ") values ('" +
                                    editText_registerAccount.getText().toString()+ "','"+
                                    editText_registerPassword.getText().toString()+ "','"+
                                    editText_registerName.getText().toString()+"','"+
                                    textViewBirthday.getText().toString()+"','"+
                                    editText_registerPhone.getText().toString()+"','"+
                                    editText_registerEmail.getText().toString()+"','"+
                                    cityName+cityArea+editText_registerAddress.getText().toString()+"');");

                            db.close();
                            dbHelper.close();
                            //----------------------------以上是把資料傳進資料庫---------------------------------------------------//
                            registerDialog.dismiss();
                            Toast.makeText(registerActivity.this,"註冊成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                    registerDialogCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            registerDialog.dismiss();
                        }
                    });
                    //---------------------以上是監聽Dialog中的Button----------------------------------------------------------//
                    registerDialog.show();
                    registerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

            }
        });
        //-------------------------------------以上是OK按鍵的監聽----------------------------------------------------------------------------//
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}