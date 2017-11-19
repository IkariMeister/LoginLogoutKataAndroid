package com.karumi.loginlogoutkata.ui;

import com.karumi.loginlogoutkata.common.StringUtils;

public class LoginPresenter {

    private final View view;

    private String email;
    private String password;

    public LoginPresenter(View view) {
        this.view = view;
    }

    public void updateEmail(String email) {
        this.email = email;
        updateLoginButtonState();
    }

    public void updatePassword(String password) {
        this.password = password;
        updateLoginButtonState();
    }

    private void updateLoginButtonState() {
        boolean enable = StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(password);
        if (enable) {
            view.enableLoginButton();
        } else {
            view.disableLoginButton();
        }
    }

    public void doLogin() {

    }

    interface View {
        void enableLoginButton();

        void disableLoginButton();

        void logged();
    }
}
