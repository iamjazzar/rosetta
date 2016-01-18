package com.ahmedjazzar.languageswitcher;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by ahmedjazzar on 1/16/16.
 */
class LocalesDetector {

    private LanguageSwitcher languageSwitcher;
    private HashSet<String> appAvailableLocales;
    private Logger logger;
    private final String TAG = LocalesDetector.class.getName();

    public LocalesDetector(LanguageSwitcher ls)    {
        logger = new Logger(TAG);
        languageSwitcher = ls;
        appAvailableLocales = new HashSet<>();
        logger.verbose("Object from " + TAG + "Created");
    }

    /**
     *
     * @param locales user pre-defined locales that wanna use without fetching the locales
     */
    public void setAppAvailableLocales(HashSet locales) {
        appAvailableLocales = validateLocales(locales);
    }

    /**
     * this method takes an experimental string id to see if it's exists in other available
     * locales inside the app than default locale.
     * NOTE: Even if you have a folder named values-ar it doesn't mean you have any resources
     *      there
     *
     * @param stringId experimental string id to discover locales
     * @param saveResults if true will going to update the locales set with the discovered one
     * @return the discovered locales
     */
    HashSet<String>  fetchAppAvailableLocales(int stringId, boolean saveResults) {

        HashSet<String> localesSet = new HashSet<>();
        Context context = languageSwitcher.getContext();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        AssetManager assetManager = context.getAssets();
        String[] locales = res.getAssets().getLocales();

        // Add default locale to the set
        localesSet.add(Locale.getDefault().getLanguage());

        for(String locale:locales) {
            logger.debug("testing locale availability: " + locale);

            configuration.locale = new Locale(locale);
            Resources tempResource1 = new Resources(assetManager, metrics, configuration);
            String base = tempResource1.getString(stringId);

            configuration.locale = new Locale("");
            Resources tempResource2 = new Resources(assetManager, metrics, configuration);
            String target = tempResource2.getString(stringId);

            logger.debug("Checking strings: '" + base + "' and '" + target + "equality.");
            if (!base.equals(target)) {
                localesSet.add(locale);
                logger.info("Locale: '" + locale + "' found");
            } else  {
                logger.debug("Strings: '" + base + "' and '" + target + "have the same locale.");
            }
        }

        if (saveResults)    {
            appAvailableLocales = localesSet;
        }

        return localesSet;
    }

    /**
     *
     * @return the available locales discovered in the application
     */
    public HashSet<String> getAppAvailableLocales() {
        return appAvailableLocales;
    }

    /**
     * This method validate locales by checking if they are available of they contain wrong letter
     * case and adding the valid ones in a clean set.
     *
     * @param locales to be checked
     * @return valid locales
     */
    public HashSet<String> validateLocales(HashSet<String> locales)   {

        logger.debug("Validating given locales..");

        HashSet<String> cleanLocales = new HashSet<>();
        String[] androidLocales = languageSwitcher.getContext().getAssets().getLocales();
        for (String locale: locales) {
            if (Arrays.asList(androidLocales).contains(locale.toLowerCase())) {
                cleanLocales.add(locale.toLowerCase());
            } else {
                logger.error("Invalid passed locale: " + locale);
                logger.warn("Invalid specified locale: '" + locale + "', has been discarded");
            }
        }
        logger.debug("passing validated locales.");
        return cleanLocales;
    }
}
