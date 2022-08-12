# Project loginPractise
## 可使用原始SQL語法的方法
> 以下方法皆為`SQLiteDatabase`類別下的方法
 + `execSQL` : 只能執行不會return data的SQL語法
 > [class SQLiteDatabase - execSQL 方法 原始文件參考](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#execSQL(java.lang.String))
 + `rawQuery` : 能執行所有SQL語法
 > [class SQLiteDatabase - rawQuery 方法 原始文件參考](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#rawQuery(java.lang.String,%20java.lang.String[]))

## Android方法
+ `日期Dialog`:
>[Dialog 日期選單](https://ithelp.ithome.com.tw/articles/10251105)

+ `Dialog製作圓角`:
> [Dialog 製作圓角](https://www.cfanz.cn/resource/detail/yoGogNzrrryKA)

## SQLite的SQL語法
> 參考資料 : https://www.sqlite.org/datatype3.html

```
CREATE TABLE t1(
    x INTEGER PRIMARY KEY,
    a,                 /* collating sequence BINARY */
    b COLLATE BINARY,  /* collating sequence BINARY */
    c COLLATE RTRIM,   /* collating sequence RTRIM  */
    d COLLATE NOCASE   /* collating sequence NOCASE */
);
                   /* x   a     b     c       d */
INSERT INTO t1 VALUES(1,'abc','abc', 'abc  ','abc');
INSERT INTO t1 VALUES(2,'abc','abc', 'abc',  'ABC');
INSERT INTO t1 VALUES(3,'abc','abc', 'abc ', 'Abc');
INSERT INTO t1 VALUES(4,'abc','abc ','ABC',  'abc');
 
/* Text comparison a=b is performed using the BINARY collating sequence. */
SELECT x FROM t1 WHERE a = b ORDER BY x;
--result 1 2 3
```
## Cursor 取出資料的方法
> 當執行 select 搜尋資料後，從 rawQuery return 的 Cursor 取出資料的方法
> 資料來源 : https://stackoverflow.com/questions/7387455/android-sqlite-how-to-retrieve-specific-data-from-particular-column
```
dbHelper = new DBHelper(getApplicationContext());
SQLiteDatabase db = dbHelper.getReadableDatabase();

Cursor cursor = db.rawQuery("select * from centuaryTbl where email='"+email+"'",null);
if (cursor.moveToFirst())
{
    do
    {
        String s1 = cursor.getString(cursor.getColumnIndex("s1"));
        String s2 = cursor.getString(cursor.getColumnIndex("s2"));
        String s3 = cursor.getString(cursor.getColumnIndex("s3"));


    }while (cursor.moveToNext());
}
```
