package com.example.guoxin.emploi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Guoxin on 01/11/2016.
 */

public class ActivityWelcome extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);

        initWelcome();

        Button btn_welcome_set = (Button) findViewById(R.id.btn_welcome_set);
        Button btn_welcome_voir = (Button) findViewById(R.id.btn_welcome_voir);

        btn_welcome_set.setOnClickListener(this);
        btn_welcome_voir.setOnClickListener(this);
    }

    private void initWelcome() {
        RelativeLayout layout_welcome = (RelativeLayout) findViewById(R.id.layout_welcome);
        Animation anim_window_enter = AnimationUtils.loadAnimation(this, R.anim.anim_window_enter);
        layout_welcome.startAnimation(anim_window_enter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_welcome_set:
                intent.setClass(ActivityWelcome.this, ActivitySet.class);
                startActivity(intent);
                break;
            case R.id.btn_welcome_voir:
                intent.setClass(ActivityWelcome.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
