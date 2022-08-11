package com.example.loginpractise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class adminActivity extends AppCompatActivity {

    private TextView textViewAdminTempshow;
    private mySQLiteContract.mySQLiteDbHelper dbHelper;
    private Map<String, String> memberMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ActionBar actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        actBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F44336")));
        setTitle("管理員 : " + getIntent().getStringExtra("name"));

        // 暫時-使用者帳密 Map<帳號, 密碼>
        memberMap = new HashMap<>();
        for (int i = 1; i < 100; i++) {
            memberMap.put("member" + i, "member" + i);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView_admin_show);
        textViewAdminTempshow = (TextView)findViewById(R.id.textView_admin_tempshow);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(memberMap);
        recyclerView.setAdapter(adapter);
        textViewAdminTempshow.setText("共:" + adapter.getItemCount() + "筆");


        //------------------------以下是日期Dialog選單--------------------------------------------------------//
        DatePickerDialog.OnDateSetListener datePicker;  //日歷的監聽，獲得選擇的日期
        Calendar calendar = Calendar.getInstance();     //日期的格式

        TextView te = (TextView)findViewById(R.id.textView_1);
        ImageView im = (ImageView)findViewById(R.id.imageView_1);

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String myformat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.TAIWAN);
                te.setText(sdf.format(calendar.getTime()));

            }
        };

        im.setOnClickListener(new View.OnClickListener() {
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
        //------------------------以上是日期Dialog選單--------------------------------------------------------//




        //------------------------以下是縣市Spinner--------------------------------------------------------//
        Spinner spinner = (Spinner)findViewById(R.id.spinner_city2);
        String city;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //------------------------以上是縣市Spinner--------------------------------------------------------//




//        dbHelper = new mySQLiteContract.mySQLiteDbHelper(adminActivity.this);
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String table = mySQLiteContract.mySQLiteEntry.TABLE_NAME;
//
//        ((Button)findViewById(R.id.button_1)).setOnClickListener(v -> {
//            Cursor cursor = db.rawQuery("select * from " + table + ";", null);
//            int cursorCount = cursor.getCount();
//
//            StringBuilder sb = new StringBuilder();
//            cursor.moveToFirst();
//            Log.d("main", ""+cursor.getColumnCount());
//            Log.d("main", ""+cursorCount);
//            do{
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    sb.append(cursor.getString(i)+"\n");
//                }
//                sb.append("-----------------\n");
//            } while(cursor.moveToNext());
//            ((TextView)findViewById(R.id.textView_output)).setText(sb);
//            cursor.close();
//        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}