package com.ahmedjazzar.rosetta.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ahmedjazzar.rosetta.LanguageSwitcher;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // This floating button switching between Arabic and English Locales manually upon click
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        if (MainApplication.languageSwitcher.getCurrentLocale().getLanguage().equals("ar"))   {
            fab.hide();
        } else {
            fab.show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainApplication.languageSwitcher.switchToLaunch(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MainApplication.languageSwitcher.showChangeLanguageDialog(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
