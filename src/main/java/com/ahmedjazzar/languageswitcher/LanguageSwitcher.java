package com.ahmedjazzar.languageswitcher;

import android.content.Context;

import java.util.HashSet;

/**
 * Created by ahmedjazzar on 1/16/16.
 */
public class LanguageSwitcher {

    private Context context;
    private LocalesDetector detector;
    private Logger logger;
    private final String TAG = LanguageSwitcher.class.getName();

    public LanguageSwitcher(Context c) {
        context = c;
        detector = new LocalesDetector(this);
        logger = new Logger(TAG);
        logger.verbose("Object from " + TAG + " has been created.");
    }

    public HashSet<String> fetchAppAvailableLocales(int stringId)  {
        return detector.fetchAppAvailableLocales(stringId, false);
    }

    public HashSet<String> fetchAppAvailableLocales(int stringId, boolean saveResults)  {
        return detector.fetchAppAvailableLocales(stringId, saveResults);
    }

    public void setAppAvailableLocales(HashSet<String> localesSet)    {
        detector.setAppAvailableLocales(localesSet);
    }

    public HashSet<String> getLocales()    {
        return detector.getAppAvailableLocales();
    }

    Context getContext() {
        return context;
    }
}
