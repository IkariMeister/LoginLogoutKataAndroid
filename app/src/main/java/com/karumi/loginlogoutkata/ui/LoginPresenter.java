package com.karumi.loginlogoutkata.ui;

import com.karumi.loginlogoutkata.common.StringUtils;
import com.karumi.loginlogoutkata.domain.usecase.DoLogin;
import com.karumi.loginlogoutkata.domain.usecase.DoLogout;
import com.karumi.loginlogoutkata.domain.usecase.IsLogged;
import com.karumi.loginlogoutkata.domain.usecase.callback.LoginResponseCallback;

public class LoginPresenter {

    private final View view;
    private final DoLogin doLogin;
    private final IsLogged isLogged;
    private final DoLogout doLogout;

    private String email;
    private String password;

    public LoginPresenter(View view, DoLogin doLogin, IsLogged isLogged, DoLogout doLogout) {
        this.view = view;
        this.doLogin = doLogin;
        this.isLogged = isLogged;
        this.doLogout = doLogout;
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

            @Override public void error(Error error) {
                view.showError(error);
            }
        });
    }

    public void onResume() {
        if (isLogged.isLogged()) {
            view.logged();
        }
    }

    public void logout() {
        doLogout.logout();
    }

    interface View {
        void enableLoginButton();

        void disableLoginButton();

        void logged();

        void showError(Error error);
    }
}
