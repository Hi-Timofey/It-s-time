package com.hitim.android.itstime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatePickedTest {
    private int hour;
    private int minutes;
    private int year;
    private int month;
    private int day;
    private DatePicked defaultDatePicked, nullable;
    private DatePicked onlyTime;
    private DatePicked onlyDate;

    @Before
    public void init() throws Exception {
        hour = 6;
        minutes = 45;
        year = 2019;
        month = 5;
        day = 30;
        defaultDatePicked = new DatePicked(hour,minutes,year,month,day);
        nullable = new DatePicked(hour,minutes,year,month,day);
        onlyDate = new DatePicked(year,month,day);
        onlyTime = new DatePicked(hour,minutes);

        nullable.resetAll();
    }

    @Test
    public void testDatePickedClass() throws Exception{
        assertEquals("Exception in Date class (HOUR)",hour,defaultDatePicked.getHour());
        assertEquals("Exception in Date class (MINUTES)",minutes,defaultDatePicked.getMinutes());
        assertEquals("Exception in Date class (YEAR)",year,defaultDatePicked.getYear());
        assertEquals("Exception in Date class (MONTH)",month,defaultDatePicked.getMonth());
        assertEquals("Exception in Date class (DAY)",day,defaultDatePicked.getDay());
    }

    @Test
    public void testDateType() throws Exception{
        assertEquals("Date type exception",DatePicked.TIME_DATE,defaultDatePicked.getType());
        assertEquals("Date type exception",DatePicked.DATE_ONLY,onlyDate.getType());
        assertEquals("Date type exception",DatePicked.TIME_ONLY,onlyTime.getType());
        assertEquals("Date type exception",DatePicked.NULL_TIME,nullable.getType());
    }
}
