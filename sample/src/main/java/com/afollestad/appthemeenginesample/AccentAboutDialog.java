package com.polaric.appthemeenginesample;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.polaric.appthemeengine.Config;
import com.polaric.materialdialogs.MaterialDialog;

/**
 * @author Aidan Follestad (polaric)
 */
public class AccentAboutDialog extends DialogFragment {

    public static void show(AppCompatActivity context) {
        AccentAboutDialog dialog = new AccentAboutDialog();
        dialog.show(context.getSupportFragmentManager(), "[ABOUT_DIALOG]");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity context = getActivity();
        final int accentColor = Config.accentColor(context);
        return new MaterialDialog.Builder(context)
                .title(R.string.about)
                .positiveText(R.string.dismiss)
                .titleColor(Config.primaryColor(context))
                .contentColor(Config.textColorSecondary(context))
                .linkColor(accentColor)
                .buttonRippleColor(accentColor)
                .positiveColor(accentColor)
                .content(Html.fromHtml(getString(R.string.about_body)))
                .contentLineSpacing(1.6f)
                .build();
    }
}