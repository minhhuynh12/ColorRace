package com.example.metfone.colorracemetfone.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.metfone.colorracemetfone.ui.SignData.model.CheckSignDataItem;
import com.example.metfone.colorracemetfone.ui.showResultQrCode.model.ContactItem;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SignPhoneNumber.db";
    public static final String ISDN_CUSTOMER = "phone_number";
    public static final String TABLE_NAME = "contacts";
    public static final String CHECKIN_TIME = "datetime";
    public static final String STATUS = "status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//        db.execSQL(
//                "create table contacts " +
//                        "(id integer primary key,phone_number text, )"
//        );
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key, " + ISDN_CUSTOMER + " text ," + STATUS + " text ," + CHECKIN_TIME + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact (String phone, String status, String datetime) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ISDN_CUSTOMER, phone);
            contentValues.put(STATUS, status);
            contentValues.put(CHECKIN_TIME, datetime);
            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public boolean checkSignData() {
        List<CheckSignDataItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from contacts order by phone_number desc limit 1", null );

        if (cursor.moveToFirst()) {
            do {
                CheckSignDataItem checkSignDataItem = new CheckSignDataItem();
                checkSignDataItem.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ISDN_CUSTOMER)));
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
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + ISDN_CUSTOMER + " = '" + phoneNumber +"'", null );

        if (cursor.moveToFirst()) {
            do {
                CheckSignDataItem checkSignDataItem = new CheckSignDataItem();
                checkSignDataItem.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ISDN_CUSTOMER)));
                list.add(checkSignDataItem);
            } while (cursor.moveToNext());
        }

        if (list.size() > 0){
            return true;
        }

        return false;
    }

    public ContactItem getStatus(String phoneNumber){
        ContactItem contactItem = new ContactItem();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + ISDN_CUSTOMER + " = '" + phoneNumber +"'", null );

            if (cursor.moveToFirst()) {
                do {
                    contactItem.setPhonenumber(cursor.getString(cursor.getColumnIndex(ISDN_CUSTOMER)));
                    contactItem.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                    contactItem.setDatetime(cursor.getString(cursor.getColumnIndex(CHECKIN_TIME)));
                } while (cursor.moveToNext());
            }

        }catch (Exception ex){
            Log.d("error" , "ex " + ex.getMessage());
        }
        return contactItem;
    }

    public int checkSize(){
        List<CheckSignDataItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        "select count(*) countS from LstIsdn order by updateCreate desc"
        Cursor cursor =  db.rawQuery( "select count(*) " + ISDN_CUSTOMER + " from " + TABLE_NAME, null );
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();
        return count;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ISDN_CUSTOMER);
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

    public boolean updateStatus (String isdn, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, status);
        db.update("contacts", contentValues, " "+ ISDN_CUSTOMER + "= ? ", new String[] { isdn } );
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
            array_list.add(res.getString(res.getColumnIndex(ISDN_CUSTOMER)));
            res.moveToNext();
        }
        return array_list;
    }
}
