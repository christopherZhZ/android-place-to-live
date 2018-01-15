package com.christopherzhz.placetoliveapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.christopherzhz.placetoliveapp.R;
import com.christopherzhz.placetoliveapp.common.C;
import com.christopherzhz.placetoliveapp.common.helpers.UserUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    
    @BindView(R.id.register_email)
    AutoCompleteTextView mEmailField;
    @BindView(R.id.register_password)
    EditText mPasswordField;
    @BindView(R.id.register_confirm_password)
    EditText mConfirmPasswordField;
    @BindView(R.id.back_to_signin_button)
    ImageButton mBackButton;
    @BindView(R.id.register_signup_button)
    Button mSignupButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmailAndBackToLogin(null);
            }
        });

        mConfirmPasswordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.imeActionRegister || id == EditorInfo.IME_NULL) {
                    Log.d(C.DEBUG_TAG, "Register: try register by enter");
                    attempRegister();
                    return true;
                }
                return false;
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(C.DEBUG_TAG, "Register: try register by button");
                attempRegister();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * attempRegister attempts to register an new user.
     */
    private void attempRegister() {
        mEmailField.setError(null);
        mPasswordField.setError(null);

        final String email = mEmailField.getText().toString();
        final String pswd = mPasswordField.getText().toString();
        final String confirmPswd = mConfirmPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pswd)) {
            if (!UserUtils.isPasswordValid(pswd)) {
                mPasswordField.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordField;
                cancel = true;
            } else if (!isPasswordMatched(pswd)) {
                mPasswordField.setError(getString(R.string.error_confirm_password_not_match));
                focusView = mPasswordField;
                cancel = true;
            }
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.error_field_required));
            focusView = mEmailField;
            cancel = true;
        } else if (!UserUtils.isEmailValid(email)) {
            mEmailField.setError(getString(R.string.error_invalid_email));
            focusView = mEmailField;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return;
        } else {
            // Create a Firebase user
            mAuth.createUserWithEmailAndPassword(email, pswd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(C.DEBUG_TAG, "attempRegister: success");
                                saveEmailAndBackToLogin(email);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.d(C.DEBUG_TAG, "attemptRegister: failure: invalid email");
                                mEmailField.setError(getString(R.string.error_invalid_email));
                                mEmailField.requestFocus();
                            } else if (e instanceof FirebaseAuthUserCollisionException) {
                                String errorCode = ((FirebaseAuthUserCollisionException) e).getErrorCode();
                                if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                    Log.d(C.DEBUG_TAG, "attemptRegister: failure: used email");
                                    mEmailField.setError(getString(R.string.error_used_email));
                                    mEmailField.requestFocus();
                                }
                            } else {
                                Log.e(C.DEBUG_TAG, "attemptRegister: failure: " + e.getMessage());
                            }
                        }
                    });
        }
    }

    private boolean isPasswordMatched(String pswd) {
        return mConfirmPasswordField.getText().toString().equals(pswd);
    }

    private void saveEmailAndBackToLogin(String email) {
        Intent emailResultIntent = new Intent();
        emailResultIntent.putExtra("currEmail", email);
        if (email != null) {
            setResult(Activity.RESULT_OK, emailResultIntent);
        } else {
            setResult(Activity.RESULT_CANCELED, emailResultIntent);
        }
        Log.d(C.DEBUG_TAG, "saveEmailAndBackToLogin: finished registration");
        finish();
    }

}
