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

由於專題想用 Android 做購物商城App，一定會用到會員註冊，管理者登入和資料庫連線儲存資料的功能，因為App資料怎麼存到SQLite還是個謎團，很怕期末最後開天窗，所以提前先測試會員資料如何儲存至資料庫，以及如何根據App需求，針對資料庫進行更新刪除插入資料等功能。

專題為多人協作後的成果，而如何協作和同步程式碼會是個重要問題，故需要寫一個小專案，先測試合作寫程式會出現什麼狀況。

## 目的 
1. 建立會員註冊，登入和修改會員資料的功能。
2. 會員資料儲存於本機SQLite資料庫中。
3. 提供管理者帳號登入，以管理和搜尋目前會員的清單。
4. 以git和github平台實現多人協作專案。

## 材料

+ 平台 : Android 
  - Minimum SDK : API 26 Android 8.0
+ 程式語言 : Java
+ 資料庫 : SQLite 3.18.2

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
    - edittext-accoun: 輸入帳號
    - edittext-password: 輸入密碼
    - button-login: 根據不同帳號通往 member/admin 頁面
    - button-register: 通往register頁面
  - MainActivity.java :
    - `getPasswordFromSQLite`: 用輸入帳號當引數去SQL取出對應的密碼
    - `madeShowFromSQL`: Show出所有會員帳/密給測試人員參考
    
