package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.parcelas.ListaDeParcelas;

public class ParcelaListActivity extends BaseActivity {

    public static final String EXTRA_FINCA_UID = "finca_uid";
    private String uidFinca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcela_list);
        // Get post key from intent
        uidFinca = getIntent().getStringExtra(EXTRA_FINCA_UID);
        if (uidFinca == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fbParcela);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParcelaListActivity.this, NewParcelaActivity.class);
                intent.putExtra(EXTRA_FINCA_UID, uidFinca);
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
            ListaDeParcelas listaDeParcelas = ListaDeParcelas.newInstance(uidFinca);


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.parcela_container, listaDeParcelas).commit();
        }
    }
}
