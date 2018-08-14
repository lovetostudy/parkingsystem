package com.parkingsystem.entity;

public class CarInfo {

    public String id;

    public String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
