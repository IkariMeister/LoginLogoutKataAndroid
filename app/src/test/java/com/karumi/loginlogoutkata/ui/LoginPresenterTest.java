package com.karumi.loginlogoutkata.ui;

import android.support.annotation.NonNull;
import com.karumi.loginlogoutkata.data.LoginApi;
import com.karumi.loginlogoutkata.data.SessionCache;
import com.karumi.loginlogoutkata.domain.error.ErrorCredentials;
import com.karumi.loginlogoutkata.domain.model.UserSession;
import com.karumi.loginlogoutkata.domain.usecase.DoLogin;
import com.karumi.loginlogoutkata.domain.usecase.callback.LoginResponseCallback;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
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
    @Mock DoLogin doLogin;
    @Mock LoginApi loginApi;
    @Mock SessionCache sessionCache;

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
        givenASucessLogin();
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);
        loginPresenter.doLogin();

        verify(view).logged();
    }

    @Test public void shouldMakeReturnInvalidCredentialWhenEmailDoesNotExist() throws Exception {
        ErrorCredentials errorCredentials = givenAnInvalidCredentials();
        LoginPresenter loginPresenter = givenLoginPresenter();

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);
        loginPresenter.doLogin();

        verify(view).showError(eq(errorCredentials));
    }

    @Test public void shouldStoreCredentialsWhenUserHasBeenLogged() {
        UserSession userSession = givenApiLoginCorrect();
        LoginPresenter loginPresenter = givenLoginPresenter(givenDoLogin());

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);
        loginPresenter.doLogin();

        verify(sessionCache).storeSession(userSession);
    }

    private UserSession givenApiLoginCorrect() {
        UserSession userSession = new UserSession();
        when(loginApi.login(anyString(), anyString())).thenReturn(userSession);

        return userSession;
    }

    @NonNull private LoginPresenter givenLoginPresenter() {
        return new LoginPresenter(view, doLogin);
    }

    @NonNull private LoginPresenter givenLoginPresenter(DoLogin doLogin) {
        return new LoginPresenter(view, doLogin);
    }

    @NonNull private DoLogin givenDoLogin() {
        return new DoLogin(loginApi, sessionCache);
    }

    private ErrorCredentials givenAnInvalidCredentials() {
        final ErrorCredentials errorCredentials = new ErrorCredentials();

        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                LoginResponseCallback callback =
                    (LoginResponseCallback) invocation.getArguments()[ARG_CALLBACK];
                callback.error(errorCredentials);
                return null;
            }
        }).when(doLogin)
            .login(anyString(), anyString(), any(LoginResponseCallback.class));

        return errorCredentials;
    }

    private void givenASucessLogin() {
        doAnswer(new Answer() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                LoginResponseCallback callback =
                    (LoginResponseCallback) invocation.getArguments()[ARG_CALLBACK];
                callback.sucess();
                return null;
            }
        }).when(doLogin)
            .login(anyString(), anyString(), any(LoginResponseCallback.class));
    }
}