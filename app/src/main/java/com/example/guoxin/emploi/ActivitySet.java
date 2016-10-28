package com.example.guoxin.emploi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Guoxin on 25/10/2016.
 * The class for setting user information
 */

public class ActivitySet extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public static String[] annees;
    public String[] options;
    public String[] groupes;

    private Context mContext;

    private Button btn_confirmer;
    private Button btn_annuler;
    private Spinner spinner_choix_annee;
    private Spinner spinner_choix_option;
    private Spinner spinner_choix_groupe;

    public static int choixAnnee = 1;
    public static int choixOption = 0;
    public static int choixGroupe = 0;

    public static final String PREFERENCE_NAME = "userinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_info);

        initData();
        initView();
        initEvent();
    }

    private void initData() {
        mContext = ActivitySet.this;

        annees = new String[]{"Ei1", "Ei2+"};
        options = new String[]{"INFO", "RV", "SANTE", "ROBOTIQUE"};
        groupes = new String[]{"M1", "M2", "M3", "M4"};

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        choixAnnee = sharedPreferences.getInt("choixAnnee", choixAnnee);
        choixOption = sharedPreferences.getInt("choixOption", choixOption);
        choixGroupe = sharedPreferences.getInt("choixGroupe", choixGroupe);
    }

    private void initView() {
        btn_confirmer = (Button) findViewById(R.id.btn_confirmer);
        btn_annuler = (Button) findViewById(R.id.btn_annuler);

        spinner_choix_annee = (Spinner) findViewById(R.id.spinner_choix_annee);
        ArrayAdapter<String> adadpterAnnee = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, annees);
        spinner_choix_annee.setAdapter(adadpterAnnee);

        spinner_choix_option = (Spinner) findViewById(R.id.spinner_choix_option);
        ArrayAdapter<String> adadpterOption = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, options);
        spinner_choix_option.setAdapter(adadpterOption);

        spinner_choix_groupe = (Spinner) findViewById(R.id.spinner_choix_groupe);
        ArrayAdapter<String> adadpterGroupe = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groupes);
        spinner_choix_groupe.setAdapter(adadpterGroupe);
    }

    private void initEvent() {
        btn_confirmer.setOnClickListener(this);
        btn_annuler.setOnClickListener(this);

        spinner_choix_annee.setSelection(choixAnnee, true);
        spinner_choix_option.setSelection(choixOption, true);
        spinner_choix_groupe.setSelection(choixGroupe, true);

        spinner_choix_annee.setOnItemSelectedListener(this);
        spinner_choix_option.setOnItemSelectedListener(this);
        spinner_choix_groupe.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_choix_annee:
                Toast.makeText(mContext, "Année ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixAnnee = position;
                break;
            case R.id.spinner_choix_option:
                Toast.makeText(mContext, "Option ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixOption = position;
                break;
            case R.id.spinner_choix_groupe:
                Toast.makeText(mContext, "Groupe ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixGroupe = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmer:
                Intent it = new Intent(ActivitySet.this, MainActivity.class);
//                 Bundle bd = new Bundle();
//                bd.putString("annee", annee);
//                bd.putString("option", option);
//                bd.putString("groupe", groupe);
//                it.putExtras(bd);
                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("choixAnnee", choixAnnee);
                editor.putInt("choixOption", choixOption);
                editor.putInt("choixGroupe", choixGroupe);
                editor.putString("annee", annees[choixAnnee]);
                editor.putString("option", options[choixOption]);
                editor.putString("groupe", groupes[choixGroupe]);

                editor.commit();

                MainActivity.instance.finish();
                startActivity(it);
                finish();
                break;
            case R.id.btn_annuler:
                finish();
                break;
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i("stop", "donne keep");
//    }

}

