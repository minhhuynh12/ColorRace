package com.example.metfone.colorracemetfone.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.metfone.colorracemetfone.ui.login.model.QrCodeItem;

public class DbQrCode extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QrCode.db";
    public static final String TABLE_NAME = "GenQrCode";
    public static final String ISDN_CUSTOMER = "phone_number";
    public static final String QR_CODE = "qr_code";
    public DbQrCode(Context context) {
        super(context, DATABASE_NAME , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (id integer primary key, " + ISDN_CUSTOMER + " text ," + QR_CODE + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
        onCreate(db);
    }

    public boolean insertQrCode (String phone, String qrCode) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(ISDN_CUSTOMER, phone);
            contentValues.put(QR_CODE, qrCode);
            db.insert(TABLE_NAME, null, contentValues);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public QrCodeItem getQrCode(String isdn){
        QrCodeItem qrCodeItem = new QrCodeItem();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_NAME + " Where " + ISDN_CUSTOMER + " = " + isdn + "  order by " + ISDN_CUSTOMER + " desc limit 1", null );
        if (cursor.moveToFirst()) {
            do {

                qrCodeItem.setIsdn(cursor.getString(cursor.getColumnIndex(ISDN_CUSTOMER)));
                qrCodeItem.setQrCode(cursor.getString(cursor.getColumnIndex(QR_CODE)));
            } while (cursor.moveToNext());
        }
        return qrCodeItem;
    }

}
