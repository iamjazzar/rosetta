package com.ahmedjazzar.rosetta.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedjazzar.rosetta.ContextWrapper;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        Locale locale = new Locale(mSharedPreferences.getString("user_preferred_language", "en"));
        Context context = ContextWrapper.wrap(newBase, locale);
        super.attachBaseContext(context);
    }
}
