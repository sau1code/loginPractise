package com.example.loginpractise;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

// mySQLite (翔雲)
public final class mySQLiteContract {
//  [reference : https://developer.android.com/training/data-storage/sqlite]
    private mySQLiteContract() {
    }
    //    資料庫名稱 : Demo
//
//    資料表名稱 : customers
//    欄位中文名稱 欄位名稱
//    id           userid
//    帳號 	       user
//    密碼 	       password
//    姓名 	       username
//    生日 	       userbirth
//    手機 	       cellphone
//    Email 	   useremail
//    住址 	       useraddress
    public static class mySQLiteEntry implements BaseColumns {
        public static final String TABLE_NAME = "customers";
        public static final String COLUMN_NAME_ID = "userid";
        public static final String COLUMN_NAME_USER = "user";
        public static final String COLUMN_NAME_PWD = "password";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_BIRTH = "userbirth";
        public static final String COLUMN_NAME_PHONE = "cellphone";
        public static final String COLUMN_NAME_EMAIL = "useremail";
        public static final String COLUMN_NAME_ADDRESS = "useraddress";


    }
//    [refer to https://www.sqlite.org/datatype3.html]
//    SQL 語法 : create table
//    create table customers (
//    userid varchar(6) not null,
//    user varchar(6) not null,
//    password varchar(25) not null ,
//    username varchar(12) not null,
//    userbirth date not null,
//    cellphone varchar(10) not null,
//    useremail varchar(45) not null,
//    useraddress varchar(50) not null ,primary key(userid));
    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + mySQLiteEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + mySQLiteEntry.TABLE_NAME + " (" +
                    mySQLiteEntry.COLUMN_NAME_ID + " TEXT NOT NULL PRIMARY KEY," +
                    mySQLiteEntry.COLUMN_NAME_USER + " TEXT NOT NULL ," +
                    mySQLiteEntry.COLUMN_NAME_PWD + " TEXT NOT NULL ," +
                    mySQLiteEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL ," +
                    mySQLiteEntry.COLUMN_NAME_BIRTH + " TEXT NOT NULL ," +
                    mySQLiteEntry.COLUMN_NAME_PHONE + " TEXT NOT NULL ," +
                    mySQLiteEntry.COLUMN_NAME_EMAIL + " TEXT NOT NULL ," +
                    mySQLiteEntry.COLUMN_NAME_ADDRESS + " TEXT NOT NULL );" ;


    public static class mySQLiteDbHelper extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the database version.
        // SQL語法: create database Demo;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Demo.db";

        public mySQLiteDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}








