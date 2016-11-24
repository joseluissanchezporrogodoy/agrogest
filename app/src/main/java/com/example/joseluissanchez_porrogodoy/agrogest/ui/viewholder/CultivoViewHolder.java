package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.NewCultivoActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Cultivo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.CultivoListActivity.EXTRA_PARCELA_UID;
import static com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.ParcelaListActivity.EXTRA_FINCA_UID;

/**
 * Created by joseluissanchez-porrogodoy on 24/11/2016.
 */

public class CultivoViewHolder extends RecyclerView.ViewHolder {//implements //View.OnCreateContextMenuListener {
    public TextView nameView;
    public TextView areaView;
    private Cultivo mCultivo;
    private View mItemview;
    public CultivoViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.cultivo_name);
        mItemview=itemView;
        areaView = (TextView) itemView.findViewById(R.id.cultivo_area);
        itemView.setOnCreateContextMenuListener(mOnCreateContextMenuListener);

    }
    public void bind(Cultivo cultivo) {
        mCultivo = cultivo;
        nameView.setText(cultivo.name);
        areaView.setText(cultivo.area);
    }


    private final View.OnCreateContextMenuListener mOnCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (mCultivo != null) {
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
            databaseReference.child("cultivos").child(mCultivo.uid).removeValue();
            databaseReference.child("parcelas-cultivos").child(mCultivo.uidParcela).child(mCultivo.uid).removeValue();
            return true;
        }
    };
    private final MenuItem.OnMenuItemClickListener mOnEditarClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(mItemview.getContext(),NewCultivoActivity.class);
            intent.putExtra(NewCultivoActivity.CULTIVOEDIT, true);
            intent.putExtra(NewCultivoActivity.UIDCULTIVO,mCultivo.uid);
            intent.putExtra(NewCultivoActivity.NAMECULTIVO,mCultivo.name);
            intent.putExtra(NewCultivoActivity.AREACULTIVO,mCultivo.area);
            intent.putExtra(EXTRA_PARCELA_UID,mCultivo.uidParcela);
            mItemview.getContext().startActivity(intent);
            return true;
        }
    };
}
