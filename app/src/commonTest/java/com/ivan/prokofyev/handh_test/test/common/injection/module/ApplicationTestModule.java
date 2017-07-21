package com.ivan.prokofyev.handh_test.test.common.injection.module;

import android.app.Application;
import android.content.Context;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.data.remote.ApiService;
import com.ivan.prokofyev.handh_test.data.remote.FacebookAuthService;
import com.ivan.prokofyev.handh_test.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Provides application-level dependencies for an app running on a testing environment
 * This allows injecting mocks if necessary.
 */
@Module
public class ApplicationTestModule {

    private final Application mApplication;

    public ApplicationTestModule(Application application) {
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

    /************* MOCKS *************/

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return mock(ApiService.class);
    }

    @Provides
    @Singleton
    FacebookAuthService provideFacebookAuthService() {
        return mock(FacebookAuthService.class);
    }

}
