package com.ahmedjazzar.languageswitcher;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.ahmedjazzar.languageswitcher.view.LanguagesListDialogFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by ahmedjazzar on 1/16/16.
 */
public class LanguageSwitcher {

    private Context mContext;
    private Logger mLogger;
    private final String TAG = LanguageSwitcher.class.getName();

    public LanguageSwitcher(Context context) {
        this.mContext = context;
        this.mLogger = new Logger(TAG);

        LocalesUtils.setLocalesPreferenceManager(new LocalesPreferenceManager(context));
        LocalesUtils.setDetector(new LocalesDetector(this.mContext));
        mLogger.verbose("Object from " + TAG + " has been created.");
    }

    public void show()  {
        ArrayList<String> languages = LocalesUtils.getLocalesWithDisplayName();
        new LanguagesListDialogFragment()
                .newInstance(languages)
                .show(((FragmentActivity) mContext).getSupportFragmentManager(), TAG);
    }

    public HashSet<Locale> getLocales()   {
        return LocalesUtils.getLocales();
    }

    public void setLocales(HashSet<Locale> locales)    {
        LocalesUtils.setLocales(locales);
    }

    public HashSet<Locale> fetchLocales(int stringId) {
        return LocalesUtils.fetchAppAvailableLocales(stringId);
    }
}
