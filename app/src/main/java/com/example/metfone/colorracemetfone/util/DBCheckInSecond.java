package com.example.metfone.colorracemetfone.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.metfone.colorracemetfone.ui.SignData.model.CheckSignDataItem;
import com.example.metfone.colorracemetfone.ui.checkInSecond.model.CheckInSecondItem;

import java.util.ArrayList;
import java.util.List;

public class DBCheckInSecond extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CheckInSecond.db";
    public static final String TABLE_NAME = "CheckIn";
    public static final String ISDN_CUSTOMER = "isdn";
    public static final String TICKET_TYPE = "ticket_type";
    public static final String CHECKIN_TIME = "datetime";

    public DBCheckInSecond(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key, " + ISDN_CUSTOMER + " text ," + TICKET_TYPE + " text, " + CHECKIN_TIME + "text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + "  ");
        onCreate(db);
    }

    public boolean insertContact (String phone, String tickType , String checkInTime) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ISDN_CUSTOMER, phone);
            contentValues.put(TICKET_TYPE, tickType);
            contentValues.put(CHECKIN_TIME, checkInTime);
            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean checkIsData(String phoneNumber) {
        List<CheckInSecondItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + ISDN_CUSTOMER + " = '" + phoneNumber +"'", null );

        if (cursor.moveToFirst()) {
            do {
                CheckInSecondItem checkSignDataItem = new CheckInSecondItem();
                checkSignDataItem.setInsd(cursor.getString(cursor.getColumnIndex(ISDN_CUSTOMER)));
                list.add(checkSignDataItem);
            } while (cursor.moveToNext());
        }

        if (list.size() > 0){
            return true;
        }

        return false;
    }

    public CheckInSecondItem getISDN(String isdn){
        CheckInSecondItem checkSignDataItem = new CheckInSecondItem();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME + " WHERE " + ISDN_CUSTOMER + " = '" + isdn +"'", null );

        if (cursor.moveToFirst()) {
            do {
                checkSignDataItem.setInsd(cursor.getString(cursor.getColumnIndex(ISDN_CUSTOMER)));
                checkSignDataItem.setTicket_type(cursor.getString(cursor.getColumnIndex(TICKET_TYPE)));
                checkSignDataItem.setTicket_type(cursor.getString(cursor.getColumnIndex(CHECKIN_TIME)));
            } while (cursor.moveToNext());
        }
        return checkSignDataItem;
    }

}
