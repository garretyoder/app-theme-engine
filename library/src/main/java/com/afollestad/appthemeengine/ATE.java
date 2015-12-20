package com.afollestad.appthemeengine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class ATE {

    private final static String KEY_BG_PRIMARY_COLOR = "bg_primary_color";
    private final static String KEY_BG_PRIMARY_COLOR_DARK = "bg_primary_color_dark";
    private final static String KEY_BG_ACCENT_COLOR = "bg_accent_color";
    private final static String KEY_BG_TEXT_PRIMARY = "bg_text_primary";
    private final static String KEY_BG_TEXT_SECONDARY = "bg_text_secondary";

    private final static String KEY_TEXT_PRIMARY_COLOR = "text_primary_color";
    private final static String KEY_TEXT_PRIMARY_COLOR_DARK = "text_primary_color_dark";
    private final static String KEY_TEXT_ACCENT_COLOR = "text_accent_color";
    private final static String KEY_TEXT_PRIMARY = "text_primary";
    private final static String KEY_TEXT_SECONDARY = "text_secondary";

    private final static String KEY_TINT_PRIMARY_COLOR = "tint_primary_color";
    private final static String KEY_TINT_PRIMARY_COLOR_DARK = "tint_primary_color_dark";
    private final static String KEY_TINT_ACCENT_COLOR = "tint_accent_color";
    private final static String KEY_TINT_TEXT_PRIMARY = "tint_text_primary";
    private final static String KEY_TINT_TEXT_SECONDARY = "tint_text_secondary";

    private static Class<?> didPreApply = null;
    protected static boolean didValuesChange = false;

    private static void processTagPart(@NonNull Context context, @NonNull View current, @NonNull String tag) {
        switch (tag) {
            case KEY_BG_PRIMARY_COLOR:
                current.setBackgroundColor(Config.primaryColor(context));
                break;
            case KEY_BG_PRIMARY_COLOR_DARK:
                current.setBackgroundColor(Config.primaryColorDark(context));
                break;
            case KEY_BG_ACCENT_COLOR:
                current.setBackgroundColor(Config.accentColor(context));
                break;
            case KEY_BG_TEXT_PRIMARY:
                current.setBackgroundColor(Config.textColorPrimary(context));
                break;
            case KEY_BG_TEXT_SECONDARY:
                current.setBackgroundColor(Config.textColorSecondary(context));
                break;

            case KEY_TEXT_PRIMARY_COLOR:
                ((TextView) current).setTextColor(Config.primaryColor(context));
                break;
            case KEY_TEXT_PRIMARY_COLOR_DARK:
                ((TextView) current).setTextColor(Config.primaryColorDark(context));
                break;
            case KEY_TEXT_ACCENT_COLOR:
                ((TextView) current).setTextColor(Config.accentColor(context));
                break;
            case KEY_TEXT_PRIMARY:
                ((TextView) current).setTextColor(Config.textColorPrimary(context));
                break;
            case KEY_TEXT_SECONDARY:
                ((TextView) current).setTextColor(Config.textColorSecondary(context));
                break;

            case KEY_TINT_PRIMARY_COLOR:
                TintHelper.setTintAuto(current, Config.primaryColor(context));
                break;
            case KEY_TINT_PRIMARY_COLOR_DARK:
                TintHelper.setTintAuto(current, Config.primaryColorDark(context));
                break;
            case KEY_TINT_ACCENT_COLOR:
                TintHelper.setTintAuto(current, Config.accentColor(context));
                break;
            case KEY_TINT_TEXT_PRIMARY:
                TintHelper.setTintAuto(current, Config.textColorPrimary(context));
                break;
            case KEY_TINT_TEXT_SECONDARY:
                TintHelper.setTintAuto(current, Config.textColorSecondary(context));
                break;
        }
    }

    private static void processTag(@NonNull Context context, @NonNull View current) {
        final String tag = (String) current.getTag();
        if (tag.contains(",")) {
            final String[] splitTag = tag.split(",");
            for (String part : splitTag)
                processTagPart(context, current, part);
        } else {
            processTagPart(context, current, tag);
        }
    }

    private static void apply(@NonNull Context context, @NonNull ViewGroup view) {
        final long start = System.currentTimeMillis();
        for (int i = 0; i < view.getChildCount(); i++) {
            final View current = view.getChildAt(i);
            if (current instanceof ViewGroup) {
                apply(context, (ViewGroup) current);
            } else if (current.getTag() != null && current.getTag() instanceof String) {
                processTag(context, current);
            }
        }
        final long diff = System.currentTimeMillis() - start;
        Log.d("ATE", String.format("Theme engine applied in %dms (%d seconds).", diff, diff / 1000));
    }

    public static Config config(@NonNull Context context) {
        return new Config(context);
    }

    public static boolean didValuesChange() {
        return didValuesChange;
    }

    public static void preApply(@NonNull AppCompatActivity activity) {
        didPreApply = activity.getClass();
        didValuesChange = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            if (Config.applyPrimaryDarkStatusBar(activity))
                window.setStatusBarColor(Config.primaryColorDark(activity));
            if (Config.applyPrimaryNavBar(activity))
                window.setNavigationBarColor(Config.primaryColor(activity));
        }
    }

    public static void apply(@NonNull Context context, @NonNull View view) {
        if (view instanceof ViewGroup) {
            apply(context, (ViewGroup) view);
        } else {
            processTag(context, view);
        }
    }

    public static void apply(@NonNull AppCompatActivity activity) {
        if (didPreApply == null)
            preApply(activity);
        if (Config.applyPrimarySupportAb(activity) && activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Config.primaryColor(activity)));
        final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        apply(activity, rootView);
        didValuesChange = false;
        didPreApply = null;
    }

    public static void apply(@NonNull android.support.v4.app.Fragment fragment) {
        didValuesChange = false;
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        else if (fragment.getView() == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        apply(fragment.getActivity(), (ViewGroup) fragment.getView());
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply((AppCompatActivity) fragment.getActivity());
    }

    public static void apply(@NonNull android.app.Fragment fragment) {
        didValuesChange = false;
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        else if (fragment.getView() == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        apply(fragment.getActivity(), (ViewGroup) fragment.getView());
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply((AppCompatActivity) fragment.getActivity());
    }

    @ColorInt
    private static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) return color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= by; // value component
        return Color.HSVToColor(hsv);
    }

    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        return shiftColor(color, 0.9f);
    }

    private ATE() {
    }
}