package com.karumi.loginlogoutkata.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.karumi.loginlogoutkata.R;
import com.karumi.loginlogoutkata.domain.usecase.DoLogin;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    private EditText emailView;
    private EditText passwordView;
    private View loginButton;

    private LoginPresenter loginPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this, new DoLogin());
        mapUi();
    }

    private void mapUi() {
        emailView = findViewById(R.id.et_login);
        passwordView = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.bt_login);

        emailView.addTextChangedListener(emailTextWatcher);
        passwordView.addTextChangedListener(passwordTextWatcher);
    }

    TextWatcher emailTextWatcher = new AfterTextWatcher() {
        @Override public void afterTextChanged(Editable editable) {
            String email = emailView.getText()
                .toString();
            loginPresenter.updateEmail(email);
        }
    };

    TextWatcher passwordTextWatcher = new AfterTextWatcher() {
        @Override public void afterTextChanged(Editable editable) {
            String password = passwordView.getText()
                .toString();
            loginPresenter.updateEmail(password);
        }
    };

    @Override public void enableLoginButton() {
        loginButton.setEnabled(true);
    }

    @Override public void disableLoginButton() {
        loginButton.setEnabled(false);
    }

    @Override public void logged() {

    }
}
