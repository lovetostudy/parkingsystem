package com.parkingsystem.entity;

public class ParkingInfo {

    public String userName;    // 用户名

    public String cost;         // 费用

    public String startTime;   // 开始时间

    public String endTime;     // 结束时间

    public ParkingInfo() {}

    public ParkingInfo(String userName, String cost, String startTime, String endTime) {
        this.userName = userName;
        this.cost = cost;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
