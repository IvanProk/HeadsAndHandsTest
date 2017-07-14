package com.ivan.prokofyev.handh_test.ui.auth.sign_up;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.prokofyev.handh_test.R;
import com.ivan.prokofyev.handh_test.ui.base.BaseActivity;
import com.ivan.prokofyev.handh_test.util.TextUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shiraha on 7/12/2017.
 */

public class SignUpActivity extends BaseActivity implements SignUpMvpView {

    private static final String EXTRA_EMAIL = "email";
    private static final String EXTRA_PASSWORD = "password";

    @Inject
    SignUpPresenter mSignUpPresenter;
    @BindView(R.id.nameInputLayout)
    TextInputLayout nameInputlayout;
    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.emailInputLayout)
    TextInputLayout emailInputLayout;
    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.passwordInputLayout)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.repeatPasswordInputLayout)
    TextInputLayout repeatPasswordInputLayout;
    @BindView(R.id.etRepeatPassword)
    EditText etRepeatPassword;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setupToolbar();
        mSignUpPresenter.attachView(this);

        etName.requestFocus();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            etEmail.setText(bundle.getString(EXTRA_EMAIL, ""));
            etPassword.setText(bundle.getString(EXTRA_PASSWORD, ""));
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(R.string.register);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignUpPresenter.detachView();
    }

    @Override
    public void showWrongCredentials() {
        TextUtils.setInputLayoutError(etEmail, emailInputLayout, getString(R.string.user_exists));
        toggleLoader(false);
    }

    @Override
    public void showSignUpSuccessful() {
        toggleLoader(false);
        finish();
    }

    @OnClick(R.id.btnSave)
    public void saveUser() {
        if (checkValidity()) {
            mSignUpPresenter.saveUser(etName.getText().toString(),
                    etEmail.getText().toString().toLowerCase(),
                    etPassword.getText().toString());
        }
    }

    private boolean checkValidity() {
        return TextUtils.isNameValid(etName, nameInputlayout, getString(R.string.field_error))
                && TextUtils.isEmailValid(etEmail, emailInputLayout, getString(R.string.field_error))
                && TextUtils.isPasswordValid(etPassword, passwordInputLayout, getString(R.string.field_error) + ". " + getString(R.string.password_rule))
                && TextUtils.isPasswordValid(etRepeatPassword, repeatPasswordInputLayout, getString(R.string.field_error))
                && TextUtils.equalityValidation(etPassword, etRepeatPassword, passwordInputLayout, repeatPasswordInputLayout, getString(R.string.password_equality_rule));
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SignUpActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, String email, String password) {
        Intent starter = new Intent(context, SignUpActivity.class);
        starter.putExtra(EXTRA_EMAIL, email);
        starter.putExtra(EXTRA_PASSWORD, password);
        context.startActivity(starter);
    }
}
