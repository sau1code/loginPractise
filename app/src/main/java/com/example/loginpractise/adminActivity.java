package com.example.loginpractise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class adminActivity extends AppCompatActivity {

    private String spinnerCityName = "", calendarBirthday = "", searchInput = "";
    private TextView textViewAdminItemCount, textViewAdminBirthday;
    private ImageView imageViewAdminBirthday;
    private EditText editTextAdminSearch;
    private CheckBox checkBoxAdmin;
    private Boolean checkBoxflag = false;
    private SQLiteDatabase SQLiteDb;
    private RecyclerViewAdapter adapter;
    private Map<String, String> memberMap;
    private List<Map<String, String>> mapList;
    private mySQLiteContract.mySQLiteDbHelper SQLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // set ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F44336")));
        setTitle("管理員 : " + getIntent().getStringExtra("name"));

        // findViewById
        textViewAdminBirthday = (TextView)findViewById(R.id.textView_admin_birthday);
        textViewAdminItemCount = (TextView)findViewById(R.id.textView_admin_ItemCount);
        imageViewAdminBirthday = (ImageView)findViewById(R.id.imageView_admin_birthday);
        editTextAdminSearch = (EditText)findViewById(R.id.editText_admin_search);
        checkBoxAdmin = (CheckBox)findViewById(R.id.checkBox_admin);

        // 搜尋監聽
        editTextAdminSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchInput = editTextAdminSearch.getText().toString();
                adminSQLite();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        checkBoxAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBoxflag = b;
                adminSQLite();
            }
        });

        // City Spinner
        Spinner spinner = (Spinner)findViewById(R.id.spinner_admin_city);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCityName = parent.getItemAtPosition(position).toString();
                adminSQLite();
                Log.d("sql", spinnerCityName);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //------------------------ 以下是日期Dialog選單 ------------------------//
        Calendar calendar = Calendar.getInstance(); //日期的格式
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
                calendarBirthday = simpleDateFormat.format(calendar.getTime());
                textViewAdminBirthday.setText(calendarBirthday);
                adminSQLite();
                Log.d("sql", calendarBirthday);
            }
        };

        imageViewAdminBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(adminActivity.this,
                        datePicker,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
            }
        });
        //------------------------ 以上是日期Dialog選單 ------------------------//

    }

    // 上一頁箭頭的監聽
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // 每次進頁面呼叫 adminSQLite();
    @Override
    protected void onStart() {
        super.onStart();
        adminSQLite();
    }

    // 取SQLite中值製作List傳進Adapter
    public void adminSQLite() {
        String[] mapKey = new String[] { "userid", "user", "password", "username", "userbirth", "cellphone", "useremail", "useraddress" };
        SQLiteHelper = new mySQLiteContract.mySQLiteDbHelper(adminActivity.this);
        SQLiteDb = SQLiteHelper.getWritableDatabase();
        String tableSQL = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
        String idSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ID;
        String userSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER;
        String pwdSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD;
        String nameSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME;
        String birthSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH;
        String phoneSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE;
        String emailSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL;
        String addrSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS;
        StringBuilder SQLSyntax = new StringBuilder("SELECT * FROM "+tableSQL);
        Boolean hasPrev = false;

        if (calendarBirthday.length() + searchInput.length() > 0 | (checkBoxflag & spinnerCityName.length() > 0)) {
            SQLSyntax.append(" WHERE ");
            if (checkBoxflag & spinnerCityName.length() > 0) {
                SQLSyntax.append(addrSQL+" LIKE '"+spinnerCityName+"%'");
                hasPrev = true;
            }
            if (calendarBirthday.length() > 0) {
                SQLSyntax.append((hasPrev)?" AND ":"");
                SQLSyntax.append(birthSQL+" < '"+calendarBirthday+"'");
                hasPrev = true;
            }
            if (searchInput.length() > 0) {
                SQLSyntax.append((hasPrev)?" AND ":"");
                SQLSyntax.append("("+idSQL+" LIKE '%"+searchInput+"%' OR "+userSQL+" LIKE '%"+searchInput+"%' OR "+pwdSQL+" LIKE '%"+searchInput+"%' OR "+nameSQL+" LIKE '%"+searchInput+"%' OR "+birthSQL+" LIKE '%"+searchInput+"%' OR "+phoneSQL+" LIKE '%"+searchInput+"%' OR "+emailSQL+" LIKE '%"+searchInput+"%' OR "+addrSQL+" LIKE '%"+searchInput+"%')");
            }
        }
        SQLSyntax.append(";");
        Cursor cursorTable = SQLiteDb.rawQuery(SQLSyntax.toString(), null);

        mapList = new ArrayList<>();
        if (cursorTable.getCount() > 0) {
            cursorTable.moveToFirst();
            do {
                memberMap = new HashMap<>();
                for (int i = 0; i < cursorTable.getColumnCount(); i++) {
                    memberMap.put(mapKey[i], cursorTable.getString(i));
                }
                mapList.add(memberMap);
            } while (cursorTable.moveToNext());
        }
        cursorTable.close();

        RecyclerView recyclerView = findViewById(R.id.recyclerView_admin_show);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new RecyclerViewAdapter(mapList);
        recyclerView.setAdapter(adapter);
        textViewAdminItemCount.setText("共:" + adapter.getItemCount() + "筆");
    }

}