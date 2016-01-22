package com.ahmedjazzar.languageswitcher;

import android.app.backup.BackupManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

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

    private static LocalesDetector sDetector;
    private static LocalesPreferenceManager sLocalesPreferenceManager;
    private static HashSet<Locale> sLocales;
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
    public static void setDetector(@NonNull LocalesDetector detector) {
        LocalesUtils.sDetector = detector;
    }

    public static void setLocalesPreferenceManager(
            @NonNull LocalesPreferenceManager localesPreferenceManager)   {

        LocalesUtils.sLocalesPreferenceManager = localesPreferenceManager;
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
        sLogger.debug("Locales have been changed");
    }

    /**
     *
     * @return a HashSet of the available sLocales discovered in the application
     */
    public static HashSet<Locale> getLocales()    {
        return LocalesUtils.sLocales;
    }

    public static ArrayList<String> getLocalesWithDisplayName()   {
        ArrayList<String> stringLocales = new ArrayList<>();

        for (Locale loc: LocalesUtils.getLocales()) {
            stringLocales.add(loc.getDisplayName(loc));
        }
        return stringLocales;
    }

    /**
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
            sLogger.warn("Current device locale '" + locale.toString() +
                    "' does not appear in your given supported locales");

            index = sDetector.detectMostClosestLocale(locale);
            if(index == -1)   {
                index = 0;
                sLogger.warn("Current locale index changed to 0 as the current locale '" +
                                locale.toString() +
                                "' not supported."
                );
            }
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

    /**
     *
     * @param context
     * @param index the selected locale position
     * @return true if the application locale changed
     */
    public static boolean setAppLocale(Context context, int index)    {

        Locale newLocale = LocalesUtils.sLocales.toArray(new Locale[LocalesUtils.sLocales.size()])[index];

        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();

        Locale oldLocale = new Locale(configuration.locale.getLanguage(), configuration.locale.getCountry());
        configuration.locale = newLocale;
        resources.updateConfiguration(configuration, displayMetrics);

        if(oldLocale.equals(newLocale)) {
            return false;
        }

        if (LocalesUtils.updateLocalePreferences(newLocale))    {
            sLogger.info("Locale preferences updated to: " +
                    LocalesUtils.sLocalesPreferenceManager.getPreferredLocale());
        } else  {
            sLogger.error("Faild to update locale preferences.");
        }

        return true;
    }

    private static boolean updateLocalePreferences(Locale locale)    {

        return LocalesUtils.sLocalesPreferenceManager
                .setPreferredLocale(locale.getLanguage(), locale.getCountry());
    }
    private static Locale getCurrentLocale() {
        return sDetector.getCurrentLocale();
    }
}