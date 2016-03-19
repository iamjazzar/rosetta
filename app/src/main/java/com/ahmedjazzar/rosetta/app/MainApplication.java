package com.ahmedjazzar.rosetta.app;

import android.app.Application;

import com.ahmedjazzar.rosetta.LanguageSwitcher;

import java.util.HashSet;
import java.util.Locale;

/**
 * Created by ahmedjazzar on /29/216.
 */
public class MainApplication extends Application {

    private Locale firstLaunchLocale;
    private HashSet<Locale> supportedLocales;
    public static LanguageSwitcher languageSwitcher;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        AutomatedSupportedLocales();
        manualSupportedLocales();

        languageSwitcher = new LanguageSwitcher(this, firstLaunchLocale);
        languageSwitcher.setSupportedLocales(supportedLocales);
    }

    private void AutomatedSupportedLocales() {

    }

    private void manualSupportedLocales() {
        // This is the locale that you wanna your app to launch with.
        firstLaunchLocale = new Locale("ar");

        // You can use a HashSet<String> instead and call 'setSupportedStringLocales()' :)
        supportedLocales = new HashSet<>();
        supportedLocales.add(Locale.US);
        supportedLocales.add(Locale.CHINA);
        supportedLocales.add(firstLaunchLocale);
    }
}
