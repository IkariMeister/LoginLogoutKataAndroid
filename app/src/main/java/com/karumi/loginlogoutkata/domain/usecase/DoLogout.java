package com.karumi.loginlogoutkata.domain.usecase;

import com.karumi.loginlogoutkata.data.SessionCache;

public class DoLogout {

    private final SessionCache sessionCache;

    public DoLogout(SessionCache sessionCache) {
        this.sessionCache = sessionCache;
    }

    public void logout() {
        sessionCache.clear();
    }
}
