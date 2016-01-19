package com.ahmedjazzar.languageswitcher;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.ahmedjazzar.languageswitcher.view.LanguagesListDialogFragment;

import java.util.HashSet;

/**
 * Created by ahmedjazzar on 1/16/16.
 */
public class LanguageSwitcher {

    private Context mContext;
    private Logger mLogger;
    private final String TAG = LanguageSwitcher.class.getName();

    public LanguageSwitcher(Context context) {
        this.mContext = context;
        this.mLogger = new Logger(TAG);

        LocalesUtils.setDetector(new LocalesDetector(this.mContext));
        mLogger.verbose("Object from " + TAG + " has been created.");
    }

    public void show()  {
        CharSequence[] locales = LocalesUtils.getCharSequenceLocales();
        new LanguagesListDialogFragment()
                .newInstance(locales)
                .show(((FragmentActivity) mContext).getSupportFragmentManager(), TAG);
    }

    public HashSet<String> getLocales()   {
        return LocalesUtils.getLocales();
    }

    public void setLocales(HashSet<String> locales)    {
        LocalesUtils.setLocales(locales);
    }

    public HashSet<String> fetchLocales(int stringId) {
        return LocalesUtils.fetchAppAvailableLocales(stringId);
    }
}
