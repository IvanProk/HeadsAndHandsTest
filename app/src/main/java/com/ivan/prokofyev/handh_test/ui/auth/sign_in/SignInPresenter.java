package com.ivan.prokofyev.handh_test.ui.auth.sign_in;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.injection.ConfigPersistent;
import com.ivan.prokofyev.handh_test.ui.base.BasePresenter;
import com.ivan.prokofyev.handh_test.util.RxEventBus;
import com.ivan.prokofyev.handh_test.util.RxUtil;

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

    @Inject
    RxEventBus mEventBus;

    @Inject
    public SignInPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SignInMvpView mvpView) {
        super.attachView(mvpView);
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
                }, () -> {
                    Timber.i("WTF");
                });
    }
}
