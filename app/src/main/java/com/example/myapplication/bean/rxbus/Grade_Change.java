package com.example.myapplication.bean.rxbus;

public class Grade_Change {
    private int action;
    private String grade;
    private int state;

    public Grade_Change(int action, String grade,int state) {
        this.action = action;
        this.grade = grade;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
