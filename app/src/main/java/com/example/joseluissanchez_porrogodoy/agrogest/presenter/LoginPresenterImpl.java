package com.example.joseluissanchez_porrogodoy.agrogest.presenter;

import com.example.joseluissanchez_porrogodoy.agrogest.interactor.LoginInteractor;
import com.example.joseluissanchez_porrogodoy.agrogest.interactor.LoginInteractorImpl;
import com.example.joseluissanchez_porrogodoy.agrogest.view.LoginView;

/**
 * Created by joseluissanchez-porrogodoy on 24/8/16.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView loginView;
    private final LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void receiveUserLogin(String email, String password) {
        loginView.spinProgressBar();
        interactor.attemptToLogIn(email, password);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onSuccess() {
        loginView.stopProgressBar();
        loginView.logTheUserIn();

    }
}
