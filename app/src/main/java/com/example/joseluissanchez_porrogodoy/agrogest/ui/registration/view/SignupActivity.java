package com.example.joseluissanchez_porrogodoy.agrogest.ui.registration.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.ResetPasswordActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.registration.presenter.SingupPresenter;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.registration.presenter.SingupPresenterImpl;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements SingupView {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private SingupPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        createUI();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.receiveUserSinUp(inputEmail.getText().toString().trim(),inputPassword.getText().toString().trim());
            }
        });
    }
    private void createUI() {
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goToResetPassword();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goToLogin();
            }
        });
        presenter = new SingupPresenterImpl(this);


    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void goToResetPassword() {
        startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
    }

    @Override
    public void goToLogin() {
        finish();
    }

    @Override
    public void spinProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void showAlertMessage(String message) {

    }
}