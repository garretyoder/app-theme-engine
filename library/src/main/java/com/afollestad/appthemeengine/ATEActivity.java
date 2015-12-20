package com.afollestad.appthemeengine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEActivity extends AppCompatActivity {

    private long updateTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ATE.preApply(this);
        super.onCreate(savedInstanceState);
        ATE.apply(this);
        updateTime = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ATE.didValuesChange(this, updateTime))
            recreate();
    }
}