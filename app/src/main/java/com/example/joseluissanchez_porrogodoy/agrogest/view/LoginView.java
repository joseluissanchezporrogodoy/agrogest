package com.example.joseluissanchez_porrogodoy.agrogest.view;

/**
 * Created by joseluissanchez-porrogodoy on 24/8/16.
 */
public interface LoginView {
    void goToSingup();
    void goToResetPassword();
    void logTheUserIn();
    void onFailure();
    void spinProgressBar();
    void stopProgressBar();
}
