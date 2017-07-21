package com.ivan.prokofyev.handh_test.features.auth.sign_in;

import android.app.Activity;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.features.base.BasePresenter;
import com.ivan.prokofyev.handh_test.injection.ConfigPersistent;
import com.ivan.prokofyev.handh_test.util.RxEventBus;
import com.ivan.prokofyev.handh_test.util.RxUtil;

import java.util.Arrays;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Shiraha on 7/12/2017.
 */
@ConfigPersistent
public class SignInPresenter extends BasePresenter<SignInMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    CallbackManager mCallbackManager;

    @Inject
    RxEventBus mEventBus;

    @Inject
    public SignInPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SignInMvpView mvpView) {
        super.attachView(mvpView);
        initFacebook();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }


    public void signIn(String email, String password) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getUser(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    if (user == null)
                        getMvpView().showNoUserFound();
                    else if (user.password().equals(password)) {
                        mEventBus.postString(user.name());
                        getMvpView().showSignInSuccessful();
                    } else {
                        getMvpView().showWrongCredentials();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    getMvpView().showNoUserFound();
                });
    }

    private void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                            loadUserFacebookProfile(loginResult.getAccessToken().getToken());

//                        Timber.i("onSuccess: " + loginResult.getAccessToken().getToken());
//                        mEventBus.postString(loginResult.getAccessToken().getUserId());
//                        getMvpView().showSignInSuccessful() ;
                    }

                    @Override
                    public void onCancel() {
                        Timber.i("onCancel: ");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        e.printStackTrace();
                        Timber.e("Error!!! " + e);
                    }
                });
    }

    private void loadUserFacebookProfile(String accessToken){
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getUserViaFacebook(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(user -> {
                    if (user != null) {
                        mEventBus.postString(user.name());
                        getMvpView().showSignInSuccessful();
                    }else
                        getMvpView().showNoUserFound();
                }, throwable -> {
                    throwable.printStackTrace();
                    getMvpView().showNoUserFound();
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginWithFacebook(Activity activity){
        LoginManager.getInstance().logInWithReadPermissions(activity,
                Arrays.asList("public_profile", "email"));
    }

    public void logOutFacebook() {
        LoginManager.getInstance().logOut();
    }
}
