package com.ivan.prokofyev.handh_test.injection.module;

import android.app.Application;
import android.content.Context;

import com.ivan.prokofyev.handh_test.data.remote.ApiService;
import com.ivan.prokofyev.handh_test.data.remote.FacebookAuthService;
import com.ivan.prokofyev.handh_test.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiService.Creator.newWeatherService();
    }

    @Provides
    @Singleton
    FacebookAuthService provideFacebookAuthService() {
        return FacebookAuthService.Creator.newFacebookAuthService();
    }

}
