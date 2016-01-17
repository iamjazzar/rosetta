package com.ahmedjazzar.languageswitcher;

import android.util.Log;

/**
 * Created by ahmedjazzar on 1/16/16.
 */

public class Logger {

    private String tag;

    public Logger(Class<?> cls) {
        this.tag = cls.getName();
    }

    public Logger(String tag) {
        this.tag = tag;
    }

    public void error(String log) {
        Log.e(this.tag, log);
    }

    public void warn(String log) {
        Log.w(this.tag, log);
    }

    public void debug(String log) {
        Log.d(this.tag, log);
    }

    public void info(String log) {
        Log.i(this.tag, log);
    }

    public void verbose(String log) {
        Log.v(this.tag, log);
    }
}
