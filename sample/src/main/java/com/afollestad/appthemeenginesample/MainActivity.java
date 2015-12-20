package com.afollestad.appthemeenginesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.Config;
import com.afollestad.materialdialogs.color.ColorChooserDialog;

public class MainActivity extends ATEActivity implements ColorChooserDialog.ColorCallback {

    private DrawerLayout mDrawer;

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

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.setDrawerListener(new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close));

        final NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawer.closeDrawers();
                if (item.getItemId() == R.id.settings) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        }
                    });
                    return false;
                } else if (item.getItemId() == R.id.about) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            AccentAboutDialog.show(MainActivity.this);
                        }
                    });
                    return false;
                }
                return true;
            }
        });
        navView.getMenu().findItem(R.id.home).setChecked(true);
    }

    private void post(Runnable runnable) {
        mDrawer.postDelayed(runnable, 200);
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