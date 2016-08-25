package com.example.joseluissanchez_porrogodoy.agrogest.interactor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.joseluissanchez_porrogodoy.agrogest.Activity.LoginActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.Activity.MainActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by joseluissanchez-porrogodoy on 24/8/16.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private final LoginPresenter presenter;
    private FirebaseAuth auth;
    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void attemptToLogIn(String email,final String password) {
        if (auth.getCurrentUser() != null) {
            presenter.onSuccess();
        }

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {

                                //Set error en vista
                                //inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                //Set Error en vista
                                //Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        }else {

                            presenter.onSuccess();
                        }
                    }
                });

    }
}
