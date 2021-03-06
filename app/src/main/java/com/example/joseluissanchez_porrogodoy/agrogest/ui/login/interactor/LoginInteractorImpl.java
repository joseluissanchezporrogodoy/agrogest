package com.example.joseluissanchez_porrogodoy.agrogest.ui.login.interactor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.joseluissanchez_porrogodoy.agrogest.ui.login.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joseluissanchez-porrogodoy on 24/8/16.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private final LoginPresenter presenter;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void attemptToLogIn(String email,final String password) {


        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                presenter.onFailure();
                            } else {
                                presenter.onFailure();

                            }
                        }else {

                            presenter.onSuccess();
                        }
                    }
                });

    }

}
