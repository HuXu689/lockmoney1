package com.example.ltdd.Model;

import java.util.Calendar;

public class CalendarSetUp {
    private  int day;
    private  int month;
    private  int year;
    Calendar calendar;
    public CalendarSetUp()
    {
        calendar = Calendar.getInstance();
        SetDefautDate();
    }
    public CalendarSetUp(int day , int month , int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        calendar = Calendar.getInstance();
    }
    public  int getDay()
    {
        return  day;
    }
    public  int getMonth()
    {
        return  month ;
    }
    public  int getYear(){
        return  year;
    }
    public  void setDay(int day)
    {
        this.day = day;
    }
    public  void setMonth(int month)
    {
        this.month = month;
    }
    public  void setYear(int year)
    {
        this.year = year;
    }
    public  String getDate()
    {
        return  this.day + "/" + this.month + "/" + this.year;
    }
    public    void SetDefautDate()
    {
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) +1;
        year = calendar.get(Calendar.YEAR);
    }
    public void NextDay() {
        day++;
        int totalday = getTotalDaysInMonth(year,month);
        if(day > totalday)
        {
            day = 1;
            month++;
            if(month>12)
            {
                month = 1;
                year++;
            }
        }
    }

    public void PreviousDay() {
        day--;


        if(day<1)
        {
            month--;
            int totalday =getTotalDaysInMonth(year,month);
            day = totalday;
            if(month<1)
            {
                year--;
                month = 1;
            }
        }
    }
    public void  show()
    {
        System.out.println(day + " " + month + " " + year) ;
    }
    private int getTotalDaysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, 1); // Set the date to the first day of the specified month
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // Get the maximum day of the month
    }

}