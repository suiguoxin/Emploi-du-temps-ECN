package com.example.guoxin.emploi;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Calendar.*;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by Guoxin on 24/10/2016.
 */

public class Util {
    private String adresseOption;
    private String adresseGroupe;
    private static Document docOption;
    private static Document docGroupe;

    private static Calendar c;

    private int currentWeekOfYear;
    private String currentMondyOfWeek;

    public static String annee;
    public static String option;
    public static String groupe;

    public Util() {
        initHttp();
        initCalendar();
    }

    private void initHttp() {
        adresseOption = "http://website.ec-nantes.fr/sites/edtemps/p16643.xml";
        adresseGroupe = "http://website.ec-nantes.fr/sites/edtemps/g16568.xml";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    docOption = Jsoup.connect(adresseOption).get();
                    docGroupe = Jsoup.connect(adresseGroupe).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    public Cour[] getCoursDeSemaine(String choix) {
        //events de la semaine
        String requete = String.format("event[date=%s]", currentMondyOfWeek);
        Elements eventsDeSemaine = null;
        if (choix.equals("option"))
            eventsDeSemaine = docOption.select(requete);
        else if (choix.equals("groupe"))
            eventsDeSemaine = docGroupe.select(requete);
        int sizeEventsDeSemaine = eventsDeSemaine.size();

        Cour[] cours = new Cour[sizeEventsDeSemaine];
        int item = 0;

        //print les détails des events
        for (Element event : eventsDeSemaine) {
            Element elementDay = event.select("day").first();
            String textDay = elementDay.text();

            Element elementPrettyWeeks = event.select("prettyWeeks").first();
            String textPrettyWeeks = elementPrettyWeeks.text();

            Element elementCategory = event.select("category").first();
            String textCategory = "";
            if (elementCategory != null)
                textCategory = elementCategory.text();

            Element elementNotes = event.select("notes").first();
            String textNotes = "";
            if (elementNotes != null)
                textNotes = elementNotes.text();

            Element elementStarttime = event.select("starttime").first();
            String textStarttime = elementStarttime.text();

            Element elementEndtime = event.select("endtime").first();
            String textEndtime = elementEndtime.text();

            Element elementRoom = event.select("room").first();
            Element elementSalle = elementRoom.select("item").first();
            String textSalle = elementSalle.text();

            cours[item] = new Cour(Integer.parseInt(textDay), Integer.parseInt(textPrettyWeeks), textCategory, textNotes,
                    textStarttime, textEndtime, textSalle);
            item++;
        }
        return cours;
    }

    public Cour[] getCoursDeSemaine(int semaine, String choix) {
        /*
        Calendar cTemp = Calendar.getInstance();
        cTemp.add(c.getTime(), (semaine - currentWeekOfYear)*7);
        */
        /*
        c.set(Calendar.YEAR,2016);
        c.set(Calendar.WEEK_OF_YEAR,20);
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        */
        String tag = "date";
        c.setWeekDate(c.get(Calendar.YEAR), semaine, Calendar.MONDAY);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //  Log.i(tag,df.format(c.getTime()) );

        // transfer à String
        tag = "mondyOfWeek";

        String mondyOfWeek = df.format(c.getTime());
        //  Log.i(tag, mondyOfWeek);

        //events de la semaine
        String requete = String.format("event[date=%s]", mondyOfWeek);
        Elements eventsDeSemaine = null;
        if (choix.equals("option"))
            eventsDeSemaine = docOption.select(requete);
        else if (choix.equals("groupe"))
            eventsDeSemaine = docGroupe.select(requete);
        int sizeEventsDeSemaine = eventsDeSemaine.size();

        if(sizeEventsDeSemaine == 0)
            return null;

        Cour[] cours = new Cour[sizeEventsDeSemaine];
        int item = 0;

        //print les détails des events
        for (Element event : eventsDeSemaine) {
            Element elementDay = event.select("day").first();
            String textDay = elementDay.text();

            Element elementPrettyWeeks = event.select("prettyWeeks").first();
            String textPrettyWeeks = elementPrettyWeeks.text();

            Element elementCategory = event.select("category").first();
            String textCategory = "";
            if (elementCategory != null)
                textCategory = elementCategory.text();

            Element elementNotes = event.select("notes").first();
            String textNotes = "";
            if (elementNotes != null)
                textNotes = elementNotes.text();

            Element elementStarttime = event.select("starttime").first();
            String textStarttime = elementStarttime.text();

            Element elementEndtime = event.select("endtime").first();
            String textEndtime = elementEndtime.text();

            Element elementRoom = event.select("room").first();
            Element elementSalle = elementRoom.select("item").first();
            String textSalle = elementSalle.text();

            cours[item] = new Cour(Integer.parseInt(textDay), Integer.parseInt(textPrettyWeeks), textCategory, textNotes,
                    textStarttime, textEndtime, textSalle);
            item++;
        }
        return cours;
    }

    public static String[] getTitle(int semaine) {
        String[] title = new String[6];
        SimpleDateFormat df = new SimpleDateFormat("dd/MM");
        String tag = "dayOfWeek";

        // Calendar c = Calendar.getInstance();
        c.setWeekDate(c.get(Calendar.YEAR), semaine, Calendar.MONDAY);

        title[0] = Integer.toString(c.get(Calendar.YEAR));
        title[1] = df.format(c.getTime()) + "\nlundi";
        //Log.i(tag,title[0]);
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[2] = df.format(c.getTime()) + "\nmardi";
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[3] = df.format(c.getTime()) + "\nmercredi";
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[4] = df.format(c.getTime()) + "\njeudi";
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[5] = df.format(c.getTime()) + "\nvendredi";

        return title;
    }

}
