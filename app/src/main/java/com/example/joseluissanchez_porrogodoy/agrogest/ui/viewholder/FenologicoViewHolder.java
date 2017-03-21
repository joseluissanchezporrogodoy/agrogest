package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.NewFenologicoActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Fenologico;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.EOFException;
import java.io.IOException;


/**
 * Created by joseluissanchez-porrogodoy on 30/11/2016.
 */

public class FenologicoViewHolder extends RecyclerView.ViewHolder {//implements //View.OnCreateContextMenuListener {
    public TextView dateView;
    public TextView stateStringView;
    public ImageView imageView;
    private Fenologico mFenologico;
    private View mItemview;
    public FenologicoViewHolder(View itemView) {
        super(itemView);

        dateView = (TextView) itemView.findViewById(R.id.fenologico_date);
        stateStringView = (TextView)itemView.findViewById(R.id.fenologico_state_string);
        imageView = (ImageView)itemView.findViewById(R.id.imageView3);
        mItemview=itemView;
        itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);

    }
    public void bind(Fenologico fenologico) {
        mFenologico = fenologico;
        dateView.setText(fenologico.date);
        if(fenologico.imageUrl!=null) {
            try {
                Bitmap image = decodeFromFirebaseBase64(fenologico.imageUrl);
                imageView.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stateStringView.setText(fenologico.stateString);

    }
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mFenologico != null) {
                MenuItem myActionItem = menu.add("Borrar");
                myActionItem.setOnMenuItemClickListener(mOnMyActionClickListener);
                MenuItem editarItem = menu.add("Editar");
                editarItem.setOnMenuItemClickListener(mOnEditarClickListener);
            }
        }
    };

    private final MenuItem.OnMenuItemClickListener mOnMyActionClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.child("fenologicos").child(mFenologico.uid).removeValue();
            databaseReference.child("cultivos-fenologicos").child(mFenologico.uidCultivo).child(mFenologico.uid).removeValue();
            return true;
        }
    };
    private final MenuItem.OnMenuItemClickListener mOnEditarClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(mItemview.getContext(),NewFenologicoActivity.class);
            intent.putExtra(NewFenologicoActivity.FENOLOGICOEDIT, true);
            intent.putExtra(NewFenologicoActivity.UIDFENOLOGICO,mFenologico.uid);
            intent.putExtra(NewFenologicoActivity.DATEFENOLOGICO,mFenologico.date);
            intent.putExtra(NewFenologicoActivity.STATEFENOLOGICO,mFenologico.state);
            intent.putExtra(NewFenologicoActivity.IMAGEURLFENOLOGICO,mFenologico.imageUrl);
            intent.putExtra(NewFenologicoActivity.EXTRA_CULTIVO_UID,mFenologico.uidCultivo);

            mItemview.getContext().startActivity(intent);
            return true;
        }
    };
}
