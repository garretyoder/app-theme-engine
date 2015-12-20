package com.afollestad.appthemeengine.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATE;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEPrimaryTextView extends TextView {

    public ATEPrimaryTextView(Context context) {
        super(context);
        init(context);
    }

    public ATEPrimaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ATEPrimaryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATEPrimaryTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setTag("text_primary");
        ATE.apply(context, this);
    }
}
