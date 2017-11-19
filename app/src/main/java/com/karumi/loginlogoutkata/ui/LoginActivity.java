package com.karumi.loginlogoutkata.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.karumi.loginlogoutkata.R;
import com.karumi.loginlogoutkata.common.StringUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText emailView;
    private EditText passwordView;
    private View loginButton;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapUi();
    }

    private void mapUi() {
        emailView = findViewById(R.id.et_login);
        passwordView = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.bt_login);

        emailView.addTextChangedListener(enableButtonTextWatcher);
        passwordView.addTextChangedListener(enableButtonTextWatcher);
    }

    TextWatcher enableButtonTextWatcher = new AfterTextWatcher() {
        @Override public void afterTextChanged(Editable editable) {
            String email = emailView.getText()
                .toString();
            String password = passwordView.getText()
                .toString();

            boolean enable = StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(password);
            loginButton.setEnabled(enable);
        }
    };
}
