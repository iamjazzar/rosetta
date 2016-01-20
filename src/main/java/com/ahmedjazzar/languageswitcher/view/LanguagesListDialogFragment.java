package com.ahmedjazzar.languageswitcher.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.ahmedjazzar.languageswitcher.LocalesUtils;
import com.ahmedjazzar.languageswitcher.Logger;

/**
 * Created by ahmedjazzar on 1/19/16.
 */

public class LanguagesListDialogFragment extends DialogFragment {

    private final String LOCALES_TAG = "LOCALES";
    private final String TAG = LanguagesListDialogFragment.class.getName();
    private Logger mLogger;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] languages = getArguments().getCharSequenceArray(LOCALES_TAG);

        mLogger.debug("Building DialogFragment.");

        builder.setTitle("Language")
                .setSingleChoiceItems(
                        languages,
                        LocalesUtils.getCurrentLocaleIndex(),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton(
                        "OK", //TODO: fetch from strings.xml
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                })
                .setNegativeButton(
                        "Cancel", //TODO: fetch from strings.xml
                        new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        mLogger.verbose("DialogFragment built.");
        return builder.create();
    }

    public LanguagesListDialogFragment newInstance(CharSequence[] locales)    {
        LanguagesListDialogFragment fragment = new LanguagesListDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putCharSequenceArray(LOCALES_TAG, locales);
        fragment.setArguments(bundle);

        return fragment;
    }

    public LanguagesListDialogFragment()  {
        this.mLogger = new Logger(TAG);
    }
}
