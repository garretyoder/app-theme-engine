package com.afollestad.appthemeengine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.reflect.Field;

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

    private final static String KEY_TEXTLINK_PRIMARY_COLOR = "text_link_primary_color";
    private final static String KEY_TEXTLINK_PRIMARY_COLOR_DARK = "text_link_primary_color_dark";
    private final static String KEY_TEXTLINK_ACCENT_COLOR = "text_link_accent_color";
    private final static String KEY_TEXTLINK_PRIMARY = "text_link_primary";
    private final static String KEY_TEXTLINK_SECONDARY = "text_link_secondary";

    private final static String KEY_TINT_PRIMARY_COLOR = "tint_primary_color";
    private final static String KEY_TINT_PRIMARY_COLOR_DARK = "tint_primary_color_dark";
    private final static String KEY_TINT_ACCENT_COLOR = "tint_accent_color";
    private final static String KEY_TINT_TEXT_PRIMARY = "tint_text_primary";
    private final static String KEY_TINT_TEXT_SECONDARY = "tint_text_secondary";

    private static Class<?> didPreApply = null;

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

            case KEY_TEXTLINK_PRIMARY_COLOR:
                ((TextView) current).setLinkTextColor(Config.primaryColor(context));
                break;
            case KEY_TEXTLINK_PRIMARY_COLOR_DARK:
                ((TextView) current).setLinkTextColor(Config.primaryColorDark(context));
                break;
            case KEY_TEXTLINK_ACCENT_COLOR:
                ((TextView) current).setLinkTextColor(Config.accentColor(context));
                break;
            case KEY_TEXTLINK_PRIMARY:
                ((TextView) current).setLinkTextColor(Config.textColorPrimary(context));
                break;
            case KEY_TEXTLINK_SECONDARY:
                ((TextView) current).setLinkTextColor(Config.textColorSecondary(context));
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

    private static void processNavigationView(@NonNull NavigationView view) {
        if (!Config.navigationViewThemed(view.getContext()))
            return;
        final ColorStateList iconSl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Config.navigationViewNormalIcon(view.getContext()),
                        Config.navigationViewSelectedIcon(view.getContext())
                });
        final ColorStateList textSl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Config.navigationViewNormalText(view.getContext()),
                        Config.navigationViewSelectedText(view.getContext())
                });
        view.setItemTextColor(textSl);
        view.setItemIconTintList(iconSl);
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
            if (current instanceof NavigationView) {
                processNavigationView((NavigationView) current);
            } else if (current.getTag() != null && current.getTag() instanceof String) {
                Log.d("ATE", "Processed view: " + current.getClass().getName());
                processTag(context, current);
            }
            if (current instanceof ViewGroup) {
                Log.d("ATE", "Processed group: " + current.getClass().getName());
                apply(context, (ViewGroup) current);
            }
        }
        final long diff = System.currentTimeMillis() - start;
        Log.d("ATE", String.format("Theme engine applied in %dms (%d seconds).", diff, diff / 1000));
    }

    public static Config config(@NonNull Context context) {
        return new Config(context);
    }

    public static boolean didValuesChange(@NonNull Context context, long updateTime) {
        return ATE.config(context).isConfigured() && Config.prefs(context).getLong(Config.VALUES_CHANGED, -1) > updateTime;
    }

    public static void preApply(@NonNull Activity activity) {
        didPreApply = activity.getClass();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            if (Config.coloredStatusBar(activity))
                window.setStatusBarColor(Config.statusBarColor(activity));
            else window.setStatusBarColor(Color.BLACK);
            if (Config.coloredNavigationBar(activity))
                window.setNavigationBarColor(Config.primaryColor(activity));
            else window.setNavigationBarColor(Color.BLACK);
            applyTaskDescription(activity);
        }
    }

    public static void apply(@NonNull View view) {
        if (view.getContext() == null)
            throw new IllegalStateException("View has no Context, use apply(Context, View) instead.");
        apply(view.getContext(), view);
    }

    public static void apply(@NonNull Context context, @NonNull View view) {
        if (view.getTag() != null)
            processTag(context, view);
        if (view instanceof ViewGroup)
            apply(context, (ViewGroup) view);
    }

    public static void apply(@NonNull Activity activity) {
        if (didPreApply == null)
            preApply(activity);
        if (Config.coloredActionBar(activity)) {
            if (activity instanceof AppCompatActivity) {
                final AppCompatActivity aca = (AppCompatActivity) activity;
                if (aca.getSupportActionBar() != null)
                    aca.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Config.primaryColor(activity)));
            } else if (activity.getActionBar() != null) {
                activity.getActionBar().setBackgroundDrawable(new ColorDrawable(Config.primaryColor(activity)));
            }
        }

        final ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        if (rootView instanceof DrawerLayout) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final int color = Config.coloredStatusBar(activity) ?
                        Color.TRANSPARENT : Color.BLACK;
                activity.getWindow().setStatusBarColor(color);
            }
            if (Config.coloredStatusBar(activity))
                ((DrawerLayout) rootView).setStatusBarBackgroundColor(Config.statusBarColor(activity));
        }

        apply(activity, rootView);
        didPreApply = null;
    }

    public static void apply(@NonNull android.support.v4.app.Fragment fragment) {
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        else if (fragment.getView() == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        apply(fragment.getActivity(), (ViewGroup) fragment.getView());
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply(fragment.getActivity());
    }

    public static void apply(@NonNull android.app.Fragment fragment) {
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        else if (fragment.getView() == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        apply(fragment.getActivity(), (ViewGroup) fragment.getView());
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply(fragment.getActivity());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void applyTaskDescription(@NonNull Activity activity) {
        // Sets color of entry in the system recents page
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(
                (String) activity.getTitle(),
                ((BitmapDrawable) activity.getApplicationInfo().loadIcon(activity.getPackageManager())).getBitmap(),
                Config.primaryColor(activity));
        activity.setTaskDescription(td);
    }

    public static void applyMenu(final @NonNull Toolbar mToolbar) {
        mToolbar.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field f1 = Toolbar.class.getDeclaredField("mMenuView");
                    f1.setAccessible(true);
                    ActionMenuView actionMenuView = (ActionMenuView) f1.get(mToolbar);

                    Field f2 = ActionMenuView.class.getDeclaredField("mPresenter");
                    f2.setAccessible(true);

                    //Actually ActionMenuPresenter
                    BaseMenuPresenter presenter = (BaseMenuPresenter) f2.get(actionMenuView);

                    Field f3 = presenter.getClass().getDeclaredField("mOverflowPopup");
                    f3.setAccessible(true);
                    MenuPopupHelper overflowMenuPopupHelper = (MenuPopupHelper) f3.get(presenter);
                    setTintForMenuPopupHelper(overflowMenuPopupHelper);

                    Field f4 = presenter.getClass().getDeclaredField("mActionButtonPopup");
                    f4.setAccessible(true);
                    MenuPopupHelper subMenuPopupHelper = (MenuPopupHelper) f4.get(presenter);
                    setTintForMenuPopupHelper(subMenuPopupHelper);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void setTintForMenuPopupHelper(MenuPopupHelper menuPopupHelper) {
        if (menuPopupHelper != null) {
            final ListView listView = menuPopupHelper.getPopup().getListView();
            listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        Field checkboxField = ListMenuItemView.class.getDeclaredField("mCheckBox");
                        checkboxField.setAccessible(true);
                        Field radioButtonField = ListMenuItemView.class.getDeclaredField("mRadioButton");
                        radioButtonField.setAccessible(true);

                        for (int i = 0; i < listView.getChildCount(); i++) {
                            View v = listView.getChildAt(i);
                            if (!(v instanceof ListMenuItemView)) {
                                continue;
                            }
                            ListMenuItemView iv = (ListMenuItemView) v;

                            CheckBox check = (CheckBox) checkboxField.get(iv);
                            if (check != null) {
                                TintHelper.setTint(check, Config.accentColor(listView.getContext()));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    check.setBackground(null);
                                }
                            }

                            RadioButton radioButton = (RadioButton) radioButtonField.get(iv);
                            if (radioButton != null) {
                                TintHelper.setTint(radioButton, Config.accentColor(listView.getContext()));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    radioButton.setBackground(null);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        //noinspection deprecation
                        listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
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
