package com.karumi.loginlogoutkata.domain.usecase.callback;

public interface LoginResponseCallback {
    void sucess();

    void error(Error error);
}
