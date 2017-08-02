package com.example.timbertraining;


import android.app.Application;

import timber.log.Timber;

public class TimberDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
               // Add the line number to the tag.

                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        } else {
            // Release mode
            // Crashlytics.start(); // init your crash reporting lib
            Timber.plant(new ReleaseTree());
        }


    }
}
