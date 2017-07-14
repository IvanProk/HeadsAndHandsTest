package com.ivan.prokofyev.handh_test.ui.auth.sign_up;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.data.model.User;
import com.ivan.prokofyev.handh_test.injection.ConfigPersistent;
import com.ivan.prokofyev.handh_test.ui.base.BasePresenter;
import com.ivan.prokofyev.handh_test.util.RxEventBus;
import com.ivan.prokofyev.handh_test.util.RxUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Shiraha on 7/12/2017.
 */
@ConfigPersistent
public class SignUpPresenter extends BasePresenter<SignUpMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    @Inject
    RxEventBus mEventBus;

    @Inject
    public SignUpPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SignUpMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void saveUser(String name, String email, String password) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        User user = User.builder()
                .setEmail(email)
                .setName(name)
                .setPassword(password)
                .build();
        getMvpView().toggleLoader(true);
        mSubscription = mDataManager.getUser(email).flatMap(user1 -> {
            if (user1!= null && user1.email().equals(email))
                return Observable.just(null);
            else
                return mDataManager.saveUser(user);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(user1 -> {
                    if (user1 == null) {
                        getMvpView().showWrongCredentials();
                    } else {
                        mEventBus.post(user1);
                        getMvpView().showSignUpSuccessful();
                    }
                }, Throwable::printStackTrace);
    }

}
