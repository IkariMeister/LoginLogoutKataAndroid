package com.karumi.loginlogoutkata.data;

import com.karumi.loginlogoutkata.data.exception.CredentialException;
import com.karumi.loginlogoutkata.domain.model.UserSession;

public class LoginApi {
    public UserSession login(String email, String pass) throws CredentialException {
        return new UserSession();
    }
}
