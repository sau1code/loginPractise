# Project loginPractise 說明

>說明大綱 :
>1. [背景](https://github.com/sau1code/loginPractise#%E8%83%8C%E6%99%AF)
>2. [目的](https://github.com/sau1code/loginPractise#%E7%9B%AE%E7%9A%84)
>3. [材料](https://github.com/sau1code/loginPractise#%E6%9D%90%E6%96%99)
>4. [方法](https://github.com/sau1code/loginPractise#%E6%96%B9%E6%B3%95)
>5. [結果](https://github.com/sau1code/loginPractise#%E7%B5%90%E6%9E%9C)
>6. [討論](https://github.com/sau1code/loginPractise#%E8%A8%8E%E8%AB%96)
>7. [補充資料](https://github.com/sau1code/loginPractise#%E8%A3%9C%E5%85%85%E8%B3%87%E6%96%99)

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
      - 建立`Linear Layout`並幫Layout製作圓角<br>
      - 帳號<br>
        建立`editText-Name`
      - 密碼<br>
        建立`editText-Password`
      - 重新輸入密碼<br>
        建立`editText-Password`
      - 姓名<br>
        建立`editText-Name`
      - 生日<br>
        建立一個`TextView`並在`registerActivity`使用日期Dialog回傳字串(格式為xxxx/xx/xx)
      - 手機號碼<br>
        建立`editText-Phone`
      - 通訊地址<br>
        建立`spinner`讓使用者選擇居住縣市、區域<br>
        建立`editText-Multiple`讓使用者輸入地址<br>
      - Email<br>
        建立`editText-Email`
      - OK Button <br>
        建立`OK Button`，按下之後判斷:<br>
        1. 是否所有資料均輸入完畢<br>
        2. 密碼是否為6-12位<br>
        3. 密碼和重新輸入密碼是相同的<br> 
        4. 手機號碼的格式是否為09開頭並且為10碼數字<br>
        5. Email格式是否為至少一位英文數字或符號 + '@' + 至少一位英文 + '.' + 至少一位英文<br>
        6. 使用者帳號、手機、Email是否已經註冊過<br>
  - register_ok_dialog.xml :
    - `TextView`
      用來顯示使用者輸入的所有資料
    - `OK Button`
      按下OK Button送出資料至`SQLite`，註冊成功<br>
    - `Cancel Button`
      讓`register_ok_dialog`消失<br>
  - registerActivity.java :
    -監聽所有元件
 
+ 會員登入 :
  - layout_main.xml :
    | 元件 | 功能 |
    | --- | --- |
    | edittext-account| |
    | edittext-password | |
    | button-login | 根據不同帳號通往(member / admin) |
    | button-register | |
  - MainActivity.java :
    - `getPasswordFromSQLite`:
      用輸入帳號當引數去SQL取出對應的密碼並return
    
      

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

### 幾種不同的監聽方法
+ `addTextChangedListener`: <br>
> 可監聽Edittext的輸入前、中、後不同時期的即時變化<br>
> 參考資料 : https://www.runoob.com/w3cnote/android-tutorial-listener-edittext-change.html
```
// 帳號輸入 監聽 (當改變時)
editTextAccount.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        inputAccount = editTextAccount.getText().toString();
        if (inputAccount.length() * inputPassword.length() == 0) {
            buttonLogin.setBackgroundColor(0xFFEEEEEE);
            buttonLogin.setTextColor(0xFFAAAAAA);
        } else {
            buttonLogin.setBackgroundColor(0xFFF44336);
            buttonLogin.setTextColor(0xFFFFFFFF);
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {}
});
```
+ `setOnTouchListener`:<br>
> 可監聽元件上按下、放開、滑動變化<br>
> 參考資料 : https://www.runoob.com/w3cnote/android-tutorial-listener-edittext-change.html
```
// 顯示密碼圖 監聽
imageViewEyes.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageViewEyes.setSelected(true);
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case MotionEvent.ACTION_UP:
                imageViewEyes.setSelected(false);
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
        }
        return true;
    }
});
```
