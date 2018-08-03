package com.parkingsystem.entity;

public class UserInfo {

    private String labelName = "";       // 标签名

    private String displayValue = "";    // 显示的值


    public UserInfo(String labelName, String displayValue) {
        this.labelName = labelName;
        this.displayValue = displayValue;
    }

    public String getLabelName() {
        return labelName;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
