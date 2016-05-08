package com.polaric.appthemeenginesample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.polaric.appthemeengine.ATE;
import com.polaric.appthemeengine.ATEActivity;

public class MainActivity extends ATEActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!ATE.config(this).isConfigured()) {
            // Default config
            ATE.config(this)
                    .primaryColor(Color.parseColor("#455A64"))
                    .accentColor(Color.parseColor("#263238"))
                    .coloredNavigationBar(true)
                    .commit();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.setDrawerListener(new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close));

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        ATE.applyMenu(mToolbar);
        return super.onMenuOpened(featureId, menu);
    }
}