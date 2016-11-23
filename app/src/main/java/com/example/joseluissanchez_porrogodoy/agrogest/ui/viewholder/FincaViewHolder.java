package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Finca;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */

public class FincaViewHolder extends RecyclerView.ViewHolder {//implements //View.OnCreateContextMenuListener {
    public TextView nameView;
    private Finca mFinca;

    public FincaViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.finca_name);
        itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);
       // itemView.setOnCreateContextMenuListener(this);
    }
    public void bind(Finca finca) {
        mFinca= finca;
        nameView.setText(finca.name);



    }

//    @Override
//    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//        contextMenu.setHeaderTitle("Borrar");
//        Finca finca = (Finca)view.getTag();
//        contextMenu.add(0, view.getId(), 0, "Si");
//        contextMenu.add(0, view.getId(), 0, "Cancelar");
//    }
    private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mFinca!= null) {
                MenuItem myActionItem = menu.add("Borrar");
                myActionItem.setOnMenuItemClickListener(mOnMyActionClickListener);
            }
        }
    };

    private final MenuItem.OnMenuItemClickListener mOnMyActionClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            //todo: process item click, mData is available here!!!
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.child("fincas").child(mFinca.uid).removeValue();
            return true;
        }
    };
}
