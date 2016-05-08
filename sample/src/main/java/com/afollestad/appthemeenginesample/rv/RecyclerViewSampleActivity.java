package com.afollestad.appthemeenginesample.rv;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.appthemeenginesample.R;
import com.afollestad.appthemeenginesample.base.BaseThemedActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class RecyclerViewSampleActivity extends BaseThemedActivity implements ATEActivityThemeCustomizer {

    @StyleRes
    @Override
    public int getActivityTheme() {
        // Make sure we don't use the one set to the Config, since we want a non-toolbar-actionbar for this activity
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dark_theme", false) ?
                R.style.AppThemeDark_ActionBar : R.style.AppTheme_ActionBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setAdapter(new SampleRVAdapter());

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
