package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class registerActivity extends AppCompatActivity {

    TextView textViewBirthday;
    Button buttonBirthday;

    DatePickerDialog.OnDateSetListener datePicker; //日歷的監聽，獲得選擇的日期
    Calendar calendar = Calendar.getInstance(); //日期的格式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textViewBirthday = (TextView)findViewById(R.id.textView_birthday);
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


    }
}