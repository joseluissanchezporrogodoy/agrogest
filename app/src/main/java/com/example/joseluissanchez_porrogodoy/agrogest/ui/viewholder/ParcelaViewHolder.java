package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.NewFincaActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.NewParcelaActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Finca;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Parcela;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.ParcelaListActivity.EXTRA_FINCA_UID;

/**
 * Created by joseluissanchez-porrogodoy on 23/11/2016.
 */

public class ParcelaViewHolder extends RecyclerView.ViewHolder {//implements //View.OnCreateContextMenuListener {
    public TextView nameView;
    public TextView areaView;
    private Parcela mParcela;
    private View mItemview;
    public ParcelaViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.parcela_name);
        mItemview=itemView;
        areaView = (TextView) itemView.findViewById(R.id.parcela_area);
        itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);

    }
    public void bind(Parcela parcela) {
        mParcela = parcela;
        nameView.setText(parcela.name);
        areaView.setText(parcela.area);
    }


    private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mParcela != null) {
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
            databaseReference.child("parcelas").child(mParcela.uid).removeValue();
            databaseReference.child("fincas-parcelas").child(mParcela.uidFinca).child(mParcela.uid).removeValue();
            return true;
        }
    };
    private final MenuItem.OnMenuItemClickListener mOnEditarClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(mItemview.getContext(),NewParcelaActivity.class);
            intent.putExtra(NewParcelaActivity.PARCELAEDIT, true);
            intent.putExtra(NewParcelaActivity.UIDPARCELA,mParcela.uid);
            intent.putExtra(NewParcelaActivity.NAMEPARCELA,mParcela.name);
            intent.putExtra(NewParcelaActivity.AREAPARCELA,mParcela.area);
            intent.putExtra(EXTRA_FINCA_UID,mParcela.uidFinca);
            mItemview.getContext().startActivity(intent);
            return true;
        }
    };
}
