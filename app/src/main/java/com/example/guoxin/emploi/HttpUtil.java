package com.example.guoxin.emploi;

import android.os.Environment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Guoxin on 28/10/2016.
 */

public class HttpUtil {
    private String urlOption;
    private String urlGroupe;
    private Document docOption;
    private Document docGroupe;

    private String annee;
    private String option;
    private String groupe;

    private static Calendar c;

    public HttpUtil(String annee, String option, String groupe) {
        initData(annee, option, groupe);
        initCalendar();
        initHttp();
    }

    private void initData(String annee, String option, String groupe) {
        this.annee = annee;
        this.option = option;
        this.groupe = groupe;
    }

    private void initCalendar() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Paris");
        TimeZone.setDefault(timeZone);

        c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);

    }

    private void initHttp() {
        if (annee.equals("Ei2+")) { //Ei2+
            if (option.equals("INFO")) //INFO
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16643.xml";
            else if (option.equals("RV")) //RV
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16655.xml";
            else if (option.equals("SANTE")) //SANTE
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16680.xml";
            else //if (option.equals("ROBOTIQUE")) //ROBOTIQUE
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16656.xml";

            if (groupe.equals("M1")) //M1
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/g16568.xml";
            else if (groupe.equals("M2")) //M2
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/g16569.xml";
            else if (groupe.equals("M3")) //M3
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/g16570.xml";
            else //if (groupe.equals("M4")) //M4
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/g16571.xml";
        }

        try {
            docOption = Jsoup.connect(urlOption).get();
            docGroupe = Jsoup.connect(urlGroupe).get();
            Log.i("Document", "Connectioin Succed");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Document", "Connectioin Failed");
        }
    }

    private void initFile() {
        urlOption = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "Download" + File.separator + "urlOption.xml";
        urlGroupe = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "Download" + File.separator + "urlGroupe.xml";
//        try {
        File fileOption = new File(urlOption);
        File fileGroupe = new File(urlGroupe);
        if (fileGroupe.exists())
            Log.i("Document", "File  exists!");
        else Log.i("Document", "File  doesn't exists!");
        docOption = Jsoup.parse(urlOption);
        docGroupe = Jsoup.parse(urlGroupe);
        Log.i("Document", "File success");
//        }catch (IOException e) {
//            e.printStackTrace();
//            Log.i("Document", "File Failed");
//        }

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
        String title = docOption.title();
        Log.i("title", title);
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
        Log.i("size", Integer.toString(sizeEventsDeSemaine));

        if (sizeEventsDeSemaine == 0)
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
