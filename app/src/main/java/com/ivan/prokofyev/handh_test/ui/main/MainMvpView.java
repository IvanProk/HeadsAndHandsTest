package com.ivan.prokofyev.handh_test.ui.main;

import com.ivan.prokofyev.handh_test.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showWeatherSnack(String message);
    void showSignInScreen();
    void showNetworkError();

}
