package com.afollestad.appthemeengine.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.afollestad.appthemeengine.ATE;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATECheckBox extends CheckBox {

    public ATECheckBox(Context context) {
        super(context);
        init(context);
    }

    public ATECheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ATECheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATECheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setTag("tint_accent_color");
        ATE.apply(context, this);
    }
}
