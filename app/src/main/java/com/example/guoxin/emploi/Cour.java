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
    private boolean visible;

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

        String empty = "";
        // set default category
        if (this.category == null || this.category.equals(empty))
            this.category = "Default";
        // set category from notes
        if (this.category.equals("Default") && (this.notes != null && (!this.notes.equals(empty)))) {
            if (this.notes.contains("DS "))
                this.category = "DS";
        }
        // delete category from notes
        if (this.notes != null && (!this.notes.equals(empty))) {
            this.notes = this.notes.replace(this.category, "");
            this.notes = this.notes.trim();
        }
        //set visibility and text
        if ((this.category.equals("Default")) && (this.notes == null || this.notes.equals(empty))) {
            this.visible = false;
            this.textLong = null;
            this.textShort = null;
        } else {
            this.visible = true;
            //test if category exist
            if (this.category.equals("Default"))
                this.textLong = this.notes;
            else this.textLong = this.category + '\n' + this.notes;
            //test if room exist
            if (this.room != null && (!this.room.equals(empty)))
                this.textLong = this.textLong + '\n' + this.room;

            this.textShort = getTextShort();
        }
    }

    public boolean getVisible() {
        return visible;
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

    private String getTextShort() {
        StringBuffer result = new StringBuffer(textLong);
        //supprimer les parenthèses
        int indexAvant = result.indexOf("(");
        int indexArriere = result.indexOf(")");
        while ((indexAvant != -1) && (indexArriere != -1)) {
            result.delete(indexAvant, indexArriere + 1);
            indexAvant = result.indexOf("(");
            indexArriere = result.indexOf(")");
        }
        return result.toString();
    }

}
