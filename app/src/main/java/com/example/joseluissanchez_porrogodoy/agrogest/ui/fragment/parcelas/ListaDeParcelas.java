package com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.parcelas;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by joseluissanchez-porrogodoy on 23/11/2016.
 */

public class ListaDeParcelas extends ParcelaListFragment {

    private String muidFinca;

    public ListaDeParcelas() {
    }

    public static ListaDeParcelas newInstance(String uidFinca) {
        ListaDeParcelas myFragment = new ListaDeParcelas();
        Bundle args = new Bundle();
        args.putString("uidFinca", uidFinca);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        String uidFinca = (String)getArguments().get("uidFinca");
        Query myTopPostsQuery = databaseReference.child("fincas-parcelas").child(uidFinca).orderByChild("starCount");


        return myTopPostsQuery;
    }
}
