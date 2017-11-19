package com.karumi.loginlogoutkata.ui;

import com.karumi.loginlogoutkata.common.StringUtils;
import com.karumi.loginlogoutkata.domain.usecase.DoLogin;
import com.karumi.loginlogoutkata.domain.usecase.callback.LoginResponseCallback;

public class LoginPresenter {

    private final View view;
    private final DoLogin doLogin;

    private String email;
    private String password;

    public LoginPresenter(View view, DoLogin doLogin) {
        this.view = view;
        this.doLogin = doLogin;
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
        doLogin.login(email, password, new LoginResponseCallback() {
            @Override public void sucess() {
                view.logged();
            }
        });
    }

    interface View {
        void enableLoginButton();

        void disableLoginButton();

        void logged();
    }
}
