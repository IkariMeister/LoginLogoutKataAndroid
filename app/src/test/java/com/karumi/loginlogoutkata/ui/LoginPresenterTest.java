package com.karumi.loginlogoutkata.ui;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private static final String NOT_EMPTY_EMAIL = "any_email";
    private static final String NOT_EMPTY_PASSWORD = "any_password";
    private static final String EMPTY_EMAIL = null;

    @Mock LoginPresenter.View view;

    @Before public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldEnableButtonWhenLoginAndFieldsAreNotEmpty() throws Exception {
        LoginPresenter loginPresenter = new LoginPresenter(view);

        loginPresenter.updateEmail(NOT_EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);

        verify(view).enableLoginButton();
    }

    @Test public void shouldDisableButtonWhenLoginIsEmpty() throws Exception {
        LoginPresenter loginPresenter = new LoginPresenter(view);

        loginPresenter.updateEmail(EMPTY_EMAIL);
        loginPresenter.updatePassword(NOT_EMPTY_PASSWORD);

        verify(view, atLeastOnce()).disableLoginButton();
        verify(view, never()).enableLoginButton();
    }

    
}