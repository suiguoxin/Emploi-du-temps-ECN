package com.example.guoxin.emploi;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static MainActivity instance;
    private Context mContext;
    private LinearLayout layout_main_welcome;
    private LinearLayout layout_main_scroll;
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

    private TextView tv_set;
    private TextView tv_diver;

    private int gridHeight, gridWidth;
    private boolean isFirst = true;

    private HttpUtil httpUtil;
    private CalendarUtil calendarUtil;
    private ArrayList<String> mData = null;

    private int dayOfWeek;
    private int weekSelected;
    private int currentWeek;
    private String[] title;

    private String annee = "Ei2+";
    private String groupe = "A";
    private String option = "INFO";
    private String groupeRSE = "M1";

    private boolean modeDetail = false;

    public String tag;
    //
    private static final int ADDLISTENER = 0x123;
    //welcome animation
    private static final int STOPWELCOME = 0x234;
    private static final long WELCOMETIME = 2000;
    private static boolean afficheWelcome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWelcome();
        initData();
        initView();
        initEvent();
    }

    private void initWelcome() {
        layout_main_welcome = (LinearLayout) findViewById(R.id.layout_main_welcome);

        if (afficheWelcome) {
            Animation anim_window_outer = AnimationUtils.loadAnimation(this, R.anim.anim_window_outer);
            layout_main_welcome.startAnimation(anim_window_outer);
            Message msg = new Message();
            msg.what = STOPWELCOME;
            handler.sendMessageDelayed(msg, WELCOMETIME);
        } else layout_main_welcome.setVisibility(View.GONE);
    }

    private void initData() {
        instance = this;
        calendarUtil = new CalendarUtil();
        title = new String[6];
        dayOfWeek = calendarUtil.getDayOfWeek();
        currentWeek = calendarUtil.getCurrentWeekOfYear();
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
        layout_main_scroll = (LinearLayout) findViewById(R.id.layout_main_scroll);
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

        tv_set = (TextView) findViewById(R.id.tv_set);
        tv_diver = (TextView) findViewById(R.id.tv_diver);

        spinner = (Spinner) findViewById(R.id.spinner_choix_semaine);
        ArrayAdapter<String> myAdadpter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mData);
        spinner.setAdapter(myAdadpter);
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
                msg.what = ADDLISTENER;
//                Bundle bundle = new Bundle();
//                bundle.putInt(UPPER_NUM ,
//                        Integer.parseInt(etNum.getText().toString()));
//                msg.setData(bundle);
                handler.sendMessage(msg);
                Log.i("msg", "msg envoyé");
//                Looper.loop();
            }
        }).start();

        tv_set.setOnClickListener(this);
        tv_diver.setOnClickListener(this);

        tv_lundi.setOnClickListener(this);
        tv_mardi.setOnClickListener(this);
        tv_mercredi.setOnClickListener(this);
        tv_jeudi.setOnClickListener(this);
        tv_vendredi.setOnClickListener(this);

        changeWeight(dayOfWeek);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        LinearLayout.LayoutParams lp;
        switch (v.getId()) {
            case R.id.tv_set:
                intent.setClass(MainActivity.this, ActivitySet.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.tv_diver:
                intent.setClass(MainActivity.this, ActivityDiver.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.tv_lundi:
                changeWeight(1);
                break;
            case R.id.tv_mardi:
                changeWeight(2);
                break;
            case R.id.tv_mercredi:
                changeWeight(3);
                break;
            case R.id.tv_jeudi:
                changeWeight(4);
                break;
            case R.id.tv_vendredi:
                changeWeight(5);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_choix_semaine:
                Toast.makeText(mContext, "Tu as choisi ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                weekSelected = position + 1;
                addCoursDeSemaine();
                if (weekSelected == currentWeek) {
                    switch (dayOfWeek) {
                        case 1:
                            tv_lundi.setTextColor(ContextCompat.getColor(this, R.color.colorFirst));
                            break;
                        case 2:
                            tv_mardi.setTextColor(ContextCompat.getColor(this, R.color.colorFirst));
                            break;
                        case 3:
                            tv_mercredi.setTextColor(ContextCompat.getColor(this, R.color.colorFirst));
                            break;
                        case 4:
                            tv_jeudi.setTextColor(ContextCompat.getColor(this, R.color.colorFirst));
                            break;
                        case 5:
                            tv_vendredi.setTextColor(ContextCompat.getColor(this, R.color.colorFirst));
                            break;
                    }
                }
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
//            DisplayMetrics dm = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(dm);
//            int screenHeight = dm.heightPixels;
//
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout_main_scroll.getLayoutParams();
//            if (modeDetail)
//                params.height = screenHeight * 2;
//            else
//                params.height = screenHeight;
//            layout_main_scroll.setLayoutParams(params);
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, gridHeight * (end - start + 1));
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
        else if (cour.category.equals("DS"))
            tv.setBackgroundResource(R.drawable.txt_radiuborder_ds);
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

        ArrayList<Cour> cours = httpUtil.getCoursDeSemaine(weekSelected);
        if (cours.isEmpty())
            addPasDeCour();
        else {
            for (Cour c : cours) {
                addView(c);
            }
        }
    }

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
        title = HttpUtil.getTitle(weekSelected);

        tv_annee.setText(title[0]);
        tv_lundi.setText(title[1]);
        tv_mardi.setText(title[2]);
        tv_mercredi.setText(title[3]);
        tv_jeudi.setText(title[4]);
        tv_vendredi.setText(title[5]);
    }

    final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ADDLISTENER:
                    spinner.setOnItemSelectedListener(MainActivity.this);
                    spinner.setSelection(currentWeek - 1, true);
                    break;
                case STOPWELCOME:
                    layout_main_welcome.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void changeWeight(int day) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        tv_lundi.setLayoutParams(lp);
        tv_mardi.setLayoutParams(lp);
        tv_mercredi.setLayoutParams(lp);
        tv_jeudi.setLayoutParams(lp);
        tv_vendredi.setLayoutParams(lp);

        layoutLundi.setLayoutParams(lp);
        layoutMardi.setLayoutParams(lp);
        layoutMercredi.setLayoutParams(lp);
        layoutJeudi.setLayoutParams(lp);
        layoutVendredi.setLayoutParams(lp);

        lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.3f);
        switch (day) {
            case 1:
                tv_lundi.setLayoutParams(lp);
                layoutLundi.setLayoutParams(lp);
                break;
            case 2:
                tv_mardi.setLayoutParams(lp);
                layoutMardi.setLayoutParams(lp);
                break;
            case 3:
                tv_mercredi.setLayoutParams(lp);
                layoutMercredi.setLayoutParams(lp);
                break;
            case 4:
                tv_jeudi.setLayoutParams(lp);
                layoutJeudi.setLayoutParams(lp);
                break;
            case 5:
                tv_vendredi.setLayoutParams(lp);
                layoutVendredi.setLayoutParams(lp);
                break;
        }
    }

}
