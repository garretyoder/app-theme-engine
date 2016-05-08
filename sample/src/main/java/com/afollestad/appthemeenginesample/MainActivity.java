package com.afollestad.appthemeenginesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeenginesample.base.BaseThemedActivity;
import com.afollestad.appthemeenginesample.collapsingtb.CollapsingToolbarActivity;
import com.afollestad.appthemeenginesample.dialogs.AboutDialog;
import com.afollestad.appthemeenginesample.misc.MiscActivity;
import com.afollestad.appthemeenginesample.rv.RecyclerViewSampleActivity;
import com.afollestad.appthemeenginesample.tabs.TabSampleActivity;
import com.afollestad.appthemeenginesample.widgets.WidgetActivity;

public class MainActivity extends BaseThemedActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Default config
        if (!ATE.config(this, "light_theme").isConfigured(4)) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppTheme)
                    .primaryColorRes(R.color.colorPrimaryLightDefault)
                    .accentColorRes(R.color.colorAccentLightDefault)
                    .coloredNavigationBar(false)
                    .navigationViewSelectedIconRes(R.color.colorAccentLightDefault)
                    .navigationViewSelectedTextRes(R.color.colorAccentLightDefault)
                    .commit();
        }
        if (!ATE.config(this, "dark_theme").isConfigured(4)) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .primaryColorRes(R.color.colorPrimaryDarkDefault)
                    .accentColorRes(R.color.colorAccentDarkDefault)
                    .coloredNavigationBar(true)
                    .navigationViewSelectedIconRes(R.color.colorAccentDarkDefault)
                    .navigationViewSelectedTextRes(R.color.colorAccentDarkDefault)
                    .commit();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.appbar_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.setDrawerListener(new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close));

        final NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search_view_example));
//        searchView.setIconifiedByDefault(false);
//        searchItem.expandActionView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawer.closeDrawers();
        final int mItemId = item.getItemId();
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (mItemId) {
                    case R.id.drawer_tabs:
                        startActivity(new Intent(MainActivity.this, TabSampleActivity.class));
                        break;
                    case R.id.drawer_recyclerview:
                        startActivity(new Intent(MainActivity.this, RecyclerViewSampleActivity.class));
                        break;
                    case R.id.drawer_collapsingtoolbar:
                        startActivity(new Intent(MainActivity.this, CollapsingToolbarActivity.class));
                        break;
                    case R.id.drawer_widgets:
                        startActivity(new Intent(MainActivity.this, WidgetActivity.class));
                        break;
                    case R.id.drawer_misc:
                        startActivity(new Intent(MainActivity.this, MiscActivity.class));
                        break;
                    case R.id.drawer_settings:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.drawer_about:
                        AboutDialog.show(MainActivity.this);
                        break;
                }
            }
        }, 75);
        return true;
    }
}