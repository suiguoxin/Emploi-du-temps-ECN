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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Guoxin on 28/10/2016.
 */

public class HttpUtil {
    private String urlGroupe;
    private String urlOption;
    private String urlGroupeRSE;

    private Document docGroupe;
    private Document docOption;
    private Document docGroupeRSE;

    private String annee;
    private String groupe;
    private String option;
    private String groupeRSE;

    private static Calendar c;

    public HttpUtil(String annee, String groupe, String option, String groupeRSE) {
        initData(annee, groupe, option, groupeRSE);
        initCalendar();
        initHttp();
    }

    private void initData(String annee, String groupe, String option, String groupeRSE) {
        this.annee = annee;
        this.groupe = groupe;
        this.option = option;
        this.groupeRSE = groupeRSE;

        urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16691.xml"; //F
        urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16643.xml"; //INFO
        urlGroupeRSE = "http://website.ec-nantes.fr/sites/edtemps/g16568.xml"; //M1
    }

    private void initCalendar() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Paris");
        TimeZone.setDefault(timeZone);

        c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);

    }

    private void initHttp() {
        if (annee.equals("Ei1")) {
            if (groupe.equals("A"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16682.xml";
            else if (groupe.equals("B"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16684.xml";
            else if (groupe.equals("C"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16685.xml";
            else if (groupe.equals("D"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16686.xml";
            else if (groupe.equals("E"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16690.xml";
            else if (groupe.equals("F"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16691.xml";
            else if (groupe.equals("G"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16692.xml";
            else if (groupe.equals("H"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16693.xml";
            else if (groupe.equals("I"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16694.xml";
            else if (groupe.equals("J"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16695.xml";
            else if (groupe.equals("K"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16696.xml";
            else if (groupe.equals("L"))
                urlGroupe = "http://website.ec-nantes.fr/sites/edtemps/p16697.xml";


        } else if (annee.equals("Ei2")||annee.equals("Ei3+")) { //Ei2 ou Ei3+
            if (option.equals("INFO")) //INFO
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16643.xml";
            else if (option.equals("RV")) //RV
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16655.xml";
            else if (option.equals("SANTE")) //SANTE
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16680.xml";
            else if (option.equals("ROBOTIQUE")) //ROBOTIQUE
                urlOption = "http://website.ec-nantes.fr/sites/edtemps/p16656.xml";

            if (groupeRSE.equals("M1")) //M1
                urlGroupeRSE = "http://website.ec-nantes.fr/sites/edtemps/g16568.xml";
            else if (groupeRSE.equals("M2")) //M2
                urlGroupeRSE = "http://website.ec-nantes.fr/sites/edtemps/g16569.xml";
            else if (groupeRSE.equals("M3")) //M3
                urlGroupeRSE = "http://website.ec-nantes.fr/sites/edtemps/g16570.xml";
            else if (groupe.equals("M4")) //M4
                urlGroupeRSE = "http://website.ec-nantes.fr/sites/edtemps/g16571.xml";
        }

        try {
            docGroupe = Jsoup.connect(urlGroupe).get();
            docOption = Jsoup.connect(urlOption).get();
            docGroupeRSE = Jsoup.connect(urlGroupeRSE).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void initFile() {
//        urlOption = Environment.getExternalStorageDirectory().getPath()
//                + File.separator + "Download" + File.separator + "urlOption.xml";
//        urlGroupeRSE = Environment.getExternalStorageDirectory().getPath()
//                + File.separator + "Download" + File.separator + "urlGroupe.xml";
////        try {
//        File fileOption = new File(urlOption);
//        File fileGroupe = new File(urlGroupe);
//        if (fileGroupe.exists())
//            Log.i("Document", "File  exists!");
//        else Log.i("Document", "File  doesn't exists!");
//        docOption = Jsoup.parse(urlOption);
//        docGroupe = Jsoup.parse(urlGroupe);
//        Log.i("Document", "File success");
////        }catch (IOException e) {
////            e.printStackTrace();
////            Log.i("Document", "File Failed");
////        }
//
//    }


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

        String mondyOfWeek = df.format(c.getTime());
        //  Log.i(tag, mondyOfWeek);

        //events de la semaine
        String requete = String.format("event[date=%s]", mondyOfWeek);
        Elements eventsDeSemaine = null;
        if (choix.equals("option"))
            eventsDeSemaine = docOption.select(requete);
        else if (choix.equals("groupe"))
            eventsDeSemaine = docGroupeRSE.select(requete);
        int sizeEventsDeSemaine = eventsDeSemaine.size();
        Log.i("size", Integer.toString(sizeEventsDeSemaine));

        if (sizeEventsDeSemaine == 0)
            return null;

        Cour[] cours = new Cour[sizeEventsDeSemaine];
        int item = 0;

        //print les détails des events
        for (Element event : eventsDeSemaine) {
            cours[item] = getCour(event);
            item++;
        }
        return cours;
    }

    public ArrayList<Cour> getCoursDeSemaine(int semaine) {
        ArrayList<Cour> cours = new ArrayList<>();
        c.setWeekDate(c.get(Calendar.YEAR), semaine, Calendar.MONDAY);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String mondyOfWeek = df.format(c.getTime());

        //events de la semaine
        String requete = String.format("event[date=%s]", mondyOfWeek);

        if (annee.equals("Ei1")) {
            Elements eventsDeSemaineGroupe = docGroupe.select(requete);
            for (Element event : eventsDeSemaineGroupe) {
                Cour cour = getCour(event);
                if (cour.getVisible()) {
                    cours.add(cour);
                }
            }
        } else if (annee.equals("Ei2")) {
            Elements eventsDeSemaineOption = docOption.select(requete);
            for (Element event : eventsDeSemaineOption) {
                Cour cour = getCour(event);
                if (cour.getVisible()) {
                    cours.add(cour);
                }
            }
            Elements eventsDeSemaineGroupeRSE = docGroupeRSE.select(requete);
            for (Element event : eventsDeSemaineGroupeRSE) {
                Cour cour = getCour(event);
                if (cour.getVisible()) {
                    cours.add(cour);
                }
            }
        } else if (annee.equals("Ei3+")) {
            Elements eventsDeSemaineOption = docOption.select(requete);
            for (Element event : eventsDeSemaineOption) {
                Cour cour = getCour(event);
                if (cour.getVisible()) {
                    cours.add(cour);
                }
            }
        }

        return cours;
    }

    private Cour getCour(Element event) {
        Element elementDay = event.select("day").first();
        String textDay = null;
        if (elementDay != null)
            textDay = elementDay.text();

        Element elementPrettyWeeks = event.select("prettyWeeks").first();
        String textPrettyWeeks = null;
        if (elementPrettyWeeks != null)
            textPrettyWeeks = elementPrettyWeeks.text();

        Element elementCategory = event.select("category").first();
        String textCategory = null;
        if (elementCategory != null)
            textCategory = elementCategory.text();

        Element elementNotes = event.select("notes").first();
        String textNotes = null;
        if (elementNotes != null)
            textNotes = elementNotes.text();

        Element elementStarttime = event.select("starttime").first();
        String textStarttime = null;
        if (elementStarttime != null)
            textStarttime = elementStarttime.text();

        Element elementEndtime = event.select("endtime").first();
        String textEndtime = null;
        if (elementEndtime != null)
            textEndtime = elementEndtime.text();

        Element elementModule = event.select("module").first();
        String textMatiere = null;
        if (elementModule != null) {
            Element elementMatiere = elementModule.select("item").first();
            textMatiere = elementMatiere.text();
        }

        Element elementRoom = event.select("room").first();
        String textSalle = null;
        if (elementRoom != null) {
            Element elementSalle = elementRoom.select("item").first();
            textSalle = elementSalle.text();
        }

        Cour cour = new Cour(Integer.parseInt(textDay), Integer.parseInt(textPrettyWeeks), textCategory, textMatiere, textNotes,
                textStarttime, textEndtime, textSalle);
        return cour;
    }

    public static String[] getTitle(int semaine) {
        String[] title = new String[6];
        SimpleDateFormat df = new SimpleDateFormat("dd/MM");
        String tag = "dayOfWeek";

        // Calendar c = Calendar.getInstance();
        c.setWeekDate(c.get(Calendar.YEAR), semaine, Calendar.MONDAY);

        title[0] = Integer.toString(c.get(Calendar.YEAR));
        title[1] = "lundi\n" + df.format(c.getTime());
        //Log.i(tag,title[0]);
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[2] = "mardi\n" + df.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[3] = "mercredi\n" + df.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[4] = "jeudi\n" + df.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 1);
        title[5] = "vendredi\n" + df.format(c.getTime());

        return title;
    }

}
