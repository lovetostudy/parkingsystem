package com.parkingsystem.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database create
 */
public class ParkingSqliteOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public ParkingSqliteOpenHelper(Context context) {
        super(context, "user.db", null, 1);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user_info (_id integer primary key, user_name varchar(20), " +
                "real_name varchar(20), gender varchar(10), car_card varchar(20)," +
                "phone varchar(10), balance varchar(10), car_state varchar(10))");
        db.execSQL("create table parking_record (_id integer primary key autoincrement, user_name varchar(20)," +
                "start_time varchar(20), end_time varchar(20), parking_money varchar(10))");
        db.execSQL("create table topup_record (_id integer primary key autoincrement, user_name varchar(20)," +
                "topup_time varchar(20), topup_money varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
