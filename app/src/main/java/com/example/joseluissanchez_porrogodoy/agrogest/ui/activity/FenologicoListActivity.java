package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.cultivos.ListaDeCultivos;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.fenologico.ListaDeFenologicos;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.login.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FenologicoListActivity extends BaseActivity {
    public static final String EXTRA_CULTIVO_UID = "cultivo_uid";
    private String uidCultivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenologico_list);
        uidCultivo = getIntent().getStringExtra(EXTRA_CULTIVO_UID);
        if (uidCultivo == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbFenologico);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FenologicoListActivity.this, NewFenologicoActivity.class);
                intent.putExtra(EXTRA_CULTIVO_UID, uidCultivo);
                startActivity(intent);
            }
        });
        if (findViewById(R.id.fenologico_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            ListaDeFenologicos listaDeCultivos = ListaDeFenologicos.newInstance(uidCultivo);


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fenologico_container, listaDeCultivos).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
