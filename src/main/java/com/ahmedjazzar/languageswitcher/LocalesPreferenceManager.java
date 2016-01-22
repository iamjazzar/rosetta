package com.ahmedjazzar.languageswitcher;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by ahmedjazzar on /22/116.
 */
public class LocalesPreferenceManager  {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private final Locale DEFAULT_LOCALE;
    private final String PREFERRED_LANGUAGE_KEY = "preferred_language";
    private final String PREFERRED_COUNTRY_KEY = "preferred_country";

    public LocalesPreferenceManager(Context context)   {
        this(context, Locale.US);
    }

    public LocalesPreferenceManager(Context context, Locale defaultLocale)   {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEditor = this.mSharedPreferences.edit();
        this.DEFAULT_LOCALE = defaultLocale;
    }

    public boolean setPreferredLocale(String language, String country)   {
        mEditor.putString(this.PREFERRED_LANGUAGE_KEY, language);
        mEditor.putString(this.PREFERRED_COUNTRY_KEY, country);

        return mEditor.commit();
    }

    public Locale getPreferredLocale()    {
        return new Locale(getPreferredLanguage(), getPreferredCountry());
    }

    private String getPreferredLanguage()  {
        return mSharedPreferences.getString(PREFERRED_LANGUAGE_KEY, DEFAULT_LOCALE.getLanguage());
    }

    private String getPreferredCountry()  {
        return mSharedPreferences.getString(PREFERRED_COUNTRY_KEY, DEFAULT_LOCALE.getCountry());
    }
}
