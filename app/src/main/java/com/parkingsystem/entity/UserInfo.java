package com.parkingsystem.entity;


/**
 * Item of user's information
 */
public class UserInfo {

    private String labelName = "";       // 标签名

    private String displayValue = "";    // 显示的值


    public UserInfo() {}

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

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
