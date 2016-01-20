package com.ahmedjazzar.languageswitcher;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by ahmedjazzar on 1/19/16.
 */
public final class LocalesUtils {

    @NonNull
    private static LocalesDetector sDetector;

    private static HashSet<Locale> sLocales;
    private static CharSequence[] sLanguages;
    private static final Locale[] PSEUDO_LOCALES = {
            new Locale("en", "XA"),
            new Locale("ar", "XB")
    };
    private static final String TAG = LocalesDetector.class.getName();
    private static Logger sLogger = new Logger(TAG);

    /**
     *
     * @param detector just a setter because I don't want to declare any constructors in this class
     */
    public static void setDetector(LocalesDetector detector) {
        LocalesUtils.sDetector = detector;
    }

    /**
     *
     * @param stringId a string to start discovering sLocales in
     * @return a HashSet of discovered sLocales
     */
    public static HashSet<Locale> fetchAppAvailableLocales(int stringId)  {
        return sDetector.fetchAppAvailableLocales(stringId);
    }

    /**
     *
     * @param localesSet sLocales  user wanna use
     */
    public static void setLocales(HashSet<Locale> localesSet)    {
        LocalesUtils.sLocales = sDetector.validateLocales(localesSet);

        sLanguages = new CharSequence[sLocales.size()];
        Iterator itr = sLocales.iterator();

        int i = 0;
        while (itr.hasNext())  {
            Locale locale = (Locale) itr.next();
            sLanguages[i] = locale.getDisplayName(locale);
            i++;
        }

        sLogger.debug("Locales have been changed");
    }

    /**
     *
     * @return a HashSet of the available sLocales discovered in the application
     */
    public static HashSet<Locale> getLocales()    {
        return LocalesUtils.sLocales;
    }

    /**
     *
     * @return a char sequence of the available sLocales discovered in the application
     */
    public static CharSequence[] getLanguages() {
        return LocalesUtils.sLanguages;
    }

    /**
     * NOTE: BETA and buggy
     *
     * @return the index of the current app locale
     */
    public static int getCurrentLocaleIndex()    {
        Locale locale = getCurrentLocale();
        int index = -1;
        int itr = 0;

        for (Locale l : sLocales)  {
            if(locale.equals(l))    {
                index = itr;
                break;
            }
            itr++;
        }

        if (index == -1)    {
            //TODO: change the index to the most closer available locale
            index = 0;
            sLogger.warn("Current device locale '" + locale.toString() +
                    "' does not appear in your given supported locales");
            sLogger.warn("Current locale index changed to 0 as the current locale '" +
                            locale.toString() +
                            "' not supported"
            );
        }
        return index;
    }

    /**
     *
     * @see <a href="http://en.wikipedia.org/wiki/Pseudolocalization">Pseudolocalization</a> for
     *      more information about pseudo localization
     * @return a list of pseudo locales
     */
    public static List<Locale> getPseudoLocales()  {
        return Arrays.asList(LocalesUtils.PSEUDO_LOCALES);
    }

    private static Locale getCurrentLocale() {
        return sDetector.getCurrentLocale();
    }
}