package com.example.guoxin.emploi;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Guoxin on 28/10/2016.
 * 
 */

public class CalendarUtil {

    private static Calendar c;
    private int currentWeekOfYear;
    private String currentMondyOfWeek;

    public CalendarUtil(){
        initCalendar();
    }

    private void initCalendar() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Paris");
        TimeZone.setDefault(timeZone);

        c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Log.i("today", dateFormat.format(c.getTime()));

        //la date du lundi de cette semaine
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        Log.i("dayOfWeek", Integer.toString(dayOfWeek));
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 1);
        // transfer à String
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentMondyOfWeek = dateFormat.format(c.getTime());
        Log.i("monday", currentMondyOfWeek);

        currentWeekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        Log.i("week", Integer.toString(currentWeekOfYear));
    }

    public int getMaxWeekNumOfYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public int getCurrentWeekOfYear() {
        return currentWeekOfYear;
    }
}
