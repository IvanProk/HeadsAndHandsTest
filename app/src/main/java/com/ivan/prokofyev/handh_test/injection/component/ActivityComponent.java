package com.ivan.prokofyev.handh_test.injection.component;

import com.ivan.prokofyev.handh_test.injection.PerActivity;
import com.ivan.prokofyev.handh_test.injection.module.ActivityModule;
import com.ivan.prokofyev.handh_test.features.auth.sign_in.SignInActivity;
import com.ivan.prokofyev.handh_test.features.auth.sign_up.SignUpActivity;
import com.ivan.prokofyev.handh_test.features.main.MainActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SignInActivity signInActivity);
    void inject(SignUpActivity signUpActivity);

}
