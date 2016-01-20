package com.ahmedjazzar.languageswitcher;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by ahmedjazzar on 1/16/16.
 */
class LocalesDetector {

    private final Context mContext;
    private Logger mLogger;
    private final String TAG = LocalesDetector.class.getName();

    public LocalesDetector(Context context)    {
        this.mLogger = new Logger(TAG);
        this.mContext = context;

        mLogger.verbose("Object from " + TAG + "Created");
    }

    /**
     * this method takes an experimental string id to see if it's exists in other available
     * locales inside the app than default locale.
     * NOTE: Even if you have a folder named values-ar it doesn't mean you have any resources
     *      there
     *
     * TODO: consider overloading this to take a base locale argument
     *
     * @param stringId experimental string id to discover locales
     * @return the discovered locales
     */
    HashSet<Locale> fetchAppAvailableLocales(int stringId) {

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        Configuration conf = mContext.getResources().getConfiguration();
        Locale originalLocale = conf.locale;
        Locale baseLocale = Locale.US;
        conf.locale = baseLocale;

        ArrayList<String> references = new ArrayList<>();
        references.add(new Resources(mContext.getAssets(), dm, conf).getString(stringId));

        HashSet<Locale> result = new HashSet<>();
        result.add(baseLocale);

        for(String loc : mContext.getAssets().getLocales()) {
            if(loc.isEmpty()){
                continue;
            }

            Locale l;
            boolean referencesUpdateLock = false;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)  {
                l = Locale.forLanguageTag(loc);
            } else {
                l = new Locale(loc.substring(0, 2));
            }

            conf.locale = l;

            //TODO: put it in a method
            String tmpString = new Resources(mContext.getAssets(), dm, conf).getString(stringId);
            for (String reference: references)  {
                if(reference.equals(tmpString)){
                    referencesUpdateLock = true;
                    break;
                }
            }

            if(!referencesUpdateLock)   {
                result.add(l);
                references.add(tmpString);
            }
        }

        conf.locale = originalLocale; // to restore our guy initial state
        return result;
    }

    /**
     * TODO: return the selected one
     * @return
     */
    Locale getCurrentLocale()   {
        return mContext.getResources().getConfiguration().locale;
    }

    /**
     * This method validate locales by checking if they are available of they contain wrong letter
     * case and adding the valid ones in a clean set.
     * @param locales to be checked
     * @return valid locales
     */
    HashSet<Locale> validateLocales(HashSet<Locale> locales)   {

        mLogger.debug("Validating given locales..");

        for (Locale l:LocalesUtils.getPseudoLocales()) {
            if(locales.remove(l)) {
                mLogger.info("Pseudo locale '" + l + "' has been removed.");
            }
        }

        HashSet<Locale> cleanLocales = new HashSet<>();
        Locale[] androidLocales = Locale.getAvailableLocales();
        for (Locale locale: locales) {
            if (Arrays.asList(androidLocales).contains(locale)) {
                cleanLocales.add(locale);
            } else {
                mLogger.error("Invalid passed locale: " + locale);
                mLogger.warn("Invalid specified locale: '" + locale + "', has been discarded");
            }
        }
        mLogger.debug("passing validated locales.");
        return cleanLocales;
    }
}
