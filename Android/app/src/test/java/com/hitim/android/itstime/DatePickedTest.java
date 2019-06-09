package com.hitim.android.itstime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class DatePickedTest {
    private int hour = 6;
    private int minutes = 45;
    private int year = 2019;
    private int month = 5;
    private int day = 30;
    private DatePicked defaultDatePicked = new DatePicked(hour,minutes,year,month,day);

    @Test
    public void testDatePickedClass(){
        assertEquals("Exception in Date class (HOUR)",hour,defaultDatePicked.getHour());
        assertEquals("Exception in Date class (MINUTES)",minutes,defaultDatePicked.getMinutes());
        assertEquals("Exception in Date class (YEAR)",year,defaultDatePicked.getYear());
        assertEquals("Exception in Date class (MONTH)",month,defaultDatePicked.getMonth());
        assertEquals("Exception in Date class (DAY)",day,defaultDatePicked.getDay());
    }

}
