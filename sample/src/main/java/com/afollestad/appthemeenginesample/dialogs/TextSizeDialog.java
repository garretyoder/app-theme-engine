package com.afollestad.appthemeenginesample.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeenginesample.R;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Locale;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TextSizeDialog extends DialogFragment implements MaterialDialog.SingleButtonCallback {

    private final static String TAG = "[text-size-dialog]";
    private final static String KEY_MODE = "textsize_mode";
    private final static String KEY_ATEKEY = "ate_key";
    private final static String KEY_TITLE = "title";
    private final static String KEY_RECREATE = "recreate_on_apply";

    private TextView mPreview;
    private SeekBar mSeeker;
    private TextView mValue;

    public static void show(@NonNull Activity context, @NonNull @Config.TextSizeMode String textSizeMode,
                            @Nullable String ateKey, @StringRes int title, boolean recreateOnApply) {
        TextSizeDialog dialog = new TextSizeDialog();
        Bundle args = new Bundle();
        args.putString(KEY_MODE, textSizeMode);
        args.putString(KEY_ATEKEY, ateKey);
        args.putInt(KEY_TITLE, title);
        args.putBoolean(KEY_RECREATE, recreateOnApply);
        dialog.setArguments(args);
        dialog.show(context.getFragmentManager(), TAG);
    }

    private int spToPx(int sp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics()));
    }

    public static int pxToSp(Fragment context, int px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity);
    }

    @SuppressWarnings("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getArguments() != null;
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(getArguments().getInt(KEY_TITLE))
                .customView(R.layout.dialog_textsize, true)
                .negativeText(android.R.string.cancel)
                .positiveText(android.R.string.ok)
                .neutralText(R.string.defaultValue)
                .autoDismiss(false)
                .onAny(this)
                .build();

        final View view = dialog.getCustomView();
        assert view != null;
        mPreview = (TextView) view.findViewById(R.id.preview);
        mSeeker = (SeekBar) view.findViewById(R.id.seeker);
        mValue = (TextView) view.findViewById(R.id.value);

        String mode = getArguments().getString(KEY_MODE);
        if (mode != null)
            mode = mode.substring(mode.indexOf('|') + 1);

        final int defaultValue = Config.textSizeForMode(getActivity(),
                getArguments().getString(KEY_ATEKEY), mode);
        mPreview.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultValue);
        mSeeker.setMax(111);
        final int dpValue = pxToSp(this, defaultValue);
        mSeeker.setProgress(dpValue - 1);
        mValue.setText(String.format(Locale.getDefault(), "%dsp", dpValue));

        mSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress++;
                if (fromUser) {
                    final int pxValue = spToPx(progress);
                    mPreview.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxValue);
                    mValue.setText(String.format(Locale.getDefault(), "%dsp", progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return dialog;
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (which == DialogAction.POSITIVE) {
            dismiss();
            if (getActivity() == null) return;
            ATE.config(getActivity(), getArguments().getString(KEY_ATEKEY))
                    .textSizeSpForMode(mSeeker.getProgress() + 1, getArguments().getString(KEY_MODE))
                    .apply(getActivity());
            if (getArguments().getBoolean(KEY_RECREATE))
                getActivity().recreate();
        } else if (which == DialogAction.NEUTRAL) {
            @Config.TextSizeMode
            final String mode = getArguments().getString(KEY_MODE);
            assert mode != null;
            int size;
            switch (mode) {
                default:
                case Config.TEXTSIZE_CAPTION:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_caption);
                    break;
                case Config.TEXTSIZE_BODY:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_body);
                    break;
                case Config.TEXTSIZE_SUBHEADING:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_subheading);
                    break;
                case Config.TEXTSIZE_TITLE:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_title);
                    break;
                case Config.TEXTSIZE_HEADLINE:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_headline);
                    break;
                case Config.TEXTSIZE_DISPLAY1:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display1);
                    break;
                case Config.TEXTSIZE_DISPLAY2:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display2);
                    break;
                case Config.TEXTSIZE_DISPLAY3:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display3);
                    break;
                case Config.TEXTSIZE_DISPLAY4:
                    size = getResources().getDimensionPixelSize(R.dimen.ate_default_textsize_display4);
                    break;
            }
            mPreview.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            final int dpValue = pxToSp(this, size);
            mValue.setText(String.format("%dsp", dpValue));
            mSeeker.setProgress(dpValue - 1);
        } else {
            dismiss();
        }
    }
}