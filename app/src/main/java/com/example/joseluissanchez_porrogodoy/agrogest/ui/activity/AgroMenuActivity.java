package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.joseluissanchez_porrogodoy.agrogest.R;

public class AgroMenuActivity extends AppCompatActivity {
    public static final String EXTRA_CULTIVO_UID = "cultivo_uid";
    private String mUidCultivo;
    private ImageButton mBtnFeno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agro_menu);
        mUidCultivo = getIntent().getStringExtra(EXTRA_CULTIVO_UID);
        if (mUidCultivo == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        mBtnFeno = (ImageButton)findViewById(R.id.btnFeno);
        mBtnFeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgroMenuActivity.this, FenologicoListActivity.class);
                intent.putExtra(EXTRA_CULTIVO_UID, mUidCultivo);
                startActivity(intent);
            }
        });
    }
}
