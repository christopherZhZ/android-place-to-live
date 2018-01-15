package com.christopherzhz.placetoliveapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.christopherzhz.placetoliveapp.R;
import com.christopherzhz.placetoliveapp.common.C;
import com.christopherzhz.placetoliveapp.common.helpers.PopupUtils;
import com.christopherzhz.placetoliveapp.common.helpers.UserUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    AutoCompleteTextView mEmailField;
    @BindView(R.id.login_password)
    EditText mPasswordField;
    @BindView(R.id.login_signin_button)
    Button mSigninButton;
    @BindView(R.id.login_signup_button)
    Button mSignupButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPasswordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.imeActionLogin && id == EditorInfo.IME_NULL) {
                    Log.d(C.DEBUG_TAG, "Login: try login by enter");
                    attempLogin();
                    return true;
                }
                return false;
            }
        });

        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(C.DEBUG_TAG, "Login: try login by button");
                attempLogin();
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(C.DEBUG_TAG, "Login: user clicked sign up");
                Intent signupPageIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(signupPageIntent, C.REGISTER_USER_REQUEST);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == C.REGISTER_USER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Registration successful
                PopupUtils.showToastMessage(this, R.string.success_registered);
                String currEmail = data.getStringExtra("currEmail");
                Log.d(C.DEBUG_TAG, "Register finished: current email: " + currEmail);
                mEmailField.setText(currEmail);
                mPasswordField.requestFocus();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Registration unfinished
                Log.d(C.DEBUG_TAG, "register finished: user clicked back");
            }
        }
    }

    /**
     * attempLogin attemps to login.
     */
    private void attempLogin() {
        final String email = mEmailField.getText().toString();
        String pswd = mPasswordField.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pswd) && !UserUtils.isPasswordValid(pswd)) {
            mPasswordField.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordField;
            cancel = true;
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
        }

        PopupUtils.showToastMessage(this, R.string.process_signing_in);

        mAuth.signInWithEmailAndPassword(email, pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(C.DEBUG_TAG, "attempLogin: success");

                            SharedPreferences prefs = getSharedPreferences(C.ROOM_PREFS, MODE_PRIVATE);
                            prefs.edit().putString(C.ONSCREEN_USERNAME_KEY, UserUtils.generateUserName(email)).apply();

                            Intent signInIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(signInIntent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(C.DEBUG_TAG, "attemptLogin: failure");
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            mPasswordField.setError(getString(R.string.error_wrong_password));
                            mPasswordField.requestFocus();
                        } else if (e instanceof FirebaseAuthInvalidUserException) {
                            mEmailField.setError(getString(R.string.error_wrong_email));
                            mEmailField.requestFocus();
                        } else {
                            Log.d(C.DEBUG_TAG, "signIn: failure: " + e.getLocalizedMessage());
                        }
                    }
                });
    }

}
