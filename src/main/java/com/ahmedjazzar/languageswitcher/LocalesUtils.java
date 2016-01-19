package com.ahmedjazzar.languageswitcher;

import android.support.annotation.NonNull;

import java.util.HashSet;

/**
 * Created by ahmedjazzar on 1/19/16.
 */
public final class LocalesUtils {

    @NonNull
    private static LocalesDetector detector;

    private static HashSet<String> locales;
    private static final String TAG = LocalesDetector.class.getName();
    private static Logger logger = new Logger(TAG);

    /**
     *
     * @param detector just a setter because I don't want to declare any constructors in this class
     */
    public static void setDetector(LocalesDetector detector) throws RuntimeException {
        LocalesUtils.detector = detector;
    }

    /**
     *
     * @param stringId a string to start discovering locales in
     * @return a HashSet of discovered locales
     */
    public static HashSet<String> fetchAppAvailableLocales(int stringId)  {
        return detector.fetchAppAvailableLocales(stringId);
    }

    /**
     *
     * @param localesSet locales  user wanna use
     */
    public static void setLocales(HashSet<String> localesSet)    {
        LocalesUtils.locales = detector.validateLocales(localesSet);
        logger.debug("Locales have been changed");
    }

    /**
     *
     * @return a HashSet of the available locales discovered in the application
     */
    public static HashSet<String> getLocales()    {
        return LocalesUtils.locales;
    }

    /**
     *
     * @return a char sequence of the available locales discovered in the application
     */
    public static CharSequence[] getCharSequenceLocales() {
        return LocalesUtils.locales.toArray(
                new CharSequence[LocalesUtils.locales.size()]
        );
    }
}
