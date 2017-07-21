package com.ivan.prokofyev.handh_test.features.main;

import com.ivan.prokofyev.handh_test.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showWeatherSnack(String message);
    void showSignInScreen();
    void showNetworkError();

}
