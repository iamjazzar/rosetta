package com.ahmedjazzar.languageswitcher.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by ahmedjazzar on 1/19/16.
 */

public class LanguagesListDialogFragment extends DialogFragment {

    private final String LOCALES_TAG = "LOCALES";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] languages = getArguments().getCharSequenceArray(LOCALES_TAG);

        builder.setTitle("Wow!")
                .setSingleChoiceItems(
                        languages,
                        0,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    public LanguagesListDialogFragment newInstance(CharSequence[] locales)    {
        LanguagesListDialogFragment fragment = new LanguagesListDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putCharSequenceArray(LOCALES_TAG, locales);
        fragment.setArguments(bundle);

        return fragment;
    }
    public LanguagesListDialogFragment()  {}
}
