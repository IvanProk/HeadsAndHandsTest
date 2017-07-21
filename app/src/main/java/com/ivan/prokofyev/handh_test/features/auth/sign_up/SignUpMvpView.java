package com.ivan.prokofyev.handh_test.features.auth.sign_up;

import com.ivan.prokofyev.handh_test.features.base.MvpView;

/**
 * Created by Shiraha on 7/12/2017.
 */

public interface SignUpMvpView extends MvpView{

    void showWrongCredentials();
    void showSignUpSuccessful();

}
