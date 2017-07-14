package com.ivan.prokofyev.handh_test.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;

import com.ivan.prokofyev.handh_test.R;
import com.ivan.prokofyev.handh_test.data.SyncService;
import com.ivan.prokofyev.handh_test.ui.auth.sign_in.SignInActivity;
import com.ivan.prokofyev.handh_test.ui.base.BaseActivity;
import com.ivan.prokofyev.handh_test.util.RxEventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.ivan.prokofyev.handh_test.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    RxEventBus mEventBus;

    @BindView(R.id.mainLayout)
    ViewGroup mainLayout;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter.attachView(this);

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }

        mEventBus.filteredObservable(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getEventAction());
    }

    private Action1<? super String> getEventAction() {
        return (Action1<String>) name -> {
            if (name!=null)
                mMainPresenter.loadWeather(name, getString(R.string.weather_message));
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }


    /***** MVP View methods implementation *****/
    @OnClick(R.id.btnLogin)
    @Override
    public void showSignInScreen() {
        SignInActivity.start(this);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showWeatherSnack(String message) {
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", view -> {
                })
                .show();
    }
}
