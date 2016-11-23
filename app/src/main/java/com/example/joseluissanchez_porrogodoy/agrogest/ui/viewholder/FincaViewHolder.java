package com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Finca;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */

public class FincaViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;


    public FincaViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.finca_name);
    }
    public void bind(Finca finca) {
        nameView.setText(finca.name);

    }
}
