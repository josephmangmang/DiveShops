package com.divetym.dive;

import android.app.Application;

import com.divetym.dive.common.SessionManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by kali_root on 4/5/2017.
 */

public class DiveTymApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        SessionManager.getInstance(this);
    }
}
