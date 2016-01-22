package com.ahmedjazzar.languageswitcher;

import android.util.Log;

/**
 * Created by ahmedjazzar on 1/16/16.
 */

public class Logger {

    private String mTag;

    public Logger(Class<?> cls) {
        this.mTag = cls.getName();
    }

    public Logger(String mTag) {
        this.mTag = mTag;
    }

    public void error(String log) {
        Log.e(this.mTag, log);
    }

    public void warn(String log) {
        Log.w(this.mTag, log);
    }

    public void debug(String log) {
        Log.d(this.mTag, log);
    }

    public void info(String log) {
        Log.i(this.mTag, log);
    }

    public void verbose(String log) {
        Log.v(this.mTag, log);
    }
}
