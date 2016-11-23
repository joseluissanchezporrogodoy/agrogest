package com.example.joseluissanchez_porrogodoy.agrogest.ui.application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
