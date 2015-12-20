package com.afollestad.appthemeenginesample;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.Config;
import com.afollestad.materialdialogs.color.ColorChooserDialog;

public class MainActivity extends ATEActivity implements ColorChooserDialog.ColorCallback {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!ATE.config(this).isConfigured())
            ATE.config(this).coloredNavigationBar(true).commit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close));
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        Config config = ATE.config(this);
        if (dialog.isAccentMode()) {
            config.accentColor(selectedColor);
        } else {
            config.primaryColor(selectedColor);
        }
        config.apply(this);
    }
}