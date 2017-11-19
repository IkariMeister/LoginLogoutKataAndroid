package com.karumi.loginlogoutkata.domain.usecase;

import com.karumi.loginlogoutkata.domain.usecase.callback.LoginResponseCallback;

public class DoLogin {
    public void login(String email, String password, LoginResponseCallback loginResponseCallback) {
        loginResponseCallback.sucess();
    }
}
