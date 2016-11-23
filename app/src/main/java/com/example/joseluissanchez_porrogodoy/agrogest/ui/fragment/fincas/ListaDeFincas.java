package com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.fincas;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */

public class ListaDeFincas extends FincaListFragment {

    public ListaDeFincas() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query myTopPostsQuery = databaseReference.child("fincas").orderByChild("starCount");


        return myTopPostsQuery;
    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        return super.onContextItemSelected(item);
//
//    }
}
