package com.afollestad.appthemeengine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ATE.preApply(this);
        super.onCreate(savedInstanceState);
        ATE.apply(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ATE.didValuesChange())
            recreate();
    }
}