package com.example.myapplication.bean;

public class UserInfo {
    private String record;
    private String name;

    public UserInfo(String record, String name) {
        this.record = record;
        this.name = name;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
