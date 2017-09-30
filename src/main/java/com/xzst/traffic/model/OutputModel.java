package com.xzst.traffic.model;

/**
 * Created by 张超 on 2017/9/25.
 */
public class OutputModel {
//    //经度
//    private String longitude;
//    //纬度
//    private String latitude;
    //路口路段
    private int lkld;
    //年
    private String  year;
    //月
    private String  month;
    //日
    private String  day;
    //时间段
    private String  num;
    //天气
    private int  weather;
    //next星期
    private int nextWeek;
    //向后预测天数
    private int nextNum;
    //交通运行指数
    private String y1;
    //交通运行等级
    private String y2;

    public int getLkld() {return lkld;}

    public void setLkld(int lkld) {this.lkld = lkld;}

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getNextWeek() {
        return nextWeek;
    }

    public void setNextWeek(int nextWeek) {
        this.nextWeek = nextWeek;
    }

    public int getNextNum() {
        return nextNum;
    }

    public void setNextNum(int nextNum) {
        this.nextNum = nextNum;
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

    @Override
    public String toString() {
        return "OutputModel{" +
                "lkld=" + lkld +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", num='" + num + '\'' +
                ", weather=" + weather +
                ", nextWeek=" + nextWeek +
                ", nextNum=" + nextNum +
                ", y1='" + y1 + '\'' +
                ", y2='" + y2 + '\'' +
                '}';
    }
}
