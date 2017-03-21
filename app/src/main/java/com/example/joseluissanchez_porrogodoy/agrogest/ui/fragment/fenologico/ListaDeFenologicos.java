package com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.fenologico;

import android.os.Bundle;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by joseluissanchez-porrogodoy on 30/11/2016.
 */

public class ListaDeFenologicos extends FenologicoListFragment {

    private String uidCultivo;

    public ListaDeFenologicos() {
    }

    public static ListaDeFenologicos newInstance(String uidCultivo) {
        ListaDeFenologicos myFragment = new ListaDeFenologicos();
        Bundle args = new Bundle();
        args.putString("uidCultivo", uidCultivo);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        String uidCultivo = (String)getArguments().get("uidCultivo");
        Query myTopPostsQuery = databaseReference.child("cultivos-fenologicos").child(uidCultivo).orderByChild("starCount");


        return myTopPostsQuery;
    }
}
