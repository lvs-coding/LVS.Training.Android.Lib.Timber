package com.example.timbertraining;


import android.util.Log;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {

    private static final int MAX_LOG_LENGTH = 4000;

    @Override
    protected boolean isLoggable(int priority) {
        if(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return false;
        }

        // Only log WARN, ERROR, WTF
        return true;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (isLoggable(priority)) {

            // Report caught exception to CrashLytics (or whathever crash library you use)
            if(priority == Log.ERROR && t != null) {
                // Crashlytics.log(e)
            }

            // Message is short enough, does not need to be broken into chunks
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag,message);
                } else {
                    Log.println(priority, tag, message);
                }

                return;
            }

            // Split by line, then ensure each line can fit into log's maximum length.
            for(int i = 0, length = message.length() ; i < length; i++) {
                int newLine = message.indexOf('\n', i);
                newLine = newLine != -1 ? newLine : length;
                do {
                    int end = Math.min(newLine, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newLine);
            }
        }


    }
}
