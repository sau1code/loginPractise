# Project loginPractise 說明

>說明大綱 :
>1. [背景](https://github.com/sau1code/loginPractise#%E8%83%8C%E6%99%AF)
>2. [目的](https://github.com/sau1code/loginPractise#%E7%9B%AE%E7%9A%84)
>3. [平台與工具](https://github.com/sau1code/loginPractise#%E5%B9%B3%E5%8F%B0%E8%88%87%E5%B7%A5%E5%85%B7)
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

## 平台與工具

+ 平台 : Android 
  - Minimum SDK : API 26 Android 8.0
+ 程式語言 : Java
+ 資料庫 : SQLite 3.18.2

## 方法 
> 以不同主要功能分項說明 : 建立何種元件與如何處理使用者與元件互動的結果

+ 會員註冊 :
  - layout_register.xml :
      - 建立`Linear Layout`並幫Layout製作圓角<br>
      - 帳號 : 建立`editText_register_account`讓使用者輸入帳號
      - 密碼 : 建立`editText_register_Password`讓使用者輸入密碼
      - 重新輸入密碼 : 建立`editText_register_checkPassword`讓使用者重新輸入密碼
      - 姓名 : 建立`editText_register_Name`讓使用者輸入姓名
      - 生日 : 建立一個`TextView`並在`registerActivity`使用日期Dialog回傳字串(格式為xxxx/xx/xx)
      - 手機號碼 : 建立`editText_register_Phone`讓使用者輸入行動電話
      - 通訊地址 : <br/>
        建立`spinner`讓使用者選擇居住縣市、區域<br>
        建立`editText_register_address`讓使用者輸入地址<br>
      - Email : 建立`editText_register_Email`讓使用者輸入Email
      - OK Button : 建立`OK Button`
  - register_ok_dialog.xml :
    - `TextView`
      用來顯示使用者輸入的所有資料
    - `OK Button`
      按下OK Button送出資料至`SQLite`，註冊成功<br>
    - `Cancel Button`
      讓`register_ok_dialog`消失<br>
  - registerActivity.java :
    * 日曆
      - `new DatePickerDialog.OnDateSetListener`:建立一個日曆Dialog
      - `Calendar calendar = Calendar.getInstance();` : Calendar.getInstance()方法獲得一個calendar型態
      - 可以用`Calendar.set(int calndr_field, int new_val)`方法修改其年、月、日、時、分、秒等值，set有兩個參數:<br/>
         1.calndr_field：這是“日曆”類型，是指要更改的日曆字段。<br/>
         2.new_val：這是指要替換的新值。<br/>
         [參考資料](https://vimsky.com/zh-tw/examples/usage/calendar-set-method-in-java-with-examples.html)
      - `SimpleDateFormat類別` : 定義日期格式、時區   
         [參考資料](https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/485945/)
         
      - 監聽`buttonBirthday` : 使用calendar.get()方法取得年/月/日
    * 地址
       - `SpinnerCity.setOnItemSelectedListener` : 監聽`SpinnerCity`並依照不同的縣市改變`SpinnerArea`選單中的區域   
       - `SpinnerArea.setOnItemSelectedListener` : 監聽`SpinnerArea`
       - `editText_Address.getText().toString() : `取得`editText_Address`中的字串  
    * `OK Button`
      - `button_registerOK.setOnClickListener` : 監聽OK Button
        - 按下之後判斷:<br>
          1. 是否所有資料均輸入完畢<br>
          2. 密碼是否為6-12位<br>
          3. 密碼和重新輸入密碼是相同的<br> 
          4. 手機號碼的格式是否為09開頭並且為10碼數字<br>
          5. Email格式是否為至少一位英文數字或符號 + '@' + 至少一位英文 + '.' + 至少一位英文<br>
          6. 使用者帳號、手機、Email是否已經註冊過<br>
        - 判斷無誤後跳出Dialog
     * `registerDialog`
        - `registerDialog.setCancelable()`方法可以設置是否接受返回鍵使用
        - `顯示使用者基本資料`，只顯示前三位密碼
        - 監聽`OK Button` : 按下之後註冊成功
        - 監聽`Cancel Button` : 按下之後registerDialog關閉 
     * 將註冊資料傳進SQLite
        
        
 
+ 會員登入 :
  - layout_main.xml :
    - `textview`: 顯示"登入"頁的大標題。
    - `edittext`: 用來讓使用者輸入帳號。
    - `edittext`: 用來讓使用者輸入密碼。
    - `imageview`: 顯示密碼的眼睛圖，按住可顯示密碼框中真實文字。
    - `textview`: 忘記密碼文字，按下顯示提示吐司 (無實質功能)。
    - `button`: 登入按鈕，按下會根據輸入框中不同帳號類型通往所屬頁面 (一般會員/管理者)。
    - `button`: 註冊按鈕，按下通往註冊頁面。
    - `textview`: 顯示目前SQLite內存所有帳號密碼 (練習用途)。
  - MainActivity.java :
    - 登入功能:<br>
    
      > 1. 帳號與密碼的輸入框皆為**即時**監聽`addTextChangedListener()`，<br>
      >    時刻的改變類別中的`inputAccount`、`inputPassword`字串。<br>
      >    若兩格都有值時，登入按鈕會亮起<br>
      > 
      > 2. 按下登入按鈕會經過一系列的if-else判斷，由上到下為:
      >     - __帳號或密碼是否為空__: 成立吐司顯示"請輸入完整帳號密碼"。<br>
      >     - __是否為管理者且密碼正確__: 成立用`Intnet`夾帶`inputAccount`訊息傳送至`adminActivity`頁面。<br>
      >     - __是否為使用者且密碼正確__: 成立用`Intnet`夾帶`inputAccount`訊息傳送至`memberActivity`頁面。<br>
      >       這裡判斷式內容為: `inputPassword.equals(getPasswordFromSQLite(inputAccount)`，<br>
              意思是用使用者輸入的`inputAccount`去自製方法`getPasswordFromSQLite()`中<br>
              找出該使用者匹配的密碼，再與`inputPassword`對比是否相同
      >     - __其他__: 成立吐司顯示"帳號或密碼錯誤"。<br>
      >
      >
    - 顯示密碼:
      > 在`imageview`使用`setOnTouchListener`監聽，<br>
      > 介面內的方法可以分辨使用者"按下"和"放開"不同動作，利用它們來模擬按住不放的情境<br>
      >
    - 往註冊頁面:
      > 簡單的使用`Intent`不帶值傳送。
    - 點兩下清空SQLite所有帳號:
      > 用最常見的`setOnClickListener`監聽，創一個`cleanSqlTime`變數記錄當下時間，<br>
      > 如果(本次紀錄時間 - 上次紀錄時間) > 0.4秒，則將本次時間存進`cleanSqlTime`給下一次比較使用，<br>
      > 如果 < 0.4秒，則判定為連點成立，重置清空SQLite中table紀錄。<br>
      > 清空使用的是`SQLiteHelper.onUpgrade(SQLiteDb, version, version)`方法。<br>
      > 這方法在`SQLiteHelper`類別中，<br>
      > 它會先執行`db.execSQL(SQL_DELETE_ENTRIES)`刪除當前table，<br>
      > 再執行`onCreate(db)`創一個新的table。<br>
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
    - `textview`: 顯示符合的搜尋結果有幾筆。
    - `edittext`: **關鍵字搜尋**的輸入框。
    - `checkbox`: 勾選表示啟用spinner縣市條件搜尋。
    - `spinner`: 可選擇某一縣市做**條件搜尋**。
    - `imageview`: 按下會跳出日期選擇視窗，選擇日期做**範圍搜尋**。
    - `textview`: 顯示所選日期
    - `recyclerview`: 將所有符合條件的結果的用戶資訊製成卡片陳列在這。
  - adminActivity.java :
    - 搜尋:<br>
      > 讓管理者自訂三種搜尋條件，分別為`edittext`關鍵字、`spinner`縣市、`imageview`日期，<br>
      > 相對應的以`searchInput`、`cityInput`、`birthdayInput`字串來存值。<br>
      > 不論改變何種條件都能即時監聽並在每個監聽中呼叫`madeShowFromSQL()`即時改變顯示結果。<br>
      > 實踐方法如下:<br>
      > 1. `edittext`關鍵字:<br>
      >    使用`addTextChangedListener`中的`onTextChanged`方法，<br>
      >    可即時監測輸入框中的變化來改變`searchInput`字串變數。
      > 2. `spinner`縣市:<br>
      >    因`spinner`有預設值，希望可以在不創"請選擇"當`spinner`預設選項的情況下<br>
      >    (因為此舉會改變同伴的陣列參數，且認為直接顯示如"台北市"字樣可對使用者明示`spinner`存在用途)，<br>
      >    於是創造了一個`checkBoxCity`，作為`spinner`的致能旗標，當勾選時`checkBoxFlag`布林值為True<br>
      >    當`spinner`改變時縣市存進`cityInput`字串變數，<br>
      >    上面兩者改變時都會呼叫`madeShowFromSQL()`，<br>
      >    由`madeShowFromSQL()`內判斷，當`checkBoxFlag`為True時`cityInput`才會被納入搜尋條件。<br>
      > 3. `imageview`日期:<br>
      >    參考同組人員作法之外，將監聽`setOnClickListener`設在`imageViewBirthday`日曆icon上，<br>
      >    當有選擇日期時，`imageViewBirthday`會變為"X圖"，並在`textview`顯示所選日期，將值存進`birthdayInput`變數中，<br>
      >    當再次按下`imageViewBirthday`，"X圖"會變回日曆icon，且清空`textview`文字。<br>
      >    每次進監聽時改變`flag`的兩種狀態，達成輪流執行if或else區塊的兩種效果。

    - 顯示結果:<br>
      > 1. `madeShowFromSQL()`:<br>
      >    因每`edittext`關鍵字、`spinner`縣市、`imageview`日期，有或沒有值，<br>
      >    皆會引響SQLite搜尋語法，若將可能組合全列出來可能有2的3次方種語法組合，<br>
      >    所以選擇以`if`判斷式加上`StringBuilder`來動態組合語法，<br>
      >    粗略用白話來形容就是，<br>
      >    先創一個 **"SELECT * FROM table"** 基本語句，<br>
      >    如果有任意搜尋條件，就在基本語句後加入 **WHERE**，<br>
      >    如果有城市條件就再加入 **地址欄位 LIKE 城市**，<br>
      >    如果有日期條件就再加入 **生日欄位 < 日期**，<br>
      >    如果有關鍵字條件就再加入 **所有欄位 = %關鍵字%**，<br>
      >    以上三者，會先判斷自己是不是首個加入WHERE後的條件，如果不是首個，會先加入 **AND**，<br>
      >    將生成的`StringBuilder`丟進`rawQuery`方法中，以取得`Cursor`，<br>
      >    用迴圈把`Cursor`的值全部取出做成`Map`放進`List`中，<br>
      >    這個List是製作`recyclerView`要用的素材之一。<br>
      > 2. `recyclerView`:<br>
      >    這邊一樣粗略用白話形容(為個人理解可能有誤，望包容指教)，<br>
      >    `recyclerView`是類似`listView`般的存在，<br>
      >    完成`recyclerView`需要先創`admin_card.xml`和`RecyclerViewAdapter.java`兩個檔案，<br>
      >    兩個檔案分別是定義了卡片的**樣貌**和**內容**，<br>
      >    當然內容也包含了我們上個階段最後所生成的`List`，<br>
      >    將兩個檔案和之前做的`List`，三者結合後，製成`Adapter`<br>
      >    把`Adapter`放入`recyclerView`元件，以顯示最終效果。

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
