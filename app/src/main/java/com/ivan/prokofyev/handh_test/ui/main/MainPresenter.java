package com.ivan.prokofyev.handh_test.ui.main;

import com.ivan.prokofyev.handh_test.data.DataManager;
import com.ivan.prokofyev.handh_test.data.model.Weather;
import com.ivan.prokofyev.handh_test.injection.ConfigPersistent;
import com.ivan.prokofyev.handh_test.ui.base.BasePresenter;
import com.ivan.prokofyev.handh_test.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadWeather(String name, String templateString) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(weather -> {
                    getMvpView().showWeatherSnack(getWeatherMessage(name ,templateString,weather));
                }, throwable -> {
                    getMvpView().showNetworkError();
                });
    }

    private String getWeatherMessage(String name, String templateString, Weather weather){
        return String.format(templateString, name, weather.temperature(), weather.description());
    }
}
