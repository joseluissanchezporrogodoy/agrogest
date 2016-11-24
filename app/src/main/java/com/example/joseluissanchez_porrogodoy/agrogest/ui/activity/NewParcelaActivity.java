package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Parcela;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.ParcelaListActivity.EXTRA_FINCA_UID;

public class NewParcelaActivity extends BaseActivity {
    private static final String TAG = "NewParcelaActivity";
    private static final String REQUIRED = "Required";
    public static final String PARCELAEDIT = "parcelaeditmode";
    public static final String UIDPARCELA = "uidParcela";
    public static final String NAMEPARCELA = "nameparcela";
    public static final String AREAPARCELA = "areaparcela";
    private DatabaseReference mDatabase;
    private EditText mNameField;
    private EditText mAreaField;
    private FloatingActionButton mSubmitButton;
    private String uidFinca;
    private boolean mEditMode;
    private String mUidParcela;
    private String nameParcela;
    private String areaParcela;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parcela);
        mNameField = (EditText) findViewById(R.id.field_name_parcela);
        mAreaField = (EditText) findViewById(R.id.field_area_parcela);
        uidFinca = getIntent().getStringExtra(EXTRA_FINCA_UID);
        if (uidFinca == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        mEditMode = getIntent().getBooleanExtra(PARCELAEDIT,false);
        if(mEditMode){
            mUidParcela= getIntent().getStringExtra(UIDPARCELA);
            nameParcela= getIntent().getStringExtra(NAMEPARCELA);
            areaParcela= getIntent().getStringExtra(AREAPARCELA);
            mNameField.setText(nameParcela);
            mAreaField.setText(areaParcela);

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_new_parcela);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitParcela();
            }
        });
    }
    private void submitParcela() {
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
                            writeNewParcela(name,area);
                        }else{
                            editParcela(name,area);
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
    private void writeNewParcela( String name,String area) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("parcelas").push().getKey();
        Parcela parcela = new Parcela(key,name,uidFinca,area);
        Map<String, Object> postValues = parcela.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/parcelas/" + key, postValues);
        childUpdates.put("/fincas-parcelas/" + uidFinca + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
    private void editParcela(String name, String area) {

        mDatabase.child("parcelas").child(mUidParcela).child("name").setValue(name);
        mDatabase.child("parcelas").child(mUidParcela).child("area").setValue(area);
        mDatabase.child("fincas-parcelas").child(uidFinca).child(mUidParcela).child("name").setValue(name);
        mDatabase.child("fincas-parcelas").child(uidFinca).child(mUidParcela).child("area").setValue(area);
    }

}
