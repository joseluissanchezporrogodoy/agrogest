package com.example.joseluissanchez_porrogodoy.agrogest.ui.fragment.fenologico;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joseluissanchez_porrogodoy.agrogest.R;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.activity.AgroMenuActivity;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.models.Fenologico;
import com.example.joseluissanchez_porrogodoy.agrogest.ui.viewholder.FenologicoViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public  abstract class FenologicoListFragment extends Fragment {

    private static final String TAG = "FenologicoListFragment";
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Fenologico,FenologicoViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    public FenologicoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parcela_list, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = (RecyclerView) rootView.findViewById(R.id.parcela_list);
        mRecycler.setHasFixedSize(true);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);

        mAdapter = new FirebaseRecyclerAdapter<Fenologico, FenologicoViewHolder>(Fenologico.class, R.layout.item_fenologico, FenologicoViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final FenologicoViewHolder viewHolder, final Fenologico model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String uuidFenologico = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        ///TODO ver el detalle
//                        Intent intent = new Intent(getActivity(), AgroMenuActivity.class);
//                        intent.putExtra(AgroMenuActivity.EXTRA_CULTIVO_UID, uuidFenologico);
//                        startActivity(intent);

                    }
                });

                viewHolder.bind(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
    public abstract Query getQuery(DatabaseReference databaseReference);
}

