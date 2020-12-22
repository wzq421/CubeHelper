package com.example.myapplication.bean.rxbus;

public class Grade_Change {
    //1表示添加成绩，-1表示删除成绩
    private int action;
    private String grade;
    //增加成绩时默认为0，删除成绩时会记录其索引，防止同样的成绩删除错误
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
