package com.example.loginpractise;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                switch (position){
                    case 0:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[0],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 1:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[1],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 2:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[2],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 3:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[3],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 4:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[4],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 5:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[5],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 6:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[6],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 7:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[7],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 8:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[8],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 9:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[9],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 10:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[10],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 11:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[11],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 12:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[12],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 13:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[13],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 14:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[14],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 15:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[15],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 16:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[16],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 17:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[17],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 18:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[18],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 19:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[19],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 20:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[20],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;

                    case 21:
                        spinnerAdapterArea = ArrayAdapter.createFromResource(registerActivity.this,cityCode[21],R.layout.simple_spinner_item);
                        spinnerAdapterArea.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        spinnerArea.setAdapter(spinnerAdapterArea);
                        break;
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
                }
                else{
                    //-------------------------------這裡是建立按下OK後的Dialog--------------------------------------//
                    registerDialog = new Dialog(registerActivity.this);
                    registerDialog.setContentView(R.layout.register_ok_dialog);
                    registerDialog.setCancelable(false);

                    TextView textViewRegisterDialog = (TextView) registerDialog.findViewById(R.id.textView_registerDialog);
                    textViewRegisterDialog.setText("");
                    textViewRegisterDialog.append("姓名 :"+editText_registerName.getText().toString()+"\n");
                    textViewRegisterDialog.append("帳號 :"+editText_registerAccount.getText().toString()+"\n");
                    textViewRegisterDialog.append("密碼 :"+editText_registerPassword.getText().toString()+"\n");
                    textViewRegisterDialog.append("生日 :"+textView_registerBirthday.getText().toString()+"\n");
                    textViewRegisterDialog.append("行動電話 :"+editText_registerPhone.getText().toString()+"\n");
                    textViewRegisterDialog.append("通訊地址 :\n"+cityName+cityArea+"\n"+editText_registerAddress.getText().toString()+"\n");
                    textViewRegisterDialog.append("Email :"+editText_registerEmail.getText().toString()+"\n");

                    //------------------------------以下是監聽Dialog中的Button-------------------------------------------//
                    Button registerDialogOK = (Button) registerDialog.findViewById(R.id.button_registerDialog_ok);
                    Button registerDialogCancel = (Button) registerDialog.findViewById(R.id.button_registerDialog_cancel);

                    registerDialogOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //----------------------------------以下是把資料傳進資料庫--------------------------------//
                            dbHelper = new mySQLiteContract.mySQLiteDbHelper(registerActivity.this);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            String table = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
                            Intent intent = getIntent();
                            String username = intent.getStringExtra("name");
                            Cursor output = db.rawQuery("select * from " + table + ";", null);
                            int outputStr = output.getCount();
                            Log.d("main", "select output=" + outputStr);

                            String userid = "A00".concat(String.valueOf(outputStr + 1));

                            db.execSQL("insert into " + table + " values ('" + userid + "','" +editText_registerName.getText().toString()+ "','"+
                                    editText_registerPassword.getText().toString()+"','"+editText_registerAccount.getText().toString()+"','"+
                                    textViewBirthday.getText().toString()+"','"+editText_registerPhone.getText().toString()+"','"+cityName+cityArea+
                                    editText_registerEmail.getText().toString()+"','"+cityName+cityArea+editText_registerAddress.getText().toString()+"');");

                            Log.d("main","insert ="+"insert into " + table + " values ('" + userid + "','" +
                                    editText_registerName.getText().toString()+ "','"+
                                    editText_registerPassword.getText().toString()+ "','"+
                                    editText_registerAccount.getText().toString()+"','"+
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
                }

            }
        });
        //-------------------------------------以上是OK按鍵的監聽----------------------------------------------------------------------------//


    }
}