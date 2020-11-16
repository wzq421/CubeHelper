package com.example.myapplication.bean;

public class MainItemBean {
    private String grade;
    private int state;


    public MainItemBean(String grade, int state) {
        this.grade = grade;
        this.state = state;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
