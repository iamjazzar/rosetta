package com.ahmedjazzar.languageswitcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * This fragment is responsible for displaying the supported locales and performing any necessary
 * action that allows user to select, cancel, and commit changes.
 *
 * Created by ahmedjazzar on 1/19/16.
 */

public class LanguagesListDialogFragment extends DialogFragment {

    private final String LANGUAGES_TAG = "LANGUAGES";
    private final String TAG = LanguagesListDialogFragment.class.getName();
    private final int DIALOG_TITLE_ID = R.string.language_switcher_dialog_title;
    private final int DIALOG_POSITIVE_ID = R.string.language_switcher_positive_button;
    private final int DIALOG_NEGATIVE_ID = R.string.language_switcher_negative_button;

    private int mSelectedLanguage = -1;
    private Logger mLogger;

    public LanguagesListDialogFragment()  {
        this.mLogger = new Logger(TAG);
    }

    /**
     *
     * @param savedInstanceState
     * @return a Dialog fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final ArrayList<String> languages = getArguments().getStringArrayList(LANGUAGES_TAG);

        mLogger.debug("Building DialogFragment.");

        builder.setTitle(getString(DIALOG_TITLE_ID))
                .setSingleChoiceItems(
                        languages.toArray(new String[languages.size()]),
                        LocalesUtils.getCurrentLocaleIndex(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                // update the selected locale
                                mSelectedLanguage = which;
                                Locale locale = LocalesUtils.getLocaleFromIndex(mSelectedLanguage);
                                AlertDialog dialog = (AlertDialog) getDialog();
                                FragmentActivity activity = getActivity();

                                mLogger.debug("Displaying dialog main strings in the selected " +
                                        "locale");

                                // Display dialog title in the selected locale
                                dialog.setTitle(LocalesUtils.getInSpecificLocale(
                                        activity, locale, DIALOG_TITLE_ID));

                                // Display positive button text in the selected locale
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setText(LocalesUtils.getInSpecificLocale(
                                                activity, locale, DIALOG_POSITIVE_ID));

                                // Display negative button text in the selected locale
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                        .setText(LocalesUtils.getInSpecificLocale(
                                                activity, locale, DIALOG_NEGATIVE_ID));
                            }
                        })
                .setPositiveButton(
                        getString(DIALOG_POSITIVE_ID).toUpperCase(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {

                                // if the user did not select the same locale go ahead, else ignore
                                if (mSelectedLanguage != -1 &&
                                        mSelectedLanguage != LocalesUtils.getCurrentLocaleIndex()) {

                                    // Try changing the locale
                                    if (LocalesUtils.setAppLocale(
                                            getActivity(), mSelectedLanguage)) {

                                        mLogger.info("App locale changed successfully.");
                                        refreshApplication();
                                    } else {
                                        mLogger.error("Unsuccessful trial to change the App locale.");
                                        // TODO: notify the user that his request not placed
                                    }
                                } else  {
                                    dialogInterface.dismiss();
                                }
                            }
                        })
                .setNegativeButton(
                        getString(DIALOG_NEGATIVE_ID).toUpperCase(),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {
                                mLogger.verbose("User discarded changing language.");
                            }
                        });

        mLogger.verbose("DialogFragment built.");
        return builder.create();
    }

    /**
     *
     * @param languages available application languages
     * @return a dialog fragment with the available languages
     */
    LanguagesListDialogFragment newInstance(ArrayList<String> languages)    {
        LanguagesListDialogFragment fragment = new LanguagesListDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putStringArrayList(LANGUAGES_TAG, languages);
        fragment.setArguments(bundle);
        mLogger.verbose("languages passed to the dialog fragment");

        return fragment;
    }

    /**
     * Refreshing the application so no weired results occurred after changing the locale.
     */
    private void refreshApplication() {

        Intent app = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        app.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent current = new Intent(getActivity(), getActivity().getClass());
        mLogger.debug("Refreshing the application: " +
                getActivity().getBaseContext().getPackageName());

        mLogger.debug("Finishing current activity.");
        getActivity().finish();

        mLogger.debug("Start the application");
        startActivity(app);
        startActivity(current);

        mLogger.debug("Application refreshed");
    }
}