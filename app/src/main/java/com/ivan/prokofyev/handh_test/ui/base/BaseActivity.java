package com.ivan.prokofyev.handh_test.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ivan.prokofyev.handh_test.HTApplication;
import com.ivan.prokofyev.handh_test.R;
import com.ivan.prokofyev.handh_test.injection.component.ActivityComponent;
import com.ivan.prokofyev.handh_test.injection.component.ConfigPersistentComponent;
import com.ivan.prokofyev.handh_test.injection.component.DaggerConfigPersistentComponent;
import com.ivan.prokofyev.handh_test.injection.module.ActivityModule;
import com.ivan.prokofyev.handh_test.util.DialogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
public class BaseActivity extends AppCompatActivity implements MvpView{

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(HTApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onPause() {
        if(mDialog!= null){
            mDialog.dismiss();
            mDialog = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

    @Override
    public void toggleLoader(boolean toShow) {
        if (mDialog == null)
            mDialog = DialogFactory.createProgressDialog(this, R.string.loading);

        if (toShow && !mDialog.isShowing())
            mDialog.show();
        else {
            mDialog.hide();
            mDialog = null;
        }
    }

    @Override
    public void toggleError(boolean toShow) {
        if (mDialog == null)
            mDialog = DialogFactory.createSimpleOkErrorDialog(this, R.string.error, R.string.loading);

        if (toShow && !mDialog.isShowing())
            mDialog.show();
        else {
            mDialog.hide();
            mDialog = null;
        }
    }


}
