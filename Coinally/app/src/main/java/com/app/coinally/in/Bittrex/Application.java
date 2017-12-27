package com.app.coinally.in.Bittrex;


import com.app.coinally.in.Bittrex.settings.ApplicationSettings;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationSettings.initialize(this);
    }
}
