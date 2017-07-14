package com.ivan.prokofyev.handh_test.ui.auth.sign_in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.prokofyev.handh_test.R;
import com.ivan.prokofyev.handh_test.data.model.User;
import com.ivan.prokofyev.handh_test.ui.auth.sign_up.SignUpActivity;
import com.ivan.prokofyev.handh_test.ui.base.BaseActivity;
import com.ivan.prokofyev.handh_test.util.DialogFactory;
import com.ivan.prokofyev.handh_test.util.RxEventBus;
import com.ivan.prokofyev.handh_test.util.TextUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Shiraha on 7/12/2017.
 */

public class SignInActivity extends BaseActivity implements SignInMvpView {

    @Inject
    SignInPresenter mSignInPresenter;
    @BindView(R.id.emailInputLayout)
    TextInputLayout emailInputLayout;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.passwordInputLayout)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    RxEventBus mEventBus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setupToolbar();
        mSignInPresenter.attachView(this);
        etEmail.requestFocus();
        mEventBus.filteredObservable(User.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getSignUpEventAction());
    }

    private Action1<? super User> getSignUpEventAction() {
        return (Action1<User>) user -> {
            etEmail.setText(user.email());
            etPassword.setText(user.password());
        };
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(R.string.auth);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_create:
                SignUpActivity.start(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignInPresenter.detachView();
    }

    private boolean checkValidity() {
        return TextUtils.isEmailValid(etEmail, emailInputLayout, getString(R.string.field_error))
                && TextUtils.isPasswordValid(etPassword, passwordInputLayout, getString(R.string.wrong_password));
    }

    @OnClick(R.id.btnSignIn)
    public void signIn() {
        if (checkValidity()) {
            mSignInPresenter.signIn(etEmail.getText().toString().toLowerCase(),
                    etPassword.getText().toString());
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SignInActivity.class);
        context.startActivity(starter);
    }


    @Override
    public void showWrongCredentials() {
        TextUtils.setInputLayoutError(etPassword, passwordInputLayout, getString(R.string.wrong_password));
    }

    @Override
    public void showSignInSuccessful() {
        finish();
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showNoUserFound() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.no_user_found),
                getString(R.string.create), () -> {
                    SignUpActivity.start(this, etEmail.getText().toString(), etPassword.getText().toString());
                }).show();
    }
}
