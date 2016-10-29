package com.example.guoxin.emploi;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static MainActivity instance;
    private Context mContext;
    private RelativeLayout layout;
    private RelativeLayout layoutLundi;
    private RelativeLayout layoutMardi;
    private RelativeLayout layoutMercredi;
    private RelativeLayout layoutJeudi;
    private RelativeLayout layoutVendredi;
    private Spinner spinner;
    private TextView tv_annee;
    private TextView tv_lundi;
    private TextView tv_mardi;
    private TextView tv_mercredi;
    private TextView tv_jeudi;
    private TextView tv_vendredi;
    private Button btn_set;
    private Button btn_diver;

    private int gridHeight, gridWidth;
    private boolean isFirst = true;

    private HttpUtil httpUtil;
    private CalendarUtil calendarUtil;
    private Cour[] cours;
    private ArrayList<String> mData = null;

    private int semaine;
    private String[] title;

    private String annee = "Ei2+";
    private String groupe = "A";
    private String option = "INFO";
    private String groupeRSE = "M1";

    private boolean modeDetail = true;

    public String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initEvent();
        Log.i("onCreate", "start");
    }

//    @Override
//    protected void onResume() {
//        recreate();
//    }

    private void initData() {
        instance = this;
        calendarUtil = new CalendarUtil();
        title = new String[6];
        semaine = calendarUtil.getCurrentWeekOfYear();
        int maxWeekNumOfYear = calendarUtil.getMaxWeekNumOfYear();

        mData = new ArrayList<>();
        for (int i = 1; i <= maxWeekNumOfYear; i++) {
            mData.add("Semaine " + i);
        }

        annee = (String) SpUtil.get(this, "annee", annee);
        groupe = (String) SpUtil.get(this, "groupe", groupe);
        option = (String) SpUtil.get(this, "option", option);
        groupeRSE = (String) SpUtil.get(this, "groupeRSE", groupeRSE);
        modeDetail = (boolean) SpUtil.get(this, "modeDetail", modeDetail);
    }

    private void initView() {
        mContext = MainActivity.this;
        layoutLundi = (RelativeLayout) findViewById(R.id.Monday);
        layoutMardi = (RelativeLayout) findViewById(R.id.Tuesday);
        layoutMercredi = (RelativeLayout) findViewById(R.id.Wednesday);
        layoutJeudi = (RelativeLayout) findViewById(R.id.Thursday);
        layoutVendredi = (RelativeLayout) findViewById(R.id.Friday);

        tv_annee = (TextView) findViewById(R.id.tv_annee);
        tv_lundi = (TextView) findViewById(R.id.tv_lundi);
        tv_mardi = (TextView) findViewById(R.id.tv_mardi);
        tv_mercredi = (TextView) findViewById(R.id.tv_mercredi);
        tv_jeudi = (TextView) findViewById(R.id.tv_jeudi);
        tv_vendredi = (TextView) findViewById(R.id.tv_vendredi);

        btn_set = (Button) findViewById(R.id.btn_set);
        btn_diver = (Button) findViewById(R.id.btn_diver);

        spinner = (Spinner) findViewById(R.id.spinner_choix_semaine);
        ArrayAdapter<String> myAdadpter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mData);
        spinner.setAdapter(myAdadpter);
        // Log.i("init1",Integer.toString(semaine));
//        spinner.setSelection(semaine - 1, true);
    }

    private void initEvent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Looper.prepare();
//                Handler handler = new Handler();
//                getMainLooper()
                httpUtil = new HttpUtil(annee, groupe, option, groupeRSE);
                Message msg = new Message();
                msg.what = 0x123;
//                Bundle bundle = new Bundle();
//                bundle.putInt(UPPER_NUM ,
//                        Integer.parseInt(etNum.getText().toString()));
//                msg.setData(bundle);
                handler.sendMessage(msg);
                Log.i("msg", "msg envoyé");
