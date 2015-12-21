package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class Config {

    private final static String CONFIG_PREFS_KEY = "[[afollestad_app-theme-engine]]";
    private final static String IS_CONFIGURED_KEY = "is_configured";
    protected final static String VALUES_CHANGED = "values_changed";

    private final static String KEY_PRIMARY_COLOR = "primary_color";
    private final static String KEY_PRIMARY_COLOR_DARK = "primary_color_dark";
    private final static String KEY_ACCENT_COLOR = "accent_color";
    private final static String KEY_STATUS_BAR_COLOR = "status_bar_color";

    private final static String KEY_TEXT_COLOR_PRIMARY = "text_color_primary";
    private final static String KEY_TEXT_COLOR_SECONDARY = "text_color_secondary";

    private final static String KEY_APPLY_PRIMARYDARK_STATUSBAR = "apply_primarydark_statusbar";
    private final static String KEY_APPLY_PRIMARY_SUPPORTAB = "apply_primary_supportab";
    private final static String KEY_APPLY_PRIMARY_NAVBAR = "apply_primary_navbar";
    private final static String KEY_AUTO_GENERATE_PRIMARYDARK = "auto_generate_primarydark";

    private final static String KEY_THEMED_NAVIGATION_VIEW = "apply_navigation_view";
    private final static String KEY_NAVIGATIONVIEW_SELECTED_TEXT = "navigation_view_selected_text";
    private final static String KEY_NAVIGATIONVIEW_NORMAL_TEXT = "navigation_view_normal_text";
    private final static String KEY_NAVIGATIONVIEW_SELECTED_ICON = "navigation_view_selected_icon";
    private final static String KEY_NAVIGATIONVIEW_NORMAL_ICON = "navigation_view_normal_icon";

    private Context mContext;
    private SharedPreferences.Editor mEditor;


    @SuppressLint("CommitPrefEdits")
    protected Config(@NonNull Context context) {
        mContext = context;
        mEditor = prefs(context).edit();
    }

    public Config primaryColor(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR, color);
        if (autoGeneratePrimaryDark(mContext))
            primaryColorDark(ATE.darkenColor(color));
        return this;
    }

    public boolean isConfigured() {
        return prefs(mContext).getBoolean(IS_CONFIGURED_KEY, false);
    }

    public Config primaryColorRes(@ColorRes int colorRes) {
        return primaryColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config primaryColorAttr(@AttrRes int colorAttr) {
        return primaryColor(Util.resolveColor(mContext, colorAttr));
    }

    public Config primaryColorDark(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR_DARK, color);
        return this;
    }

    public Config primaryColorDarkRes(@ColorRes int colorRes) {
        return primaryColorDark(ContextCompat.getColor(mContext, colorRes));
    }

    public Config primaryColorDarkAttr(@AttrRes int colorAttr) {
        return primaryColorDark(Util.resolveColor(mContext, colorAttr));
    }

    public Config accentColor(@ColorInt int color) {
        mEditor.putInt(KEY_ACCENT_COLOR, color);
        return this;
    }

    public Config accentColorRes(@ColorRes int colorRes) {
        return accentColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config accentColorAttr(@AttrRes int colorAttr) {
        return accentColor(Util.resolveColor(mContext, colorAttr));
    }

    public Config statusBarColor(@ColorInt int color) {
        mEditor.putInt(KEY_STATUS_BAR_COLOR, color);
        return this;
    }

    public Config statusBarColorRes(@ColorRes int colorRes) {
        return statusBarColor(ContextCompat.getColor(mContext, colorRes));
    }

    public Config statusBarColorAttr(@AttrRes int colorAttr) {
        return statusBarColor(Util.resolveColor(mContext, colorAttr));
    }

    public Config textColorPrimary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_PRIMARY, color);
        return this;
    }

    public Config textColorPrimaryRes(@ColorRes int colorRes) {
        return textColorPrimary(ContextCompat.getColor(mContext, colorRes));
    }

    public Config textColorPrimaryAttr(@AttrRes int colorAttr) {
        return textColorPrimary(Util.resolveColor(mContext, colorAttr));
    }

    public Config textColorSecondary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_SECONDARY, color);
        return this;
    }

    public Config textColorSecondaryRes(@ColorRes int colorRes) {
        return textColorSecondary(ContextCompat.getColor(mContext, colorRes));
    }

    public Config textColorSecondaryAttr(@AttrRes int colorAttr) {
        return textColorSecondary(Util.resolveColor(mContext, colorAttr));
    }

    public Config coloredStatusBar(boolean colored) {
        mEditor.putBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, colored);
        return this;
    }

    public Config coloredActionBar(boolean applyToActionBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, applyToActionBar);
        return this;
    }

    public Config coloredNavigationBar(boolean applyToNavBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_NAVBAR, applyToNavBar);
        return this;
    }

    public Config autoGeneratePrimaryDark(boolean autoGenerate) {
        mEditor.putBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, autoGenerate);
        return this;
    }

    public Config navigationViewThemed(boolean themed) {
        mEditor.putBoolean(KEY_THEMED_NAVIGATION_VIEW, themed);
        return this;
    }

    public Config navigationViewSelectedIcon(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_ICON, color);
        return this;
    }

    public Config navigationViewSelectedIconRes(@ColorRes int colorRes) {
        return navigationViewSelectedIcon(ContextCompat.getColor(mContext, colorRes));
    }

    public Config navigationViewSelectedIconAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedIcon(Util.resolveColor(mContext, colorAttr));
    }

    public Config navigationViewSelectedText(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_TEXT, color);
        return this;
    }

    public Config navigationViewSelectedTextRes(@ColorRes int colorRes) {
        return navigationViewSelectedText(ContextCompat.getColor(mContext, colorRes));
    }

    public Config navigationViewSelectedTextAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedText(Util.resolveColor(mContext, colorAttr));
    }

    public Config navigationViewNormalIcon(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_NORMAL_ICON, color);
        return this;
    }

    public Config navigationViewNormalIconRes(@ColorRes int colorRes) {
        return navigationViewNormalIcon(ContextCompat.getColor(mContext, colorRes));
    }

    public Config navigationViewNormalIconAttr(@AttrRes int colorAttr) {
        return navigationViewNormalIcon(Util.resolveColor(mContext, colorAttr));
    }

    public Config navigationViewNormalText(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_NORMAL_TEXT, color);
        return this;
    }

    public Config navigationViewNormalTextRes(@ColorRes int colorRes) {
        return navigationViewNormalText(ContextCompat.getColor(mContext, colorRes));
    }

    public Config navigationViewNormalTextAttr(@AttrRes int colorAttr) {
        return navigationViewNormalText(Util.resolveColor(mContext, colorAttr));
    }

    public void commit() {
        mEditor.putLong(VALUES_CHANGED, System.currentTimeMillis())
                .putBoolean(IS_CONFIGURED_KEY, true)
                .commit();
    }

    public void apply(@NonNull Activity activity) {
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
    protected static SharedPreferences prefs(@NonNull Context context) {
        return context.getSharedPreferences(CONFIG_PREFS_KEY, Context.MODE_PRIVATE);
    }

    @ColorInt
    public static int primaryColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR, Util.resolveColor(context, R.attr.colorPrimary, Color.parseColor("#455A64")));
    }

    @ColorInt
    public static int primaryColorDark(@NonNull Context context) {
        return prefs(context).getInt(KEY_PRIMARY_COLOR_DARK, Util.resolveColor(context, R.attr.colorPrimaryDark, Color.parseColor("#37474F")));
    }

    @ColorInt
    public static int accentColor(@NonNull Context context) {
        return prefs(context).getInt(KEY_ACCENT_COLOR, Util.resolveColor(context, R.attr.colorAccent, Color.parseColor("#263238")));
    }

    @ColorInt
    public static int statusBarColor(@NonNull Context context) {
        if (context instanceof ATEStatusBarCustomizer)
            return ((ATEStatusBarCustomizer) context).getStatusBarColor();
        return prefs(context).getInt(KEY_STATUS_BAR_COLOR, primaryColorDark(context));
    }

    @ColorInt
    public static int textColorPrimary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_PRIMARY, Util.resolveColor(context, android.R.attr.textColorPrimary));
    }

    @ColorInt
    public static int textColorSecondary(@NonNull Context context) {
        return prefs(context).getInt(KEY_TEXT_COLOR_SECONDARY, Util.resolveColor(context, android.R.attr.textColorSecondary));
    }

    public static boolean coloredStatusBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, true);
    }

    public static boolean coloredActionBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, true);
    }

    public static boolean coloredNavigationBar(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_APPLY_PRIMARY_NAVBAR, false);
    }

    public static boolean autoGeneratePrimaryDark(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, true);
    }

    public static boolean navigationViewThemed(@NonNull Context context) {
        return prefs(context).getBoolean(KEY_THEMED_NAVIGATION_VIEW, true);
    }

    @ColorInt
    public static int navigationViewSelectedIcon(@NonNull Context context) {
        return prefs(context).getInt(KEY_NAVIGATIONVIEW_SELECTED_ICON, accentColor(context));
    }

    @ColorInt
    public static int navigationViewSelectedText(@NonNull Context context) {
        return prefs(context).getInt(KEY_NAVIGATIONVIEW_SELECTED_TEXT, accentColor(context));
    }

    @ColorInt
    public static int navigationViewNormalIcon(@NonNull Context context) {
        return prefs(context).getInt(KEY_NAVIGATIONVIEW_NORMAL_ICON, textColorSecondary(context));
    }

    @ColorInt
    public static int navigationViewNormalText(@NonNull Context context) {
        return prefs(context).getInt(KEY_NAVIGATIONVIEW_NORMAL_TEXT, textColorPrimary(context));
    }
}