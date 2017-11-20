package com.karumi.loginlogoutkata.data;

import com.karumi.loginlogoutkata.domain.model.UserSession;

public class LoginApi {
    public UserSession login(String email, String pass) {
        return new UserSession();
    }
}
