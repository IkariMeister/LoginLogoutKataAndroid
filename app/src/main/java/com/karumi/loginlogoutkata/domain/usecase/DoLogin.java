package com.karumi.loginlogoutkata.domain.usecase;

import com.karumi.loginlogoutkata.data.LoginApi;
import com.karumi.loginlogoutkata.data.SessionCache;
import com.karumi.loginlogoutkata.domain.model.UserSession;
import com.karumi.loginlogoutkata.domain.usecase.callback.LoginResponseCallback;

public class DoLogin {

    private final LoginApi loginApi;
    private final SessionCache sessionCache;

    public DoLogin(LoginApi loginApi, SessionCache sessionCache) {
        this.loginApi = loginApi;
        this.sessionCache = sessionCache;
    }

    public void login(String email, String password, LoginResponseCallback loginResponseCallback) {
        UserSession userSession = loginApi.login(email, password);
        sessionCache.storeSession(userSession);
        loginResponseCallback.sucess();
    }
}
