package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Finca;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Parcela;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by joseluissanchez-porrogodoy on 23/11/2016.
 */

public class ParcelaViewHolder extends RecyclerView.ViewHolder {//implements //View.OnCreateContextMenuListener {
    public TextView nameView;
    public TextView areaView;
    private Parcela mParcela;

    public ParcelaViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.parcela_name);
        areaView = (TextView) itemView.findViewById(R.id.parcela_area);
        itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);

    }
    public void bind(Parcela parcela) {
        mParcela = parcela;
        nameView.setText(parcela.name);
    }


    private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mParcela != null) {
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
            databaseReference.child("parcelas").child(mParcela.uid).removeValue();
            databaseReference.child("fincas-parcelas").child(mParcela.uidFinca).child(mParcela.uid).removeValue();
            return true;
        }
    };
}
