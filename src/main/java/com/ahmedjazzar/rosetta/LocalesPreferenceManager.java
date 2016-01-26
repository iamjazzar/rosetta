package com.ahmedjazzar.rosetta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * This class is responsible for setting and getting the preferred locale and manage any related
 * actions. I think that there's no need for logging here because the utils class already handles
 * logs for these actions based on their returned results.
 *
 * Created by ahmedjazzar on 1/22/16.
 */
class LocalesPreferenceManager  {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private final Locale BASE_LOCALE;
    private final String PREFERRED_LANGUAGE_KEY = "preferred_language";
    private final String PREFERRED_COUNTRY_KEY = "preferred_country";

    LocalesPreferenceManager(Context context, Locale firstLaunchLocale, Locale baseLocale)   {

        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = this.mSharedPreferences.edit();
        this.BASE_LOCALE = baseLocale;

        // these lines detect for a previous preferred locale and prevent overriding it
        if (getPreferredLocale() == null)    {
            this.setPreferredLocale(firstLaunchLocale);
        }
    }

    /**
     * Sets user preferred locale
     *
     * @param locale user desired locale
     * @return true if the preference updated
     */
    boolean setPreferredLocale(Locale locale)   {
        return this.setPreferredLocale(locale.getLanguage(), locale.getCountry());
    }

    /**
     *
     * @return preferred locale after concatenating language and country
     */
    Locale getPreferredLocale()    {
        return new Locale(getPreferredLanguage(), getPreferredCountry());
    }

    /**
     *
     * @return base locale. (The one that used in main xml string file)
     */
    Locale getBaseLocale()   {
        return this.BASE_LOCALE;
    }

    /**
     * Sets user preferred locale by setting a language preference and a country preference since
     * there's no supported preferences for locales
     * @param language of the locale; ex. en
     * @param country of the locale; ex. US
     * @return true if the preferences updated
     */
    private boolean setPreferredLocale(String language, String country)   {
        mEditor.putString(this.PREFERRED_LANGUAGE_KEY, language);
        mEditor.putString(this.PREFERRED_COUNTRY_KEY, country);

        return mEditor.commit();
    }

    /**
     *
     * @return preferred language
     */
    private String getPreferredLanguage()  {
        return mSharedPreferences.getString(PREFERRED_LANGUAGE_KEY, BASE_LOCALE.getLanguage());
    }

    /**
     *
     * @return preferred country
     */
    private String getPreferredCountry()  {
        return mSharedPreferences.getString(PREFERRED_COUNTRY_KEY, BASE_LOCALE.getCountry());
    }
}
