package com.example.joseluissanchez_porrogodoy.agrogest.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.customviews.DatePickerView;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Cultivo;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Fenologico;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class NewFenologicoActivity extends BaseActivity {
    private static final String TAG = "NewFenologicoActivity";
    private static final String REQUIRED = "Required";
    public static final String FENOLOGICOEDIT = "fenologicoeditmode";
    public static final String UIDFENOLOGICO = "uidFenologico";
    public static final String DATEFENOLOGICO = "datefenologico";
    public static final String STATEFENOLOGICO = "statefenologico";
    public static final String IMAGEURLFENOLOGICO = "imageurlfenologico";
    public static final String EXTRA_FENOLOGICO_UID = "fenologico_uid";
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    public static final String EXTRA_CULTIVO_UID = "cultivo_uid";
    private String mUidCultivo;
    ImageButton imageButton;
    ImageView mImageLabel;
    RadioGroup mState;
    private DatePickerView mDate;
    String mImageURL;
    String imageEncoded;

    private DatabaseReference mDatabase;
    private FloatingActionButton mSubmitButton;
    private boolean mEditMode;
    private String uidFenolofico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fenologico);
        mUidCultivo = getIntent().getStringExtra(EXTRA_CULTIVO_UID);
        if (mUidCultivo == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }
        imageButton =(ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLaunchCamera();
            }
        });
        mImageLabel= (ImageView)findViewById(R.id.imageView);
        mState = (RadioGroup)findViewById(R.id.radio);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDate = (DatePickerView)findViewById(R.id.fenologico_piker);
        mEditMode = getIntent().getBooleanExtra(FENOLOGICOEDIT,false);
        if(mEditMode){
            uidFenolofico = getIntent().getStringExtra(UIDFENOLOGICO);
            mImageURL = getIntent().getStringExtra(IMAGEURLFENOLOGICO);
            if (mImageURL!=null) {
                try {
                    Bitmap image = decodeFromFirebaseBase64(mImageURL);
                    mImageLabel.setImageBitmap(image);
                    imageEncoded=mImageURL;
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            int selectedRadio= Integer.parseInt(getIntent().getStringExtra(STATEFENOLOGICO));
            RadioButton selectedView = (RadioButton)findViewById(selectedRadio);
            String texto = selectedView.getText().toString();
            mState.check(selectedRadio);
            if(null!= getIntent().getStringExtra(DATEFENOLOGICO)) {
                mDate.setDate(getIntent().getStringExtra(DATEFENOLOGICO));
            }
        }
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fbFenologicoSubmit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFenologico();
            }
        });

    }

    private void submitFenologico(){

            //Default dd/mm/yyyy
        String dateFormat = "dd/MM/yyyy";
        final SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        if(mDate.getDate() == null){
            Toast.makeText(this, "Introduce fecha", Toast.LENGTH_SHORT).show();
            return;
        }
        final String date = format.format(mDate.getDate().getTime());
        final String state = String.valueOf(mState.getCheckedRadioButtonId());
        RadioButton rb = (RadioButton) findViewById(mState.getCheckedRadioButtonId());
        final String stateString = rb.getText().toString();
        final String imageUrl = imageEncoded;





        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Enviando...", Toast.LENGTH_SHORT).show();


        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(!mEditMode) {
                            writeNewFenologico(date, state,stateString, imageUrl);
                        }else{
                            editFenologico(date, state, imageUrl,stateString);
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
    private void writeNewFenologico( String date,String state,String stateString ,String mImageURL) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("fenologicos").push().getKey();
        Fenologico fenologico = new Fenologico(key,mUidCultivo,date,state,stateString,mImageURL);
        Map<String, Object> postValues = fenologico.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/fenologicos/" + key, postValues);
        childUpdates.put("/cultivos-fenologicos/" + mUidCultivo + "/" + key, postValues);
        mDatabase.updateChildren(childUpdates);
    }
    private void editFenologico(String date,String state,String mImageURL, String stateString) {

        mDatabase.child("fenologicos").child(uidFenolofico).child("date").setValue(date);
        mDatabase.child("fenologicos").child(uidFenolofico).child("state").setValue(state);
        mDatabase.child("fenologicos").child(uidFenolofico).child("imageUrl").setValue(mImageURL);
        mDatabase.child("fenologicos").child(uidFenolofico).child("stateString").setValue(stateString);

        mDatabase.child("cultivos-fenologicos").child(mUidCultivo).child(uidFenolofico).child("date").setValue(date);
        mDatabase.child("cultivos-fenologicos").child(mUidCultivo).child(uidFenolofico).child("state").setValue(state);
        mDatabase.child("cultivos-fenologicos").child(mUidCultivo).child(uidFenolofico).child("imageUrl").setValue(mImageURL);
        mDatabase.child("cultivos-fenologicos").child(mUidCultivo).child(uidFenolofico).child("stateString").setValue(stateString);
    }

    private void setEditingEnabled(boolean enabled) {

        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
    

}
