package com.xzst.traffic.model;

/**
 * Created by 张超 on 2017/9/25.
 */
public class InputModel {
//    //经度
//    private String longitude;
//    //纬度
//    private String latitude;
    //路口路段
    private int lkld;
    //日期
    private String date;
    //年
    private String dt;
    //月
    private String mouth;
    //日
    private String day;
    //时间段
    private String timeRegion;
    //星期
    private int week;
    //是否节假日   1:是
    private int isHoliday;
    //天气
    private int weather;
    //是否有事件发生
    private int isEvent;
    //交通运行指数
    private String y1;
    //交通运行等级
    private String y2;
    //向后预测天数
    private String nextNum;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNextNum() {
        return nextNum;
    }

    public void setNextNum(String nextNum) {
        this.nextNum = nextNum;
    }

    public String getTimeRegion() {
        return timeRegion;
    }

    public void setTimeRegion(String timeRegion) {
        this.timeRegion = timeRegion;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(int isHoliday) {
        this.isHoliday = isHoliday;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(int isEvent) {
        this.isEvent = isEvent;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getLkld() {return lkld;}

    public void setLkld(int lkld) {this.lkld = lkld;}

    @Override
    public String toString() {
        return "InputModel{" +
                "lkld=" + lkld +
                ", date='" + date + '\'' +
                ", dt='" + dt + '\'' +
                ", mouth='" + mouth + '\'' +
                ", day='" + day + '\'' +
                ", timeRegion='" + timeRegion + '\'' +
                ", week=" + week +
                ", isHoliday=" + isHoliday +
                ", weather=" + weather +
                ", isEvent=" + isEvent +
                ", y1='" + y1 + '\'' +
                ", y2='" + y2 + '\'' +
                ", nextNum='" + nextNum + '\'' +
                '}';
    }
}
