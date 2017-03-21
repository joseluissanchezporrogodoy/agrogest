package com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.cultivos;

import android.os.Bundle;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by joseluissanchez-porrogodoy on 24/11/2016.
 */

public class ListaDeCultivos extends CultivoListFragment {

    private String muidParcela;

    public ListaDeCultivos() {
    }

    public static ListaDeCultivos newInstance(String uidParcela) {
        ListaDeCultivos myFragment = new ListaDeCultivos();
        Bundle args = new Bundle();
        args.putString("uidParcela", uidParcela);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        String uidParcela = (String)getArguments().get("uidParcela");
        Query myTopPostsQuery = databaseReference.child("parcelas-cultivos").child(uidParcela).orderByChild("starCount");


        return myTopPostsQuery;
    }
}