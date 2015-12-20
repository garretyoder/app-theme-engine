package com.afollestad.appthemeenginesample;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.Config;
import com.afollestad.materialdialogs.color.ColorChooserDialog;

public class MainActivity extends ATEActivity
        implements View.OnClickListener, ColorChooserDialog.ColorCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ATE.config(this)
                .applyPrimaryNavBar(true);

        findViewById(R.id.changePrimaryColor).setOnClickListener(this);
        findViewById(R.id.changeAccentColor).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int titleRes = v.getId() == R.id.changePrimaryColor ?
                R.string.change_primary_color : R.string.change_accent_color;
        new ColorChooserDialog.Builder(this, titleRes)
                .accentMode(v.getId() == R.id.changeAccentColor)
                .show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        Config config = ATE.config(this);
        if (dialog.isAccentMode()) {
            config.accentColor(selectedColor);
        } else {
            config.primaryColor(selectedColor)
                    .primaryColorDark(ATE.darkenColor(selectedColor));
        }
        config.apply(this);
    }
}