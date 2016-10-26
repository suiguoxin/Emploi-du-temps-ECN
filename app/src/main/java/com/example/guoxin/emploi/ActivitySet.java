package com.example.guoxin.emploi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Guoxin on 25/10/2016.
 */

public class ActivitySet extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String annee;
    private String option;
    private String groupe;

    private Context mContext;

    private Button btn_confirmer;
    private Button btn_annuler;
    private Spinner spinner_choix_annee;
    private Spinner spinner_choix_option;
    private Spinner spinner_choix_groupe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_info);

        initData();
        initView();
        initEvent();
    }
    private void initData(){
        mContext = ActivitySet.this;
    }

    private void initView(){
        btn_confirmer = (Button) findViewById(R.id.btn_confirmer);
        btn_annuler = (Button) findViewById(R.id.btn_annuler);

        spinner_choix_annee = (Spinner) findViewById(R.id.spinner_choix_annee);
        spinner_choix_option = (Spinner) findViewById(R.id.spinner_choix_option);
        spinner_choix_groupe = (Spinner) findViewById(R.id.spinner_choix_groupe);
    }

    private void initEvent(){
        btn_confirmer.setOnClickListener(this);
        btn_annuler.setOnClickListener(this);
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
                annee = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_choix_option:
                Toast.makeText(mContext, "Option ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                option = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_choix_groupe:
                Toast.makeText(mContext, "Groupe ：" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                groupe = parent.getItemAtPosition(position).toString();
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
                Util.annee = annee;
                Util.option = option;
                Util.groupe = groupe;
            /*   Intent it = new Intent(ActivitySet.this, MainActivity.class);
                 Bundle bd = new Bundle();
                bd.putString("annee", annee);
                bd.putString("option", option);
                bd.putString("groupe", groupe);
                it.putExtras(bd);
                */
               // MainActivity.instance.finish();
               // startActivity(it);
                finish();
                break;
            case R.id.btn_annuler:
                finish();
                break;
        }
    }

}

