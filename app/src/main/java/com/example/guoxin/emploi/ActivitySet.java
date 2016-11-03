package com.example.guoxin.emploi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Guoxin on 25/10/2016.
 * The class for setting user information
 */

public class ActivitySet extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public static String[] annees;
    public static String[] groupes;
    public String[] options;
    public String[] groupesRSE;

    private Context mContext;

    private Button btn_confirmer;
    private Button btn_annuler;
    private Spinner spinner_annee;
    private Spinner spinner_groupe;
    private Spinner spinner_option;
    private Spinner spinner_groupeRSE;
    private RelativeLayout layout_spinner_groupe;
    private RelativeLayout layout_spinner_option;
    private RelativeLayout layout_spinner_groupeRSE;

    private int choixAnnee = 1;
    private int choixGroupe = 5;
    private int choixOption = 0;
    private int choixGroupeRSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set);

        initData();
        initView();
        initEvent();
    }

    private void initData() {
        mContext = ActivitySet.this;

        annees = new String[]{"Ei1", "Ei2+"};
        groupes = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        options = new String[]{"INFO", "RV", "SANTE", "ROBOTIQUE"};
        groupesRSE = new String[]{"M1", "M2", "M3", "M4"};

        choixAnnee = (int) SpUtil.get(this, "choixAnnee", choixAnnee);
        choixGroupe = (int) SpUtil.get(this, "choixGroupe", choixGroupe);
        choixOption = (int) SpUtil.get(this, "choixOption", choixOption);
        choixGroupeRSE = (int) SpUtil.get(this, "choixGroupeRSE", choixGroupeRSE);
    }

    private void initView() {
        btn_confirmer = (Button) findViewById(R.id.btn_confirmer);
        btn_annuler = (Button) findViewById(R.id.btn_annuler);

        spinner_annee = (Spinner) findViewById(R.id.spinner_annee);
        ArrayAdapter<String> adadpterAnnee = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, annees);
        spinner_annee.setAdapter(adadpterAnnee);

        spinner_groupe = (Spinner) findViewById(R.id.spinner_groupe);
        ArrayAdapter<String> adadpterGroupe = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groupes);
        spinner_groupe.setAdapter(adadpterGroupe);

        spinner_option = (Spinner) findViewById(R.id.spinner_option);
        ArrayAdapter<String> adadpterOption = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, options);
        spinner_option.setAdapter(adadpterOption);

        spinner_groupeRSE = (Spinner) findViewById(R.id.spinner_groupeRSE);
        ArrayAdapter<String> adadpterGroupeRSE = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groupesRSE);
        spinner_groupeRSE.setAdapter(adadpterGroupeRSE);

        layout_spinner_groupe = (RelativeLayout) findViewById(R.id.layout_spinner_groupe);
        layout_spinner_option = (RelativeLayout) findViewById(R.id.layout_spinner_option);
        layout_spinner_groupeRSE = (RelativeLayout) findViewById(R.id.layout_spinner_groupeRSE);
    }

    private void initEvent() {
        btn_confirmer.setOnClickListener(this);
        btn_annuler.setOnClickListener(this);

        spinner_annee.setOnItemSelectedListener(this);
        spinner_groupe.setOnItemSelectedListener(this);
        spinner_option.setOnItemSelectedListener(this);
        spinner_groupeRSE.setOnItemSelectedListener(this);

        spinner_annee.setSelection(choixAnnee, true);
        spinner_groupe.setSelection(choixGroupe, true);
        spinner_option.setSelection(choixOption, true);
        spinner_groupeRSE.setSelection(choixGroupeRSE, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_annee:
                Toast.makeText(mContext, "Année ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixAnnee = position;
                if (choixAnnee == 0) {
                    layout_spinner_groupe.setVisibility(View.VISIBLE);
                    layout_spinner_option.setVisibility(View.GONE);
                    layout_spinner_groupeRSE.setVisibility(View.GONE);
                } else if (choixAnnee == 1) {
                    layout_spinner_groupe.setVisibility(View.GONE);
                    layout_spinner_option.setVisibility(View.VISIBLE);
                    layout_spinner_groupeRSE.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.spinner_groupe:
                Toast.makeText(mContext, "Option ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixGroupe = position;
                break;
            case R.id.spinner_option:
                Toast.makeText(mContext, "Option ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixOption = position;
                break;
            case R.id.spinner_groupeRSE:
                Toast.makeText(mContext, "Groupe ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                choixGroupeRSE = position;
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
                SpUtil.put(this, "choixAnnee", choixAnnee);
                SpUtil.put(this, "choixGroupe", choixGroupe);
                SpUtil.put(this, "choixOption", choixOption);
                SpUtil.put(this, "choixGroupeRSE", choixGroupeRSE);

                SpUtil.put(this, "annee", annees[choixAnnee]);
                SpUtil.put(this, "groupe", groupes[choixGroupe]);
                SpUtil.put(this, "option", options[choixOption]);
                SpUtil.put(this, "groupeRSE", groupesRSE[choixGroupeRSE]);

                Intent it = new Intent(ActivitySet.this, MainActivity.class);
//                 Bundle bd = new Bundle();
//                bd.putString("annee", annee);
//                bd.putString("option", option);
//                bd.putString("groupe", groupe);
//                it.putExtras(bd);
                if (MainActivity.instance != null)
                    MainActivity.instance.finish();
                startActivity(it);
                finish();
                break;
            case R.id.btn_annuler:
                finish();
                break;
        }
    }
}

