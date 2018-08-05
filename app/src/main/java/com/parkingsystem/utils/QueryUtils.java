package com.parkingsystem.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parkingsystem.entity.User;
import com.parkingsystem.entity.UserInfo;
import com.parkingsystem.logs.LogUtil;

import java.util.ArrayList;

public class QueryUtils {

    private ParkingSqliteOpenHelper parkingSqliteOpenHelper;


    public QueryUtils(Context mContext) {
        parkingSqliteOpenHelper = new ParkingSqliteOpenHelper(mContext);
    }

    /**
     * 添加用户信息
     *
     * @param user
     * @return result 插入结果的判断值
     */
    public boolean addUserInfo(User user) {
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

        if (result != -1) {
            return true;
        } else {
            return false;
        }
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
        /*ArrayList<User> userArrayList = new ArrayList<>();*/
        User user = new User();

        Cursor cursor = database.rawQuery("select * from user_info where user_name = ?",
                new String[]{userName});
        /*_id,user_name,real_name,gender,car_card,phone,balance*/
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
/*                User user = new User();*/
                user.id = cursor.getInt(0);
                user.username = cursor.getString(1);
                user.realname = cursor.getString(2);
                user.gender = cursor.getString(3);
                user.card = cursor.getString(4);
                user.phone = cursor.getString(5);
                user.balance = cursor.getString(6);
/*                userArrayList.add(user);*/
            }
            cursor.close();
        }
        database.close();

        return user;
    }

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

}
