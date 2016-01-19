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

    private Context mContext;
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
     * @param stringId experimental string id to discover locales
     * @return the discovered locales
     */
    HashSet<String>  fetchAppAvailableLocales(int stringId) {

        HashSet<String> localesSet = new HashSet<>();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        Resources res = mContext.getResources();
        Configuration configuration = res.getConfiguration();
        AssetManager assetManager = mContext.getAssets();
        String[] locales = res.getAssets().getLocales();

        // Add default locale to the set
        localesSet.add(Locale.getDefault().getLanguage());

        for(String locale:locales) {
            mLogger.debug("testing locale availability: " + locale);

            configuration.locale = new Locale(locale);
            Resources tempResource1 = new Resources(assetManager, metrics, configuration);
            String base = tempResource1.getString(stringId);

            configuration.locale = new Locale("");
            Resources tempResource2 = new Resources(assetManager, metrics, configuration);
            String target = tempResource2.getString(stringId);

            mLogger.debug("Checking strings: '" + base + "' and '" + target + "' equality.");
            if (!base.equals(target)) {
                localesSet.add(locale);
                mLogger.info("Locale: '" + locale + "' found");
            } else  {
                mLogger.debug("Strings: '" + base + "' and '" + target + "' have the same locale.");
            }
        }
        return validateLocales(localesSet);
    }

    /**
     * This method validate locales by checking if they are available of they contain wrong letter
     * case and adding the valid ones in a clean set.
     * @param locales to be checked
     * @return valid locales
     */
    HashSet<String> validateLocales(HashSet<String> locales)   {

        mLogger.debug("Validating given locales..");

        HashSet<String> cleanLocales = new HashSet<>();
        String[] androidLocales = mContext.getAssets().getLocales();
        for (String locale: locales) {
            if (Arrays.asList(androidLocales).contains(locale.toLowerCase())) {
                cleanLocales.add(locale.toLowerCase());
            } else {
                mLogger.error("Invalid passed locale: " + locale);
                mLogger.warn("Invalid specified locale: '" + locale + "', has been discarded");
            }
        }
        mLogger.debug("passing validated locales.");
        return cleanLocales;
    }
}
