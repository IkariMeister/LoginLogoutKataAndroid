package com.karumi.loginlogoutkata.ui;

import android.support.annotation.NonNull;
import com.karumi.loginlogoutkata.data.LoginApi;
import com.karumi.loginlogoutkata.data.SessionCache;
import com.karumi.loginlogoutkata.data.exception.CredentialException;
import com.karumi.loginlogoutkata.domain.error.ErrorCredentials;
import com.karumi.loginlogoutkata.domain.model.UserSession;
import com.karumi.loginlogoutkata.domain.usecase.DoLogin;
import com.karumi.loginlogoutkata.domain.usecase.DoLogout;
import com.karumi.loginlogoutkata.domain.usecase.IsLogged;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {

    private static final String NOT_EMPTY_EMAIL = "any_email";
    private static final String NOT_EMPTY_PASSWORD = "any_password";
    private static final String EMPTY_EMAIL = null;
    private static final String EMPTY_PASSWORD = null;
    private static final int ARG_CALLBACK = 2;

    @Mock LoginPresenter.View view;
    @Mock LoginApi loginApi;
    @Mock SessionCache sessionCache;
    private LoginPresenter loginPresenter;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldEnableButtonWhenLoginAndFieldsAreNotEmpty() throws Exception {
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);

        verify(view).enableLoginButton();
    }

    @Test public void shouldDisableButtonWhenLoginIsEmpty() throws Exception {
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);

        verify(view, atLeastOnce()).disableLoginButton();
        verify(view, never()).enableLoginButton();
    }

    @Test public void shouldDisableButtonWhenPasswordIsEmpty() throws Exception {
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_PASSWORD);
        loginPresenter.updatePassword(EMPTY_PASSWORD);

        verify(view, atLeastOnce()).disableLoginButton();
        verify(view, never()).enableLoginButton();
    }

    @Test public void shouldDisableButtonWhenPasswordAndLoginIsEmpty() throws Exception {
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(EMPTY_EMAIL);
        loginPresenter.updatePassword(EMPTY_PASSWORD);

        verify(view, atLeastOnce()).disableLoginButton();
        verify(view, never()).enableLoginButton();
    }

    @Test public void shouldMakeLoginWhenCredentialsAreCorrectAndPressLogin() throws Exception {
        givenApiLoginCorrect();
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);
        loginPresenter.doLogin();

        verify(view).logged();
    }

    @Test public void shouldMakeReturnInvalidCredentialWhenEmailDoesNotExist() throws Exception {
        givenAnInvalidCredentials();
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);
        loginPresenter.doLogin();

        verify(view).showError(any(ErrorCredentials.class));
    }

    @Test public void shouldStoreCredentialsWhenUserHasBeenLogged() throws CredentialException {
        UserSession userSession = givenApiLoginCorrect();
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);
        loginPresenter.doLogin();

        verify(sessionCache).storeSession(userSession);
    }

    @Test public void shouldLoginIfTheCredentialsHasBeenProvided() throws Exception {
        givenCredentials();
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.onResume();

        verify(view).logged();
    }

    @Test public void shouldRemoveCacheDataWhenTheUserPressLogout() throws Exception {
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.logout();

        verify(sessionCache).clear();
    }

    private UserSession givenApiLoginCorrect() throws CredentialException {
        UserSession userSession = new UserSession();
        when(loginApi.login(anyString(), anyString())).thenReturn(userSession);

        return userSession;
    }

    private void givenCredentials() {
        when(sessionCache.hasCredentials()).thenReturn(true);
    }

    @NonNull private LoginPresenter givenLoginPresenter() {
        return new LoginPresenter(view, givenDoLogin(), givenIsLogged(), givenDoLogout());
    }

    private DoLogout givenDoLogout() {
        return new DoLogout(sessionCache);
    }

    private IsLogged givenIsLogged() {
        return new IsLogged(sessionCache);
    }

    @NonNull private DoLogin givenDoLogin() {
        return new DoLogin(loginApi, sessionCache);
    }

    private void givenAnInvalidCredentials() throws CredentialException {
        when(loginApi.login(anyString(), anyString())).thenThrow(new CredentialException());
    }
}