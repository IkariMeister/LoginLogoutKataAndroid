package com.karumi.loginlogoutkata.domain.usecase;

import com.karumi.loginlogoutkata.data.SessionCache;

public class IsLogged {

    private final SessionCache sessionCache;

    public IsLogged(SessionCache sessionCache) {
        this.sessionCache = sessionCache;
    }

    public boolean isLogged() {
        return sessionCache.hasCredentials();
    }
}
