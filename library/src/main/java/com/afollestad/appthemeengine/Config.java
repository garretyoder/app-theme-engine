package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class Config {

    private final static String CONFIG_PREFS_KEY = "[[afollestad_app-theme-engine]]";
    private final static String KEY_PRIMARY_COLOR = "primary_color";
    private final static String KEY_PRIMARY_COLOR_DARK = "primary_color_dark";
    private final static String KEY_ACCENT_COLOR = "accent_color";

    private final static String KEY_TEXT_COLOR_PRIMARY = "text_color_primary";
    private final static String KEY_TEXT_COLOR_SECONDARY = "text_color_secondary";

    private final static String KEY_APPLY_PRIMARYDARK_STATUSBAR = "apply_primarydark_statusbar";
    private final static String KEY_APPLY_PRIMARY_SUPPORTAB = "apply_primary_supportab";
    private final static String KEY_APPLY_PRIMARY_NAVBAR = "apply_primary_navbar";

    private Context mContext;
    private SharedPreferences.Editor mEditor;


    @SuppressLint("CommitPrefEdits")
    protected Config(@NonNull Context context) {
        mContext = context;
        mEditor = prefs(context).edit();
    }

    public Config primaryColor(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR, color);
        return this;
    }

    public Config primaryColorRes(@ColorRes int colorRes) {
        return primaryColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config primaryColorDark(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR_DARK, color);
        return this;
    }

    public Config primaryColorDarkRes(@ColorRes int colorRes) {
        return primaryColorDark(ContextCompat.getColor(mContext, colorRes));
    }

    public Config accentColor(@ColorInt int color) {
        mEditor.putInt(KEY_ACCENT_COLOR, color);
        return this;
    }

    public Config accentColorRes(@ColorRes int colorRes) {
        return accentColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config textColorPrimary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_PRIMARY, color);
        return this;
    }

    public Config textColorPrimaryRes(@ColorRes int colorRes) {
        return textColorPrimary(ContextCompat.getColor(mContext, colorRes));
    }

    public Config textColorSecondary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_SECONDARY, color);
        return this;
    }

    public Config textColorSecondaryRes(@ColorRes int colorRes) {
        return textColorSecondary(ContextCompat.getColor(mContext, colorRes));
    }

    public Config applyPrimaryDarkStatusBar(boolean applyToStatusBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, applyToStatusBar);
        return this;
    }

    public Config applyPrimarySupportAb(boolean applyToStatusBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, applyToStatusBar);
        return this;
    }

    public Config applyPrimaryNavBar(boolean applyToStatusBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_NAVBAR, applyToStatusBar);
        return this;
    }

    public void commit() {
        if (mContext instanceof Activity)
            ATE.didValuesChange = true;
        mEditor.commit();
    }

    public void apply(@NonNull AppCompatActivity activity) {
        commit();
        ATE.apply(activity);
    }

    public void apply(@NonNull android.support.v4.app.Fragment fragment) {
        commit();
        ATE.apply(fragment);
    }

    public void apply(@NonNull android.app.Fragment fragment) {
        commit();
        ATE.apply(fragment);
    }

    public void apply(@NonNull View view) {
        commit();
        ATE.apply(view.getContext(), view);
    }

    @NonNull
    private static SharedPreferences prefs(@NonNull Context context) {
        return context.getSharedPreferences(CONFIG_PREFS_KEY, Context.MODE_PRIVATE);
    }

    @ColorInt
    public static int primaryColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR, Util.resolveColor(context, R.attr.colorPrimary));
    }

    @ColorInt
    public static int primaryColorDark(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR_DARK, Util.resolveColor(context, R.attr.colorPrimaryDark));
    }

    @ColorInt
    public static int accentColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_ACCENT_COLOR, Util.resolveColor(context, R.attr.colorAccent));
    }

    @ColorInt
    public static int textColorPrimary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_PRIMARY, Util.resolveColor(context, android.R.attr.textColorPrimary));
    }

    @ColorInt
    public static int textColorSecondary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_SECONDARY, Util.resolveColor(context, android.R.attr.textColorSecondary));
    }

    public static boolean applyPrimaryDarkStatusBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, true);
    }

    public static boolean applyPrimarySupportAb(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, true);
    }

    public static boolean applyPrimaryNavBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_NAVBAR, false);
    }
}