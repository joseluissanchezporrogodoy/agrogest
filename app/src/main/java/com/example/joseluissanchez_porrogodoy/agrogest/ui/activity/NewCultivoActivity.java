package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Cultivo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

;
import static com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.CultivoListActivity.EXTRA_PARCELA_UID;


public class NewCultivoActivity extends BaseActivity {
    private static final String TAG = "NewCultivoActivity";
    private static final String REQUIRED = "Required";
    public static final String CULTIVOEDIT = "cultivoeditmode";
    public static final String UIDCULTIVO = "uidCultivo";
    public static final String NAMECULTIVO = "namecultivo";
    public static final String AREACULTIVO = "areacultivo";
    private DatabaseReference mDatabase;
    private EditText mNameField;
    private EditText mAreaField;
    private FloatingActionButton mSubmitButton;
    private String uidParcela;
    private boolean mEditMode;
    private String mUidCultivo;
    private String nameCultivo;
    private String areaCultivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cultivo);
        mNameField = (EditText) findViewById(R.id.field_name_cultivo);
        mAreaField = (EditText) findViewById(R.id.field_area_cultivo);
        uidParcela = getIntent().getStringExtra(EXTRA_PARCELA_UID);
        if (uidParcela == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        mEditMode = getIntent().getBooleanExtra(CULTIVOEDIT,false);
        if(mEditMode){
            mUidCultivo= getIntent().getStringExtra(UIDCULTIVO);
            nameCultivo= getIntent().getStringExtra(NAMECULTIVO);
            areaCultivo= getIntent().getStringExtra(AREACULTIVO);
            mNameField.setText(nameCultivo);
            mAreaField.setText(areaCultivo);

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_new_cultivo);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCultivo();
            }
        });
    }
    private void submitCultivo() {
        final String name = mNameField.getText().toString();
        final String area = mAreaField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return;
        }



        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Enviando...", Toast.LENGTH_SHORT).show();


        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        if(!mEditMode) {
                            writeNewCultivo(name,area);
                        }else{
                            editCultivo(name,area);
                        }


                        setEditingEnabled(true);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }
    private void setEditingEnabled(boolean enabled) {
        mNameField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }
    private void writeNewCultivo( String name,String area) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("cultivos").push().getKey();
        Cultivo cultivo = new Cultivo(key,name,uidParcela,area);
        Map<String, Object> postValues = cultivo.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/cultivos/" + key, postValues);
        childUpdates.put("/parcelas-cultivos/" + uidParcela + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
    private void editCultivo(String name, String area) {

        mDatabase.child("cultivos").child(mUidCultivo).child("name").setValue(name);
        mDatabase.child("cultivos").child(mUidCultivo).child("area").setValue(area);
        mDatabase.child("parcelas-cultivos").child(uidParcela).child(mUidCultivo).child("name").setValue(name);
        mDatabase.child("parcelas-cultivos").child(uidParcela).child(mUidCultivo).child("area").setValue(area);
    }

}
