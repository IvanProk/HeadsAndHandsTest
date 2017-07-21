package com.ivan.prokofyev.handh_test.injection.component;

import android.app.Application;
import android.content.Context;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.data.SyncService;
import com.ivan.prokofyev.handh_test.data.local.DatabaseHelper;
import com.ivan.prokofyev.handh_test.data.local.PreferencesHelper;
import com.ivan.prokofyev.handh_test.data.remote.ApiService;
import com.ivan.prokofyev.handh_test.data.remote.FacebookAuthService;
import com.ivan.prokofyev.handh_test.injection.ApplicationContext;
import com.ivan.prokofyev.handh_test.injection.module.ApplicationModule;
import com.ivan.prokofyev.handh_test.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    ApiService apiService();
    FacebookAuthService fbAuthService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