+ 會員資料檢視修改 : 
  - layout_member.xml : 
    - `textView-member-tempshow` : 顯示會員帳號名稱
    - `LinearLayout` : 會員資料顯示/輸入框進行排版。
      + `textView` : 顯示資料名稱。
      + `editText` : 提供會員資料顯示/輸入框。
    - `LinearLayout` : 刪除帳號，修改會員資料和送出修改資料的`button`進行排版。
  - memberActivity.java :
    * 設定 memberActivity 的 `Action Bar` :
      + `onCreate`
       > * `setTitle`: 設定Activity標頭為會員資料檢視頁面。
       > * `actBar.setDisplayHomeAsUpEnabled` : 顯示返回前一頁的`menu`元件。
       > * `actBar.setBackgroundDrawable` : 設定Action Bar的背景圖片。
      
      + `onOptionsItemSelected`
       >監聽`menu`元件，當使用者按下返回前一頁的`menu`元件的時候，回到前一個 Activity。
         
     * 接收從 `MainActivity` 傳來的`Intent`:
       > 從`Intent`取出會員帳號字串，利用`setText`讓`textView`元件顯示登入的會員帳號名稱。
         
     * 設定/接收`editText`元件文字 & 連接`SQLite`資料庫，以取得/修改會員資料:
       >  * `editText.setEnabled(false)` : 設定editText為使用者不可編輯，只顯示文字的狀態。
       >  * 資料庫連線:<br>
       >  1. `mySQLiteContract.mySQLiteDbHelper`類別<br>
       >  -連接SQLite資料庫，無建立資料庫就會自動建立資料庫和資料表，如已存在資料庫，就會直接使用已存在的資料庫:<br>
       >    `mySQLiteContract.mySQLiteDbHelper dbHelper`<br>
       >    `= new mySQLiteContract.mySQLiteDbHelper(memberActivity.this);`<br>
       >    
       >  2. `mySQLiteContract.mySQLiteEntry`類別 <br>
       >  -定義資料表名稱和欄位名稱避免打錯欄位或資料表名稱而產生的SQL語法錯誤:
       >  
       >      例如 : 
       >      `mySQLiteContract.mySQLiteEntry.TABLE_NAME` 為資料表名稱
       >      `mySQLiteContract.mySQLiteEntry.COLUMN_NAME_EMAIL` 為會員email資料欄位名稱      
       >  
       >  3. 資料庫設定為可寫入資料的狀態  
       >    `SQLiteDatabase db = dbHelper.getWritableDatabase();`
       >  4. `SQLiteDatabase`類別即提供可直接執行SQL語法的方法:
       >     * `execSQL(String SQL_command)` : 執行無傳回值的`SQL`語法(`CREATE`, `INSERT`, `UPDATE`, `DELETE`語法)。
       >     * `rawQuery(String SQL_command,null)` : 執行有傳回值的`SQL`語法(`SELECT`語法)，傳回`Cursor`物件。
       >     
       >  5. `Cursor`類別提供取出搜尋結果資料的方法 :
       >     * `getCount()` : return int，傳回有幾列搜尋結果。
       >     * `getColumnCount()` : return int，傳回搜尋結果有幾個欄位。
       >     
       >     (1) 控制 cursor 指向列數 <br>
       >     * `moveToFirst()` : return boolean，true 代表將cursor移到第一個搜尋結果，false代表沒有搜尋結果。
       >     * `moveToNext()` :  return boolean，true 代表將cursor移到下一個搜尋結果，false代表沒有下一個搜尋結果。
       >     
       >     (2) 取出此列的指定欄位資料 <br>
       >     * `getColumnIndex(String columnName)` : return int，傳回搜尋結果欄位對應到哪個index。
       >     * `getColumnName(int columnIndex)` : return String，傳回index對應到哪個搜尋結果欄位。
       >     * `getString(int columnIndex)` : return String，傳回指定index欄位的字串資料。
       >     
       >  6. `close()`結束資料庫連線:<br>
       >  注意!! `mySQLiteContract.mySQLiteDbHelper`, `SQLiteDatabase`和 `Cursor`物件都需要`close()`
       >  以避免佔用太多資源。
       >  
       >  `onCreate`Activity進行會員資料顯示:
       > 
       >  * `rawQuery(String SELECT_SQL_command,null)` 資料庫搜尋取得會員資料 <br>
       >     (SELECT SQL語法設定where條件為user='使用者帳號')
       >  
       >     &rarr; `EdiText.setText` 設定`editText`元件文字
       >     
       >  `setOnClickListener`監聽刪除帳號Button :
       >  當使用者按下刪除帳號Button時
       >     
       >  * `execSQL(String DELETE_SQL_command)` : 刪除會員帳號。<br>
       >  (DELETE SQL語法設定where條件為user='使用者帳號')
       >  <br>
       >  
       >   `setOnClickListener`監聽修改會員資料Button :
       >  當使用者按下修改會員資料Button時
       >  
       >  * `EditText.getText()`取得使用者輸入到`editText`元件的文字 
       >
       >     &rarr;  `isEmpty()` : 確認使用者是否完整輸入所有會員資料
       >
       >     &rarr; `matches(passwordformat)` , `matches(phoneformat)` , `matches(emailformat)`:
       >
       >     確認使用者是否輸入6-12位數的密碼 , 完整的手機電話(ex: 0912345678, 0912-345-678) , 完整的email(abc@gmail.com)
       >     ```
       >        //To check the format of the password , phone number and email,
       >         //set regular expression to match desirable input
       >         String passwordformat = "^.{6,12}$";
       >         String phoneformat = "^09\\d{2}-?\\d{3}-?\\d{3}$";
       >         String emailformat = "^.+@\\w+\\..*$";
       >     ```
       >
       >     &rarr; `rawQuery(String SELECT_SQL_command,null)` return 資料庫搜尋結果(Cursor 物件)
       >
       >     (分別SELECT SQL語法設定where條件為 useremail='會員輸入的email' , cellphone='會員輸入的手機電話')
       >
       >     &rarr; `Cursor.moveToFirst()` Cursor 物件指到搜尋結果的第一列資料
       >
       >     &rarr; `Cursor.getString(1)` 取出會員帳號名稱放到`userCheck`字串變數
       >
       >     &rarr; `cursor.getCount()>0 && userCheck.equals(user)==false` : 有一筆以上的搜尋結果且會員帳號名稱不是本人，
       >     則無法修改手機電話或email，否則確認行動電話和email與其他會員不重複，而可修改會員本人資料。     
       >
       >     &rarr; `execSQL(String UPDATE_SQL_command)` 修改會員資料<br>
       >     (UPDATE SQL語法設定where條件為user='使用者帳號')

+ 管理者登入 : 
  - layout_admin.xml : 
    - textview-sum: 顯示有幾筆搜尋結果
    - edittext-search: 關鍵字搜尋
    - spinner-city: 選擇城市篩選
    - checkbox-city: 是否啟用城市篩選
    - imageview-birthday: 生日範圍篩選
    - recyclerview-show: 列出所有搜尋結果
  - adminActivity.java :
    - `madeShowFromSQL`: 依篩選條件取SQLite中值製作List放進Adapter傳進recyclerView

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
