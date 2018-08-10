package com.parkingsystem.entity;

public  class TopupItem {

    public static final int EDIT_TYPE = 0;
    public static final int TEXT_TYPE = 1;

    public String price;

    private int type;

    public TopupItem(int type, String price) {
        this.type = type;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
