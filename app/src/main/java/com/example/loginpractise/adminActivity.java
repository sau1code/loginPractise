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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

    private final String tableSQL = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
//    2022-08-26 update : mySQLiteEntry.COLUMN_NAME_ID => _id
    private final String idSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ID;
    private final String userSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USER;
    private final String pwdSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PWD;
    private final String nameSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_USERNAME;
    private final String birthSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_BIRTH;
    private final String phoneSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_PHONE;
    private final String emailSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL;
    private final String addrSQL = mySQLiteContract.mySQLiteEntry.COLUMN_NAME_ADDRESS;
    private TextView textViewSum, textViewBirthday;
    private ImageView imageViewBirthday;
    private EditText editTextSearch;
    private CheckBox checkBoxCity;
    private Spinner spinnerCity;
    private RecyclerView recyclerView;
    private String cityInput, birthdayInput, searchInput;
    private Boolean checkBoxFlag;
    private mySQLiteContract.mySQLiteDbHelper SQLiteHelper;
    private SQLiteDatabase SQLiteDb;
    private RecyclerViewAdapter adapter;
    private List<Map<String, String>> mapList;
    private Map<String, String> memberMap;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //---------------------------------------------
        // findView
        // init set
        //---------------------------------------------
        // ???????????? ??????
        // checkBoxCity?????? ?????? (???spinner???????????????)
        // spinnerCity?????? ??????
        // ??????Dialog ??????
        // ??????icon ??????
        //--------------------------- (??????????????????) --
        // ????????????
        // ???????????????madeShowFromSQL()
        // ??????????????????SQLite????????????List??????Adapter??????recyclerView
        //---------------------------------------------

        // findView
        textViewSum = (TextView)findViewById(R.id.textView_admin_sum);
        editTextSearch = (EditText)findViewById(R.id.editText_admin_search);
        textViewBirthday = (TextView)findViewById(R.id.textView_admin_birthday);
        imageViewBirthday = (ImageView)findViewById(R.id.imageView_admin_birthday);
        spinnerCity = (Spinner)findViewById(R.id.spinner_admin_city);
        checkBoxCity = (CheckBox)findViewById(R.id.checkBox_admin_city);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_admin_show);

        // init set
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F44336")));
        setTitle("????????? : " + getIntent().getStringExtra("name"));
        getWindow().setStatusBarColor(0xFFF44336);      // ?????????StatusBar??????
        SQLiteHelper = new mySQLiteContract.mySQLiteDbHelper(adminActivity.this);
        SQLiteDb = SQLiteHelper.getWritableDatabase();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        calendar = Calendar.getInstance();   //???????????????
        cityInput = "";
        birthdayInput = "";
        searchInput = "";
        checkBoxFlag = false;

        // ???????????? ??????
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchInput = editTextSearch.getText().toString();
                madeShowFromSQL();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // checkBoxCity?????? ?????? (???spinner???????????????)
        checkBoxCity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBoxFlag = b;
                madeShowFromSQL();
            }
        });

        // spinnerCity?????? ??????
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityInput = parent.getItemAtPosition(position).toString();
                madeShowFromSQL();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // ??????Dialog ??????
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
                birthdayInput = simpleDateFormat.format(calendar.getTime());
                textViewBirthday.setText(birthdayInput);
                imageViewBirthday.setImageDrawable(getDrawable(R.drawable.xicon));
                madeShowFromSQL();
            }
        };

        // ??????icon ??????
        imageViewBirthday.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;    // ????????????????????????
            @Override
            public void onClick(View v) {
                if (flag) {
                    DatePickerDialog dialog = new DatePickerDialog(adminActivity.this,
                            datePicker, calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    dialog.show();
                    flag = false;
                } else {
                    textViewBirthday.setText("");
                    imageViewBirthday.setImageDrawable(getDrawable(R.drawable.birsdayicon));
                    birthdayInput = "";
                    madeShowFromSQL();
                    flag = true;
                }
            }
        });

    }

    // ????????????
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // ???????????????madeShowFromSQL();
    @Override
    protected void onStart() {
        super.onStart();
        madeShowFromSQL();
    }

    // ??????????????????SQLite????????????List??????Adapter??????recyclerView
    public void madeShowFromSQL() {
        // ??????????????????SQL??????
        StringBuilder SQLSyntax = new StringBuilder("SELECT * FROM "+tableSQL);
        Boolean hasPrev = false;

        // (??????????????????????????? ??????+ WHERE)
        if (birthdayInput.length() + searchInput.length() > 0 | checkBoxFlag) {
            SQLSyntax.append(" WHERE ");
            // (????????????????????? ??????+ LIKE cityInput)
            if (checkBoxFlag & cityInput.length() > 0) {
                SQLSyntax.append(addrSQL+" LIKE '"+cityInput+"%'");
                hasPrev = true;
            }
            // (????????????????????? ??????+ < 'birthdayInput')
            if (birthdayInput.length() > 0) {
                SQLSyntax.append((hasPrev)?" AND ":"");
                SQLSyntax.append(birthSQL+" < '"+birthdayInput+"'");
                hasPrev = true;
            }
            // (????????????????????? ??????+ LIKE '%searchInput%')
            if (searchInput.length() > 0) {
                SQLSyntax.append((hasPrev)?" AND ":"");
                SQLSyntax.append("("+idSQL+" LIKE '%"+searchInput+"%' OR "+userSQL+" LIKE '%"+searchInput+"%' OR "+pwdSQL+" LIKE '%"+searchInput+"%' OR "+nameSQL+" LIKE '%"+searchInput+"%' OR "+birthSQL+" LIKE '%"+searchInput+"%' OR "+phoneSQL+" LIKE '%"+searchInput+"%' OR "+emailSQL+" LIKE '%"+searchInput+"%' OR "+addrSQL+" LIKE '%"+searchInput+"%')");
            }
        }
        SQLSyntax.append(";");
        Cursor cursorTable = SQLiteDb.rawQuery(SQLSyntax.toString(), null);

        // ???????????????????????????Cursor ???????????????List
        mapList = new ArrayList<>();
        if (cursorTable.getCount() > 0) {
            cursorTable.moveToFirst();
            String[] mapKey = new String[] { "userid", "user", "password", "username", "userbirth", "cellphone", "useremail", "useraddress" };
            do {
                memberMap = new HashMap<>();
                for (int i = 0; i < cursorTable.getColumnCount(); i++) {
                    memberMap.put(mapKey[i], cursorTable.getString(i));
                }
                mapList.add(memberMap);
            } while (cursorTable.moveToNext());
        }
        cursorTable.close();

        // ????????????List??????Adapter??????recyclerView
        adapter = new RecyclerViewAdapter(mapList);
        recyclerView.setAdapter(adapter);
        textViewSum.setText("???:" + adapter.getItemCount() + "???");
    }

}