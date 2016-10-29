package com.example.guoxin.emploi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by Guoxin on 29/10/2016.
 */

public class ActivityDiver extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Switch switch_mode_detail;
    private Button btn_rentrer;
    private boolean modeDetail = true;
    private String tag = "ActivityDiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diver);

        initData();
        initView();
        initEvent();
        
        Log.i(tag, "create");
    }

    private void initData() {
        modeDetail = (boolean)SpUtil.get(this,"modeDetail", modeDetail);
    }

    private void initView() {
        switch_mode_detail = (Switch) findViewById(R.id.switch_mode_detail);
        btn_rentrer = (Button) findViewById(R.id.btn_rentrer);
        switch_mode_detail.setChecked(modeDetail);
    }

    private void initEvent() {
        switch_mode_detail.setOnCheckedChangeListener(this);
        btn_rentrer.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_mode_detail:
                modeDetail = isChecked;
                SpUtil.put(this,"modeDetail", modeDetail);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rentrer:
                Intent it = new Intent(ActivityDiver.this, MainActivity.class);
                MainActivity.instance.finish();
                startActivity(it);
                finish();
        }
    }

}
