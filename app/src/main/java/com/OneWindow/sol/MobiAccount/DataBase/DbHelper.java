package com.OneWindow.sol.MobiAccount.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by waqarbscs on 9/9/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private  static DbHelper dbHelper;


    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="finace.db";


    private static String TABLE_1="user";

    private static String USER_ID="userId";
    private static final String USER_NAME="username";
    private static final String PASSWORD="password";
    private static final String BUSINESS="business";
    private static final String CATEGOTY="category";
    private static final String EMAIL="email";
    private static final String  PHONE="phone";

    private static final String TABLE_2="item_type";

    private static final String COLUMN_SUBITEM_ID="itemSubId";
    private static final String COLUMN_ITEM_TYPE="type";
    private static final String COLUMN_SUBTYPE="subtype";

    private static final String TABLE_3="items";

    private static final String ITEM_ID="itemId";
    private static final String TYPE="type";
    private static final String SUBTYPE="subtype";
    private static final String AMOUNT="amount";
    private static final String TIMESTAMP="time_stamp";
    private static final String DESCRIPTION="description";
    private static final String USERID="userId";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    static public DbHelper getDbHelper(Context context) {
        if (null == dbHelper) {
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    static public void closeDbHelper() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE "+TABLE_1+"(" +
                USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                PASSWORD + " TEXT, " +
                BUSINESS + " TEXT, " +
                CATEGOTY + " TEXT, " +
                PHONE + " TEXT, " +
                EMAIL + " TEXT)";
        db.execSQL(CREATE_BOOK_TABLE);

        String CREATE_ITEM_SUBTYPE= "CREATE TABLE "+TABLE_2+" ( "
                + COLUMN_SUBITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEM_TYPE + " INTEGER NOT NULL, "
                + COLUMN_SUBTYPE + " TEXT NOT NULL UNIQUE )";
        db.execSQL(CREATE_ITEM_SUBTYPE);
        String CREATE_ITEMS= "CREATE TABLE "+TABLE_3+" ( "
                + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE + " INTEGER, "
                + SUBTYPE + " TEXT, "
                + AMOUNT + " BIGINTEGER, "
                + TIMESTAMP + " TEXT, "
                + USERID + " TEXT, "
                + DESCRIPTION + " TEXT )";

        db.execSQL(CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!=newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
            this.onCreate(db);
        }
    }
    public boolean saveUSER(String user,String pass,String business,String cat,String email,String phone){
        ContentValues values=new ContentValues();
        values.put(USER_NAME,user);
        values.put(PASSWORD,pass);
        values.put(BUSINESS,business);
        values.put(CATEGOTY,cat);
        values.put(PHONE,phone);
        values.put(EMAIL,email);
        long r=dbHelper.getWritableDatabase().insert(TABLE_1,null,values);
        if(r!=-1){
            return true;
        }
        return false;
    }
    public void insertItemSubType(int  item_type,String item_subtype){
        ContentValues values=new ContentValues();
        values.put(COLUMN_ITEM_TYPE,item_type);
        values.put(COLUMN_SUBTYPE,item_subtype);
        dbHelper.getWritableDatabase().insert(TABLE_2,null,values);
    }
    public boolean insertItem(int type, String subtype, long amount, String timestamp, String description, String userId){
        boolean i=false;
        ContentValues values=new ContentValues();
        values.put(TYPE,type);
        values.put(SUBTYPE,subtype);
        values.put(AMOUNT,amount);
        values.put(TIMESTAMP,timestamp);
        values.put(DESCRIPTION,description);
        values.put(USERID,userId);
        long l=dbHelper.getWritableDatabase().insert(TABLE_3,null,values);
        if(l!=-1){
            i=true;
        }
        return i;
    }
    public boolean login_data(String user,String pass){
        String[] SelectionArgs=new String[]{user,pass};
        boolean login=false;
        String selectString = "SELECT * FROM " + TABLE_1 + " WHERE " + USER_NAME + " =? AND " + PASSWORD + " =?";
        //String selectString = "SELECT * FROM " + TABLE_1;
        Cursor c=dbHelper.getReadableDatabase().rawQuery(selectString,SelectionArgs);
        c.moveToFirst();
        while (!c.isAfterLast()){
            login=true;
            c.moveToNext();
        }
        return login;
    }
    public Cursor getSubType(int  t){
        String[] columns = new String[] { "*", COLUMN_ITEM_TYPE + " AS _id" };
        String selection=COLUMN_ITEM_TYPE+"=?";
        String[]  selectionArgs = new String[] {Integer.toString(t)};
        return dbHelper.getReadableDatabase().query(TABLE_2,columns,selection,selectionArgs,null,null,null);
        //return  dbHelper.getReadableDatabase().rawQuery("select * from "+TABLE_SMS,null);
    }
    public Cursor getItems(String  userId){
        String[] columns = new String[] { "*", TYPE + " AS _id" };
        String selection=USERID+"=?";
        String[]  selectionArgs = new String[] {userId};
        return dbHelper.getReadableDatabase().query(TABLE_3,columns,selection,selectionArgs,null,null,null);
        //return  dbHelper.getReadableDatabase().rawQuery("select * from "+TABLE_SMS,null);
    }
    public Cursor getIncome(String  userId,int type){
        String[] columns = new String[] { "*", TYPE + " AS _id" };
        String selection=USERID+"=? AND " + TYPE+"=?";
        String[]  selectionArgs = new String[] {userId, String.valueOf(type)};
        return dbHelper.getReadableDatabase().query(TABLE_3,columns,selection,selectionArgs,null,null,null);
        //return  dbHelper.getReadableDatabase().rawQuery("select * from "+TABLE_SMS,null);
    }
    public Cursor getIncome(String  userId,int type,String from,String to){
        String[] columns = new String[] { "*", TYPE + " AS _id" };
        String selection=USERID+"=? AND " + TYPE+"=? AND Date(time_stamp) >=Date('"+from+"')" +
                " AND Date(time_stamp) <= Date('"+to+"')";
        String[]  selectionArgs = new String[] {userId, String.valueOf(type)};
        return dbHelper.getReadableDatabase().query(TABLE_3,columns,selection,selectionArgs,null,null,null);
        //return  dbHelper.getReadableDatabase().rawQuery("select * from "+TABLE_SMS,null);
    }
    public Cursor getIncome(String  userId,int type,String from,String to,String cat){
        String[] columns = new String[] { "*", TYPE + " AS _id" };
        String selection=USERID+"=? AND "+SUBTYPE+"=? AND " + TYPE+"=? AND Date(time_stamp) >=Date('"+from+"')" +
                " AND Date(time_stamp) <= Date('"+to+"')";
        String[]  selectionArgs = new String[] {userId,cat, String.valueOf(type)};
        return dbHelper.getReadableDatabase().query(TABLE_3,columns,selection,selectionArgs,null,null,null);
        //return  dbHelper.getReadableDatabase().rawQuery("select * from "+TABLE_SMS,null);
    }

    public Cursor getItem(String  userId){
        String[] columns = new String[] { "*", TYPE + " AS _id" };
        String selection=USERID+"=?";
        String[]  selectionArgs = new String[] {userId};
        return dbHelper.getReadableDatabase().query(TABLE_3,columns,selection,selectionArgs,null,null,null);
        //return  dbHelper.getReadableDatabase().rawQuery("select * from "+TABLE_SMS,null);
    }
    public void update_subItem(int item_type,String item_subtype,String newValue){
        ContentValues values=new ContentValues();
        values.put(COLUMN_ITEM_TYPE,item_type);
        values.put(COLUMN_SUBTYPE,newValue);
        String whereClause = COLUMN_SUBTYPE + "=?";
        String[] whereArgs = new String[]{item_subtype};
        update_item(item_subtype,newValue);
        dbHelper.getWritableDatabase().update(TABLE_2, values, whereClause, whereArgs);
    }
    public void update_item(String item_subtype,String newValue){
        /*long row=    dbHelper.getWritableDatabase().rawQuery("UPDATE "+ TABLE_3 +
                            " SET " + SUBTYPE + " = '" + newValue +
                            "' WHERE " + SUBTYPE + item_subtype  ,
                    null);
        */
        ContentValues values=new ContentValues();
        values.put(SUBTYPE,newValue);
        long l=dbHelper.getWritableDatabase().update(TABLE_3, values, "subtype='"+item_subtype+"'", null);
        //long p=dbHelper.getWritableDatabase().update(TABLE_3, values, "subtype="+item_subtype, null);
    }
    public void update_item(int id, int type, String subtype, long amount, String timestamp, String description, String userId){
        ContentValues values=new ContentValues();
        values.put(ITEM_ID,id);
        values.put(TYPE,type);
        values.put(SUBTYPE,subtype);
        values.put(AMOUNT,amount);
        values.put(TIMESTAMP,timestamp);
        values.put(DESCRIPTION,description);
        values.put(USERID,userId);
        dbHelper.getWritableDatabase().update(TABLE_3, values, "itemId="+id, null);

    }
    public void delete_subType(String subType) {
        String selection = COLUMN_SUBTYPE + "=?";
        String[] selectionArgs = new String[] {subType};
        delete_Item(subType);
        dbHelper.getReadableDatabase().delete(TABLE_2, selection, selectionArgs);
    }
    public void delete_Item(int id) {
        String selection = ITEM_ID + "=?";
        String[] selectionArgs = new String[] {String.valueOf(id)};
        dbHelper.getReadableDatabase().delete(TABLE_3, selection, selectionArgs);
    }
    public void delete_Item(String sub_type) {
        String selection = SUBTYPE + "=?";
        String[] selectionArgs = new String[] {sub_type};
        dbHelper.getReadableDatabase().delete(TABLE_3, selection, selectionArgs);
    }


}
