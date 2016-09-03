package com.example.joseluissanchez_porrogodoy.agrogest.ui.registration.view;

/**
 * Created by joseluissanchez-porrogodoy on 26/8/16.
 */
public interface SingupView {
    void goToResetPassword();
    void goToLogin();
    void spinProgressBar();
    void stopProgressBar();
    void onSuccess();
    void onFailure();
    void showAlertMessage(String message);

}
