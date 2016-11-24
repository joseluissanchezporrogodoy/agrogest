package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Finca;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewFincaActivity extends BaseActivity {
    private static final String TAG = "NewFincaActivity";
    private static final String REQUIRED = "Required";
    public static final String FINCAEDITMODE = "fincaeditmode";
    public static final String UIDFINCA = "uidFinca";
    public static final String NAMEFINCA = "namefinca";
    private DatabaseReference mDatabase;
    private EditText mNameField;
    private FloatingActionButton mSubmitButton;
    private boolean mEditMode;
    private String mUidFinca;
    private String nameFinca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_finca);
        mNameField = (EditText) findViewById(R.id.field_name_finca);
        mEditMode = getIntent().getBooleanExtra(FINCAEDITMODE,false);
        if(mEditMode){
            mUidFinca= getIntent().getStringExtra(UIDFINCA);
            nameFinca= getIntent().getStringExtra(NAMEFINCA);
            mNameField.setText(nameFinca);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_new_finca);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFinca();
            }
        });

    }
    private void submitFinca() {
        final String name = mNameField.getText().toString();


        // Title is required
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return;
        }



        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Enviando...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        // [START_EXCLUDE]
                        if(!mEditMode) {
                            writeNewFinca(name);
                        }else {
                            editFinca(name);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
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
    private void writeNewFinca( String name) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("fincas").push().getKey();
        Finca finca = new Finca(name, key);
        Map<String, Object> postValues = finca.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/fincas/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }

    private void editFinca(String name) {

        mDatabase.child("fincas").child(mUidFinca).child("name").setValue(name);

    }
}
