package com.ivan.prokofyev.handh_test;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.stetho.Stetho;
import com.ivan.prokofyev.handh_test.injection.component.ApplicationComponent;
import com.ivan.prokofyev.handh_test.injection.component.DaggerApplicationComponent;
import com.ivan.prokofyev.handh_test.injection.module.ApplicationModule;

import timber.log.Timber;

public class HTApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//            Fabric.with(this, new Crashlytics());
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .build());
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setIsDebugEnabled(true);
    }

    public static HTApplication get(Context context) {
        return (HTApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
