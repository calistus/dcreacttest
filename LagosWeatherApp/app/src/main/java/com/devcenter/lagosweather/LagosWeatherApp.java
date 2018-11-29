package com.devcenter.lagosweather;

import android.app.Application;

/**
 * @author Ilo Calistus
 */


public class LagosWeatherApp extends Application {


    private static LagosWeatherApp _INSTANCE;

    public static LagosWeatherApp getInstance() {
        return _INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _INSTANCE = this;
    }
}