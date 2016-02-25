package com.bhuvanesh.sqlitetraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Bhuvanesh on 1/24/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "USER PROFILE";
    private static final int    DATABASE_VERSION = 4;
    private static final String TABLE_NAME       = "UserRecords";
    private static final String UID              = "_id";
    private static final String NAME             = "Name";
    private static final String EMAIL            = "Email";
    private static final String PASSWORD         = "Password";
    private static final String MOBILE_NO        = "MobileNo";
    private static final String STATE_NAME       = "StateName";
    private static final String DISTRICT_NAME    = "DistrictNmae";
    SQLiteDatabase dbWrite, dbRead;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("logcheck", "on create called");
        String createTable = "CREATE TABLE "+TABLE_NAME+" ( " +
                UID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME+" TEXT, "+ EMAIL+" TEXT, "+PASSWORD+ " TEXT, "+MOBILE_NO+ " TEXT, " +
                STATE_NAME +" TEXT, "+DISTRICT_NAME+ " TEXT)";

        try {
            db.execSQL(createTable);
            Log.d("logcheck", "table created success");
        } catch (SQLException ex) {
           Log.d("logcheck","table not created, exception");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE "+ TABLE_NAME + " IF EXISTS";
        try {
            db.execSQL(dropTable);
            Log.d("logcheck", "table deleted and recreate");
            onCreate(db);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addUserRecord(ArrayList<String> userInfo) {
        dbWrite = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, userInfo.get(0));
        initialValues.put(EMAIL, userInfo.get(1));
        initialValues.put(PASSWORD, userInfo.get(2));
        initialValues.put(MOBILE_NO, userInfo.get(3));
        initialValues.put(STATE_NAME, userInfo.get(4));
        initialValues.put(DISTRICT_NAME, userInfo.get(5));
        long id = dbWrite.insert(TABLE_NAME, null, initialValues);
        if(id < 0)
            Log.d("logcheck", "unsuceessful row");
        else
            Log.d("logcheck", "row inserted sucessfully");

        dbWrite.close();
    }

    /*public Cursor getAllUserDetails() {
        SQLiteDatabase dbReadAll = this.getReadableDatabase();
        String[] columns = {UID, NAME, EMAIL, PASSWORD, MOBILE_NO};
        return dbReadAll.query(TABLE_NAME, columns, null, null, null, null, null);
    }*/

    public Cursor matchWithEmailPassword(String email, String password) {
        //Select Records match with given email & password
         dbRead = this.getReadableDatabase();
        String[] columns = {UID, NAME, EMAIL, PASSWORD, MOBILE_NO, STATE_NAME, DISTRICT_NAME};
        String[] selectionArgs = {email, password};
        return dbRead.query(TABLE_NAME, columns, EMAIL+" = ? AND "+PASSWORD+" = ? ", selectionArgs, null, null, null, null);
    }

    public Cursor matchWithEmail(String email) {
        dbRead = this.getReadableDatabase();
        String[] column = {EMAIL};
        String[] selectArgs = {email};
        return dbRead.query(TABLE_NAME, column, EMAIL+ " = ? ", selectArgs, null, null, null, null);

    }
}
