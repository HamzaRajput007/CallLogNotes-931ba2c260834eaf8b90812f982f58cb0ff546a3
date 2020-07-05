package com.developer.calllogmanager.Models;

public class ReminderModel {
    int hours,minutes,dayOfMonth,month,year;

    public ReminderModel(int hours, int minutes, int dayOfMonth, int month, int year, String ampm) {
        this.hours = hours;
        this.minutes = minutes;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
        this.ampm = ampm;
    }

    public ReminderModel(){

    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    String ampm;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