//                Looper.loop();
            }
        }).start();

        btn_set.setOnClickListener(this);
        btn_diver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_set:
                intent.setClass(MainActivity.this, ActivitySet.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.btn_diver:
                intent.setClass(MainActivity.this, ActivityDiver.class);
                startActivity(intent);
                //finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_choix_semaine:
                Toast.makeText(mContext, "Tu as choisi ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                semaine = position + 1;
                addCoursDeSemaine();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirst) {
            isFirst = false;
            gridWidth = layoutLundi.getWidth();
            gridHeight = layoutLundi.getHeight() / 12;
        }
    }

    //ToolBar Menu initialisation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    //ToolBar Items initialisation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            this.finish();
            return true;
        }
        if (id == R.id.action_refresh) {
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private TextView createTv(String starttime, String endtime, String text) {
        tag = "creatTv";
        TextView tv = new TextView(this);
        int start = 0, end = 0;
        if (starttime.equals("08:00")) start = 1;
        else if (starttime.equals("09:00")) start = 2;
        else if (starttime.equals("10:15")) start = 3;
        else if (starttime.equals("11:15")) start = 4;
        else if (starttime.equals("13:45")) start = 5;
        else if (starttime.equals("14:45")) start = 6;
        else if (starttime.equals("16:00")) start = 7;
        else if (starttime.equals("17:00")) start = 8;
        else if (starttime.equals("18:15")) start = 9;
        else if (starttime.equals("19:15")) start = 10;
        else if (starttime.equals("20:30")) start = 11;


        if (endtime.equals("09:00")) end = 1;
        else if (endtime.equals("10:00")) end = 2;
        else if (endtime.equals("11:15")) end = 3;
        else if (endtime.equals("12:15")) end = 4;
        else if (endtime.equals("14:45")) end = 5;
        else if (endtime.equals("15:45")) end = 6;
        else if (endtime.equals("17:00")) end = 7;
        else if (endtime.equals("18:00")) end = 8;
        else if (endtime.equals("19:15")) end = 9;
        else if (endtime.equals("20:15")) end = 10;
        else if (endtime.equals("21:30")) end = 11;
        else if (endtime.equals("22:30")) end = 12;
        /*
         指定高度和宽度
         */
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, gridHeight * (end - start + 1));
        Log.i(tag, Integer.toString(gridWidth));
        Log.i(tag, Integer.toString(gridHeight));
        /*
        指定位置
         */
        tv.setY(gridHeight * (start - 1));
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(text);

        return tv;
    }

    private void addView(Cour cour) {
        TextView tv;
        switch (cour.day) {
            case 0:
                layout = (RelativeLayout) findViewById(R.id.Monday);
                break;
            case 1:
                layout = (RelativeLayout) findViewById(R.id.Tuesday);
                break;
            case 2:
                layout = (RelativeLayout) findViewById(R.id.Wednesday);
                break;
            case 3:
                layout = (RelativeLayout) findViewById(R.id.Thursday);
                break;
            case 4:
                layout = (RelativeLayout) findViewById(R.id.Friday);
                break;
        }
        if (modeDetail)
            tv = createTv(cour.starttime, cour.endtime, cour.textLong);
        else tv = createTv(cour.starttime, cour.endtime, cour.textShort);

        if (cour.category.equals("CM"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_cm);
        else if (cour.category.equals("TD"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_td);
        else if (cour.category.equals("TP"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_tp);
        else if (cour.category.equals("Journée partenaire"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_jp);
        else if (cour.category.equals("Journée férié"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_jf);
        else if (cour.category.equals("Vacances"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_vacances);
        else
            tv.setBackgroundResource(R.drawable.txt_radiuborder_df);

        layout.addView(tv);
    }

    private void addPasDeCour() {
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, gridHeight * 2);

        String pas_de_cour = "Pas De Cour Cette Semaine!";

        tv.setY(gridHeight);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(pas_de_cour);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.Wednesday);
        layout.addView(tv);
    }

    private void addCoursDeSemaine() {
        deleteCours();
        setTitle();

        cours = httpUtil.getCoursDeSemaine(semaine);
        if (cours != null) {
            for (Cour c : cours) {
                addView(c);
            }
        } else addPasDeCour();
    }

//    private void addCoursDeSemaine() {
//        deleteCours();
//        setTitle();
//        boolean pasDeCour = true;
//
//        cours = httpUtil.getCoursDeSemaine(semaine, "option");
//        if (cours != null) {
//            Log.i("addCours", "option");
//            pasDeCour = false;
//            for (Cour c : cours) {
//                addView(c);
//            }
//        }
//
//        cours = httpUtil.getCoursDeSemaine(semaine, "groupe");
//        if (cours != null) {
//            Log.i("addCours", "groupe");
//            pasDeCour = false;
//            for (Cour c : cours) {
//                addView(c);
//            }
//        }
//
//        if (pasDeCour) addPasDeCour();
//    }

    private void deleteCours() {
        layoutLundi.removeAllViews();
        layoutMardi.removeAllViews();
        layoutMercredi.removeAllViews();
        layoutJeudi.removeAllViews();
        layoutVendredi.removeAllViews();

        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);
        TextView tv5 = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, gridHeight);
        tv1.setLayoutParams(params);
        tv2.setLayoutParams(params);
        tv3.setLayoutParams(params);
        tv4.setLayoutParams(params);
        tv5.setLayoutParams(params);
        tv1.setY(0);
        tv2.setY(0);
        tv3.setY(0);
        tv4.setY(0);
        tv5.setY(0);
        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");
        tv5.setText("");

        layoutLundi.addView(tv1);
        layoutMardi.addView(tv2);
        layoutMercredi.addView(tv3);
        layoutJeudi.addView(tv4);
        layoutVendredi.addView(tv5);
    }

    private void setTitle() {
        title = HttpUtil.getTitle(semaine);

        tv_annee.setText(title[0]);
        tv_lundi.setText(title[1]);
        tv_mardi.setText(title[2]);
        tv_mercredi.setText(title[3]);
        tv_jeudi.setText(title[4]);
        tv_vendredi.setText(title[5]);
    }

    final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x123) {
                spinner.setOnItemSelectedListener(MainActivity.this);
                spinner.setSelection(semaine - 1, true);
            }
        }
    };

}
