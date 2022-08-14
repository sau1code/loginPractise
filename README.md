# Project loginPractise 說明

>說明大綱 :
>1. [背景](https://github.com/sau1code/loginPractise/edit/master/README.md#%E8%83%8C%E6%99%AF)
>2. [目的](https://github.com/sau1code/loginPractise/edit/master/README.md#%E7%9B%AE%E7%9A%84)
>3. [材料](https://github.com/sau1code/loginPractise/edit/master/README.md#%E6%9D%90%E6%96%99)
>4. [方法](https://github.com/sau1code/loginPractise/edit/master/README.md#%E6%96%B9%E6%B3%95)
>5. [結果](https://github.com/sau1code/loginPractise/edit/master/README.md#%E7%B5%90%E6%9E%9C)
>6. [討論](https://github.com/sau1code/loginPractise/edit/master/README.md#%E8%A8%8E%E8%AB%96)
>7. [補充資料](https://github.com/sau1code/loginPractise/edit/master/README.md#%E8%A3%9C%E5%85%85%E8%B3%87%E6%96%99)

## 背景

由於專題想用 Android 做購物商城App，一定會用到會員註冊，管理者登入和資料庫連線儲存資料的功能，因為App資料怎麼存到SQLite還是個謎團，很怕期末最後開天窗，所以提前先測試會員資料如何儲存至資料庫，以及如何根據App需求對資料庫進行更新刪除插入資料等功能。

專題為多人協作後的成果，而如何協作和同步程式碼會是個重要問題，故需要寫一個小專案測試合作寫程式會出現什麼問題。

## 目的 
1. 建立會員註冊，登入和修改會員資料的功能。
2. 會員資料儲存於本機SQLite資料庫中。
3. 提供管理者帳號登入，以管理和搜尋目前會員的清單。
4. 以git和github平台實現多人協作專案。

## 材料

+ 平台 : Android 
+ 程式語言 : Java
+ 資料庫 : SQLite

## 方法 
> 以不同主要功能分項說明 : 建立何種元件與如何處理使用者與元件互動的結果

+ 會員註冊 :
  - layout_register.xml :
  - register_ok_dialog.xml :
  - registerActivity.java :
 
+ 會員登入 :
  - layout_main.xml :
  - MainActivity.java :

+ 會員資料檢視修改 : 
  - layout_member.xml : 
  - memberActivity.java : 

+ 管理者登入 : 
  - layout_admin.xml : 
  - adminActivity.java :
  - admin_card.xml :
  - RecyclerViewAdapter.java :
 
+ 初始化資料庫類別頁面 :
  - mySQLiteContract.java : 

## 結果



## 討論

## 補充資料
### 可使用原始SQL語法的方法
> 以下方法皆為`SQLiteDatabase`類別下的方法
 + `execSQL` : 只能執行不會return data的SQL語法
 > [class SQLiteDatabase - execSQL 方法 原始文件參考](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#execSQL(java.lang.String))
 + `rawQuery` : 能執行所有SQL語法
 > [class SQLiteDatabase - rawQuery 方法 原始文件參考](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase#rawQuery(java.lang.String,%20java.lang.String[]))

### Android方法
+ `日期Dialog`:
>[Dialog 日期選單](https://ithelp.ithome.com.tw/articles/10251105)

+ `Dialog製作圓角`:
> [Dialog 製作圓角](https://www.cfanz.cn/resource/detail/yoGogNzrrryKA)

### SQLite的SQL語法
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
### Cursor 取出資料的方法
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
