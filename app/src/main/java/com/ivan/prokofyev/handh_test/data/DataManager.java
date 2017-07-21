package com.ivan.prokofyev.handh_test.data;

import com.ivan.prokofyev.handh_test.data.local.DatabaseHelper;
import com.ivan.prokofyev.handh_test.data.local.PreferencesHelper;
import com.ivan.prokofyev.handh_test.data.model.User;
import com.ivan.prokofyev.handh_test.data.model.Weather;
import com.ivan.prokofyev.handh_test.data.remote.ApiService;
import com.ivan.prokofyev.handh_test.data.remote.FacebookAuthService;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private final ApiService mApiService;
    private final FacebookAuthService mFbAuthService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ApiService apiService,
                       FacebookAuthService fbAuthService,
                       PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mApiService = apiService;
        mFbAuthService = fbAuthService;
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

    public Observable<User> saveUser(User user) {
        return mDatabaseHelper.addUser(user);
    }

    public Observable<User> getUser(String email) {
        return mDatabaseHelper.getUser(email);
    }

    public Observable<User> getUserViaFacebook(String accessToken) {
        return mDatabaseHelper.getUserByToken(accessToken)
                .flatMap(user -> {
                    if (user != null)
                        return Observable.just(user);
                    else
                        return mFbAuthService.getUserProfile(accessToken)
                                .flatMap(facebookUserResponse -> {
                                    User user1 = User.builder()
                                            .setName(facebookUserResponse.getName())
                                            .setEmail(facebookUserResponse.getEmail())
                                            .setPassword("")
                                            .setToken(accessToken)
                                            .build();
                                    return mDatabaseHelper.addUser(user1);
                                });
                });

    }
}
