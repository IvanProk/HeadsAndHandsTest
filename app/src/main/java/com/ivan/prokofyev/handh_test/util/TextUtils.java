package com.ivan.prokofyev.handh_test.util;

import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by Shiraha on 7/13/2017.
 */

public class TextUtils {
    public static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";


    public static boolean isEmailValid(EditText editText, TextInputLayout layout, String errorMessage) {
        return (emptyValidation(editText, layout, errorMessage)
                && regExMatchValidation(editText, layout, Patterns.EMAIL_ADDRESS.pattern(), errorMessage));
    }

    public static boolean isNameValid(EditText editText, TextInputLayout layout, String errorMessage) {
        return emptyValidation(editText, layout, errorMessage);
    }

    public static boolean isPasswordValid(EditText editText, TextInputLayout layout, String errorMessage) {
        return emptyValidation(editText, layout, errorMessage)
                && regExMatchValidation(editText, layout, PASSWORD_PATTERN, errorMessage);

    }

    public static boolean isPasswordsValid(EditText editText1, EditText editText2,
                                           TextInputLayout layout1, TextInputLayout layout2,
                                           String errorMessage) {
        return !emptyValidation(editText1, layout1, errorMessage)
                && regExMatchValidation(editText1, layout1, PASSWORD_PATTERN, errorMessage)
                && emptyValidation(editText2, layout2, errorMessage)
                && regExMatchValidation(editText2, layout2, PASSWORD_PATTERN, errorMessage)
                && editText1.getText().toString().equals(editText2.getText().toString());
    }

    public static boolean equalityValidation(EditText editText1, EditText editText2,
                                             TextInputLayout layout1, TextInputLayout layout2,
                                             String errorMessage) {
        if (editText1.getText().toString().equals(editText2.getText().toString())) {
            clearInputLayoutError(layout2);
            return true;
        } else {
            setInputLayoutError(editText2, layout2, errorMessage);
            return false;
        }
    }

    public static boolean emptyValidation(EditText editText, TextInputLayout textInputLayout, String errorMessage) {
        if (editText.getText().toString().isEmpty()) {
            setInputLayoutError(editText, textInputLayout, errorMessage);
            return false;
        } else {
            clearInputLayoutError(textInputLayout);
            return true;
        }
    }

    public static boolean regExMatchValidation(EditText editText, TextInputLayout textInputLayout, String regEx, String errorMessage) {
        if (editText.getText().toString().matches(regEx)) {
            clearInputLayoutError(textInputLayout);
            return true;
        } else {
            setInputLayoutError(editText, textInputLayout, errorMessage);
            return false;
        }
    }

    public static void setInputLayoutError(EditText textView, TextInputLayout textInputLayout, String errorMessage) {
        textInputLayout.setError(errorMessage);
        textInputLayout.setErrorEnabled(true);
        textView.requestFocus();
    }

    public static void clearInputLayoutError(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }
}
