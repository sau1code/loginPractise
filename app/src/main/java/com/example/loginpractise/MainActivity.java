package com.example.loginpractise;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextAccount, editTextPassword;
    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        buttonLogin = (Button)findViewById(R.id.button_login);
        textViewRegister = (TextView)findViewById(R.id.textView_register);
        editTextAccount = (EditText)findViewById(R.id.EditText_account);
        editTextPassword = (EditText)findViewById(R.id.EditText_password);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acco = editTextAccount.getText().toString();
                Toast.makeText(MainActivity.this, acco, Toast.LENGTH_SHORT).show();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = editTextPassword.getText().toString();
                Toast.makeText(MainActivity.this, pass, Toast.LENGTH_SHORT).show();
            }
        });

    }
}