package com.ivan.prokofyev.handh_test.data;

import com.ivan.prokofyev.handh_test.data.local.DatabaseHelper;
import com.ivan.prokofyev.handh_test.data.local.PreferencesHelper;
import com.ivan.prokofyev.handh_test.data.model.User;
import com.ivan.prokofyev.handh_test.data.model.Weather;
import com.ivan.prokofyev.handh_test.data.remote.ApiService;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private final ApiService mApiService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ApiService apiService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mApiService = apiService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Weather> syncWeather() {
        return mApiService.getWeather(Locale.getDefault().getLanguage())
                .flatMap(mDatabaseHelper::setWeather);
    }

    public Observable<Weather> getWeather() {
        return mDatabaseHelper.getWeather().distinct();
    }

    public Observable<User> saveUser(User user){
        return mDatabaseHelper.addUser(user);
    }

    public Observable<User> getUser(String email){
        return mDatabaseHelper.getUser(email);
    }

}
