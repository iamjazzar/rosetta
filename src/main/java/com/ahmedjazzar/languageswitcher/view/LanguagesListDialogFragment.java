package com.ahmedjazzar.languageswitcher.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.ahmedjazzar.languageswitcher.LocalesUtils;
import com.ahmedjazzar.languageswitcher.Logger;
import com.ahmedjazzar.languageswitcher.R;

import java.util.ArrayList;

/**
 * Created by ahmedjazzar on 1/19/16.
 */

public class LanguagesListDialogFragment extends DialogFragment {

    private final String LANGUAGES_TAG = "LANGUAGES";
    private final String TAG = LanguagesListDialogFragment.class.getName();
    private int mSelectedLanguage = -1;
    private Logger mLogger;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final ArrayList<String>
                languages = getArguments().getStringArrayList(LANGUAGES_TAG);

        mLogger.debug("Building DialogFragment.");

        builder.setTitle(getString(R.string.language_switcher_dialog_title))
                .setSingleChoiceItems(
                        languages.toArray(new String[languages.size()]),
                        LocalesUtils.getCurrentLocaleIndex(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO: Display Dialog title in the selected locale
                                // TODO: Display positive button in the selected locale
                                // TODO: Display negative button in the selected locale
                                // update the selected locale
                                mSelectedLanguage = which;
                            }
                        })
                .setPositiveButton(
                        getString(R.string.language_switcher_positive_button).toUpperCase(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (mSelectedLanguage != -1 &&
                                        mSelectedLanguage != LocalesUtils.getCurrentLocaleIndex()) {

                                    if (LocalesUtils.setAppLocale(
                                            getActivity().getBaseContext(), mSelectedLanguage)) {

                                        mLogger.info("App locale changed successfully.");
                                        refreshApplication();
                                    } else {
                                        mLogger.error("Unsuccessful trial to change the App locale.");
                                        // TODO: notify the user
                                    }
                                } else  {
                                    dialog.dismiss();
                                }
                            }
                        })
                .setNegativeButton(
                        getString(R.string.language_switcher_negative_button).toUpperCase(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mLogger.verbose("User discarded changing language.");
                            }
                        });

        mLogger.verbose("DialogFragment built.");
        return builder.create();
    }

    private void refreshApplication() {

        Intent refresh = new Intent(getActivity(), getActivity()
                .getClass());
        startActivity(refresh);
        getActivity().finish();
    }

    public LanguagesListDialogFragment newInstance(ArrayList<String> languages)    {
        LanguagesListDialogFragment fragment = new LanguagesListDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putStringArrayList(LANGUAGES_TAG, languages);
        fragment.setArguments(bundle);

        return fragment;
    }

    public LanguagesListDialogFragment()  {
        this.mLogger = new Logger(TAG);
    }
}