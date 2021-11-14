package com.becker.foreger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MushroomFinder extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView iMushroomList;
    private FirestoreRecyclerAdapter adapter;
    private DatabaseReference aRef;
    private FirebaseStorage astore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mushroom_finder);
        firebaseFirestore = FirebaseFirestore.getInstance();
        iMushroomList = findViewById(R.id.mushroomList);
        aRef = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // query
        Query query = firebaseFirestore.collection("Mushrooms");

        FirestoreRecyclerOptions<Mushroom>
                options = new FirestoreRecyclerOptions.Builder<Mushroom>()
                .setQuery(query, Mushroom.class).build();

        adapter = new FirestoreRecyclerAdapter<Mushroom, MushroomViewHolder>(options) {
            @NonNull
            @Override
            public MushroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mushroom_item, parent, false);
                return new MushroomViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MushroomViewHolder holder, int position, @NonNull Mushroom model) {

                holder.list_name.setText(model.getName());
                holder.list_location.setText(model.getLocation());
                holder.list_edibility.setText(model.getEdible());
                holder.list_description.setText(model.getDescription());
            }
        };

        iMushroomList.setHasFixedSize(true);
        iMushroomList.setLayoutManager(new LinearLayoutManager(this));
        iMushroomList.setAdapter(adapter);

    }

    private class MushroomViewHolder extends RecyclerView.ViewHolder{

        private ImageView list_image;
        private TextView list_name;
        private TextView list_location;
        private TextView list_edibility;
        private TextView list_description;

        public MushroomViewHolder(@NonNull View itemView) {
            super(itemView);

            list_image = itemView.findViewById(R.id.mushImage);
            list_name = itemView.findViewById(R.id.MushName);
            list_location = itemView.findViewById(R.id.MushLocation);
            list_edibility = itemView.findViewById(R.id.MushEdibility);
            list_description = itemView.findViewById(R.id.MushDescription);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    }

