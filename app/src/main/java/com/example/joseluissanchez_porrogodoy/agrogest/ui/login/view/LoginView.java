package com.example.joseluissanchez_porrogodoy.agrogest.ui.login.view;

/**
 * Created by joseluissanchez-porrogodoy on 24/8/16.
 */
public interface LoginView {
    void showAlertMessage(String message);
    void goToSingup();
    void goToResetPassword();
    void logTheUserIn();
    void onFailure();
    void spinProgressBar();
    void stopProgressBar();
}
