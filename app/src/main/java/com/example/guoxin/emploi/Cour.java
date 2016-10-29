package com.example.guoxin.emploi;

/**
 * Created by Guoxin on 23/10/2016.
 */

public class Cour {

    public int day;
    public int prettyWeeks;
    public String category;
    public String notes;
    public String starttime;
    public String endtime;
    public String room;
    //la text qu'on utilise pour afficher
    public String textLong;
    public String textShort;

    public Cour(int day, int prettyWeeks, String category, String notes, String starttime, String endtime, String room) {
        /**
         * day = 0 lundi , day = 3 jeudi
         */
        this.day = day;
        this.prettyWeeks = prettyWeeks;
        this.category = category;
        this.notes = notes;
        this.starttime = starttime;
        this.endtime = endtime;
        this.room = room;
        textLong = notes + '\n' + room;
        textShort = notes;
    }

    public String toString() {
        String res = "";
        res = res + "day: " + day;
        res = res + " prettyWeeks: " + prettyWeeks;
        res = res + " category: " + category;
        res = res + " notes: " + notes;
        res = res + " starttime: " + starttime;
        res = res + " endtime: " + endtime;
        res = res + " room: " + room;
        return res;
    }
}
