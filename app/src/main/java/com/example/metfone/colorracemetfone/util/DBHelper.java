package com.example.metfone.colorracemetfone.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.metfone.colorracemetfone.ui.SignData.model.CheckSignDataItem;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SignPhoneNumber.db";
    public static final String PHONE_TABLE_NAME = "phone_number";
    public static final String TABLE_NAME = "contacts";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key,phone_number text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact (String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", phone);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public boolean checkSignData() {
        List<CheckSignDataItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor =  db.rawQuery( "select * from contacts where id="+id+"", null );
        Cursor cursor =  db.rawQuery( "select * from contacts order by phone_number desc limit 1", null );

        if (cursor.moveToFirst()) {
            do {
                CheckSignDataItem checkSignDataItem = new CheckSignDataItem();
                checkSignDataItem.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PHONE_TABLE_NAME)));
                list.add(checkSignDataItem);
            } while (cursor.moveToNext());
        }

        if (list.size() > 0){
            return true;
        }

        return false;
    }

    public boolean checkIsData(String phoneNumber) {
        List<CheckSignDataItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor =  db.rawQuery( "select * from contacts where id="+id+"", null );
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + PHONE_TABLE_NAME + " = '" + phoneNumber +"'", null );

        if (cursor.moveToFirst()) {
            do {
                CheckSignDataItem checkSignDataItem = new CheckSignDataItem();
                checkSignDataItem.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PHONE_TABLE_NAME)));
                list.add(checkSignDataItem);
            } while (cursor.moveToNext());
        }

        if (list.size() > 0){
            return true;
        }

        return false;
    }

    public int checkSize(){
        List<CheckSignDataItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        "select count(*) countS from LstIsdn order by updateCreate desc"
        Cursor cursor =  db.rawQuery( "select count(*) " + PHONE_TABLE_NAME + " from " + TABLE_NAME, null );
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        return count;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PHONE_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + ""); //delete all rows in a table
        db.close();
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(PHONE_TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
