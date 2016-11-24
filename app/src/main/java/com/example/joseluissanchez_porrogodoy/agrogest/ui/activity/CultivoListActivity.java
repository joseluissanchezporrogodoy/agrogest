package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.cultivos.ListaDeCultivos;


public class CultivoListActivity extends AppCompatActivity {

    public static final String EXTRA_PARCELA_UID = "parcela_uid";
    private String uidParcela;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcela_list);
        // Get post key from intent
        uidParcela = getIntent().getStringExtra(EXTRA_PARCELA_UID);
        if (uidParcela == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbParcela);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CultivoListActivity.this, NewCultivoActivity.class);
                intent.putExtra(EXTRA_PARCELA_UID, uidParcela);
                startActivity(intent);
            }
        });


        if (findViewById(R.id.parcela_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            ListaDeCultivos listaDeCultivos = ListaDeCultivos.newInstance(uidParcela);


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.parcela_container, listaDeCultivos).commit();
        }
    }
}
