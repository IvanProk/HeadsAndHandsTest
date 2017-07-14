package com.ivan.prokofyev.handh_test.ui.auth.sign_in;

import com.ivan.prokofyev.handh_test.ui.base.MvpView;

/**
 * Created by Shiraha on 7/12/2017.
 */

public interface SignInMvpView extends MvpView{

    void showWrongCredentials();
    void showSignInSuccessful();
    void showNetworkError();
    void showNoUserFound();

}
