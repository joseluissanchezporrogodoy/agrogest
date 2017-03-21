package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.NewFincaActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Finca;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */

public class FincaViewHolder extends RecyclerView.ViewHolder {//implements //View.OnCreateContextMenuListener {
    public TextView nameView;
    private Finca mFinca;
    private View mItemview;

    public FincaViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.finca_name);
        mItemview=itemView;
        itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);

    }
    public void bind(Finca finca) {
        mFinca= finca;
        nameView.setText(finca.name);


    }


    private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mFinca!= null) {
                MenuItem borrarItem = menu.add("Borrar");
                borrarItem.setOnMenuItemClickListener(mOnBorrarClickListener);
                MenuItem editarItem = menu.add("Editar");
                editarItem.setOnMenuItemClickListener(mOnEditarClickListener);
            }
        }
    };

    private final MenuItem.OnMenuItemClickListener mOnBorrarClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.child("fincas").child(mFinca.uid).removeValue();
            return true;
        }
    };
    private final MenuItem.OnMenuItemClickListener mOnEditarClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(mItemview.getContext(),NewFincaActivity.class);
            intent.putExtra(NewFincaActivity.FINCAEDITMODE, true);
            intent.putExtra(NewFincaActivity.UIDFINCA,mFinca.uid);
            intent.putExtra(NewFincaActivity.NAMEFINCA,mFinca.name);
            mItemview.getContext().startActivity(intent);
            return true;
        }
    };
}
