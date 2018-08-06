package com.parkingsystem.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parkingsystem.entity.ParkingInfo;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.entity.User;
import com.parkingsystem.entity.UserInfo;
import com.parkingsystem.logs.LogUtil;

import java.util.ArrayList;

public class QueryUtils {

    private ParkingSqliteOpenHelper parkingSqliteOpenHelper;


    public QueryUtils(Context mContext) {
        parkingSqliteOpenHelper = new ParkingSqliteOpenHelper(mContext);
    }

    // **************************用户操作************************
    /**
     * 添加用户信息
     *
     * @param user
     * @return result 插入结果的判断值
     */
    public void addUserInfo(User user) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("_id", 1);
        contentValues.put("user_name", user.username);
        contentValues.put("real_name", user.realname);
        contentValues.put("gender", user.gender);
        contentValues.put("car_card", user.card);
        contentValues.put("phone", user.phone);
        contentValues.put("balance", user.balance);
        contentValues.put("car_state", user.carState);
        long result = database.insert("user_info", null, contentValues);

        database.close();
    }

     /**
     * 删除用户
     *
     * @param userName
     * @return
     */
    public int delUserInfo(String userName) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();

        int result = database.delete("user_info", "user_name=?",
                new String[]{userName});
        database.close();

        return result;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public int updateUserInfo(User user) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", user.username);
        contentValues.put("real_name", user.realname);
        contentValues.put("gender", user.gender);
        contentValues.put("car_card", user.card);
        contentValues.put("phone", user.phone);
        contentValues.put("balance", user.balance);

        int result = database.update("user_info", contentValues, "user_name=?",
                new String[]{user.username});
        database.close();

        return result;

    }

    /**
     * 查询用户名
     *
     * @return userName 用户名
     */
    public String queryUserName() {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        String userName = "";

        /*Cursor cursor = database.query("user_info", new String[]{"user_name"}, "_id=1",
                null, null,null,null);*/
        Cursor cursor = database.rawQuery("select user_name from user_info where _id=1",
                null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                userName = cursor.getString(0);
            }
            cursor.close();
        }
        database.close();
        return userName;
    }

    /**
     * 查询用户信息
     *
     * @param userName
     * @return userArrayList 用户信息
     */
    public User queryUserInfo(String userName) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        User user = new User();

        Cursor cursor = database.rawQuery("select * from user_info where user_name = ?",
                new String[]{userName});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user.id = cursor.getInt(0);
                user.username = cursor.getString(1);
                user.realname = cursor.getString(2);
                user.gender = cursor.getString(3);
                user.card = cursor.getString(4);
                user.phone = cursor.getString(5);
                user.balance = cursor.getString(6);
            }
            cursor.close();
        }
        database.close();

        return user;
    }


    // **************************停车状态操作************************
    /**
     * 查询用户停车状态
     *
     * @param userName
     * @return
     */
    public String queryCarState(String userName) {
        String state = "2";
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select car_state from user_info where user_name=?", new String[]{userName});
        if (cursor != null && cursor.getCount() >0) {
            while (cursor.moveToNext()) {
                state = cursor.getString(0);
            }
            cursor.close();
        }
        database.close();

        return state;
    }

    /**
     * 更新用户停车状态
     *
     * @param state
     * @param username
     * @return
     */
    public int updateCarState(String state, String username) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("car_state", state);

        int result = database.update("user_info", contentValues, "user_name=?",
                new String[]{username});
        database.close();

        return result;
    }


    // **************************停车记录操作************************
    /**
     * 查询停车记录
     */
    public ArrayList<ParkingInfo> queryLocalParkingRecord(String username) {

        ArrayList<ParkingInfo> parkingRecords = new ArrayList<>();

        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select user_name, parking_money, " +
                "start_time, end_time from parking_record where user_name = ?", new String[]{username});

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ParkingInfo parkingInfo = new ParkingInfo();
                parkingInfo.userName = cursor.getString(0);
                parkingInfo.cost = cursor.getString(1);
                parkingInfo.startTime = cursor.getString(2);
                parkingInfo.endTime = cursor.getString(3);

                parkingRecords.add(parkingInfo);
            }
            cursor.close();
        }
        database.close();

        return parkingRecords;
    }

    /**
     * 插入停车记录
     *
     * @param parkingInfos
     */
    public boolean addParkingRecord(ArrayList<ParkingInfo> parkingInfos) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        ParkingInfo parkingInfo = new ParkingInfo();
        ContentValues contentValues = new ContentValues();
        long result = 0;

        for (int i = 0; i < parkingInfos.size(); i++) {
            parkingInfo = parkingInfos.get(i);
            contentValues.put("user_name", parkingInfo.userName);
            contentValues.put("parking_money", parkingInfo.cost);
            contentValues.put("start_time", parkingInfo.startTime);
            contentValues.put("end_time", parkingInfo.endTime);

            result = database.insert("parking_record", null, contentValues);
        }
        database.close();

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除停车记录
     *
     * @param username
     * @return
     */
    public boolean delParkingRecord(String username) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();

        int result = database.delete("parking_record", "user_name=?",
                new String[]{username});
        database.close();

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }


    // **************************充值记录操作************************
    /**
     * 查询本地充值记录
     *
     * @param userName
     * @return
     */
    public ArrayList<TopupInfo> queryLocalTopupRecord(String userName) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();

        ArrayList<TopupInfo> topupInfoArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select user_name, topup_time, topup_money from" +
                " topup_record where user_name = ?", new String[]{userName});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                TopupInfo topupInfo = new TopupInfo();
                topupInfo.userName = cursor.getString(0);
                topupInfo.time = cursor.getString(1);
                topupInfo.money = cursor.getString(2);

                topupInfoArrayList.add(topupInfo);
            }
            cursor.close();
        }
        database.close();

        return topupInfoArrayList;
    }

    /**
     * 添加充值记录到本地
     *
     * @param topupInfos
     * @return
     */
    public boolean addTopupRecord(ArrayList<TopupInfo> topupInfos) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();
        TopupInfo topupInfo = new TopupInfo();
        ContentValues contentValues = new ContentValues();
        long result = 0;

        for (int i = 0; i < topupInfos.size(); i++) {
            topupInfo = topupInfos.get(i);
            contentValues.put("user_name", topupInfo.userName);
            contentValues.put("topup_money", topupInfo.money);
            contentValues.put("topup_time", topupInfo.time);

            result = database.insert("topup_record", null, contentValues);
        }
        database.close();

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除充值记录
     *
     * @param username
     * @return
     */
    public boolean delTopupRecord(String username) {
        SQLiteDatabase database = parkingSqliteOpenHelper.getReadableDatabase();

        int result = database.delete("topup_record", "user_name=?",
                new String[]{username});
        database.close();

        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }
}
