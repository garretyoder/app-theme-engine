package com.afollestad.appthemeenginesample.prefs;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeenginesample.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATECheckBoxPreference extends CheckBoxPreference {

    public ATECheckBoxPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ATECheckBoxPreference(Context context) {
        this(context, null, 0);
    }

    public ATECheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.preference_custom);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        ATE.apply(view.getContext(), view);
    }
}