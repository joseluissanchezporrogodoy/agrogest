package com.example.joseluissanchez_porrogodoy.agrogest.presenter;

/**
 * Created by joseluissanchez-porrogodoy on 24/8/16.
 */
public interface LoginPresenter {
    void receiveUserLogin(String email, String password);
    void onFailure();
    void onSuccess();
}
