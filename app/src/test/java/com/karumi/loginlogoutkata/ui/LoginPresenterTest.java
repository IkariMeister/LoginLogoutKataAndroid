package com.karumi.loginlogoutkata.ui;

import android.support.annotation.NonNull;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private static final String NOT_EMPTY_EMAIL = "any_email";
    private static final String NOT_EMPTY_PASSWORD = "any_password";
    private static final String EMPTY_EMAIL = null;
    private static final String EMPTY_PASSWORD = null;
    private static final int ARG_CALLBACK = 2;

    @Mock LoginPresenter.View view;
    @Mock DoLogin doLogin;

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

    @NonNull private LoginPresenter givenLoginPresenter() {
        return new LoginPresenter(view, doLogin);
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