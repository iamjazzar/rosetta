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

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        AutomatedSupportedLocales();
        manualSupportedLocales();

        LanguageSwitcher ls = new LanguageSwitcher(this, firstLaunchLocale);
        ls.setSupportedLocales(supportedLocales);
    }

    private void AutomatedSupportedLocales() {

    }

    private void manualSupportedLocales() {
        // This is the locale that you wanna your app to launch with.
        firstLaunchLocale = new Locale("ar");

        // You can use a HashSet<String> instead and call 'setSupportedStringLocales()'
        // and it'll work perfectly :)
        supportedLocales = new HashSet<>();
        supportedLocales.add(Locale.US);
        supportedLocales.add(Locale.CHINA);
        supportedLocales.add(new Locale("ar"));
    }
}
