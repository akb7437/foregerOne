package com.becker.foreger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home extends AppCompatActivity implements View.OnClickListener {

    public Button index, map;
    private FirebaseFirestore firebaseFirestore1;
    private RecyclerView iMushroomFeed;
    private FirestoreRecyclerAdapter adapter1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseFirestore1 = FirebaseFirestore.getInstance();
        iMushroomFeed = findViewById(R.id.mushroomFeedList);

        index = (Button) findViewById(R.id.goToIdentifier);
        index.setOnClickListener(this);

        map = (Button) findViewById(R.id.goToMap);
        map.setOnClickListener(this);

        Query query = firebaseFirestore1.collection("Maps data");

        FirestoreRecyclerOptions<MushroomFeed>
                options = new FirestoreRecyclerOptions.Builder<MushroomFeed>()
                .setQuery(query, MushroomFeed.class).build();

        adapter1 = new FirestoreRecyclerAdapter<MushroomFeed, Home.MushroomFeedViewHolder>(options) {
            @NonNull
            @Override
            public Home.MushroomFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mushroom_feed_item, parent, false);
                return new Home.MushroomFeedViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Home.MushroomFeedViewHolder holder, int position, @NonNull MushroomFeed model) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String currentUser = user.getEmail();
                holder.list_usermushname.setText(model.getMushroomFound());
                holder.list_user.setText(currentUser);


            }
        };

        iMushroomFeed.setHasFixedSize(true);
        iMushroomFeed.setLayoutManager(new LinearLayoutManager(this));
        iMushroomFeed.setAdapter(adapter1);
    }

    private class MushroomFeedViewHolder extends RecyclerView.ViewHolder{

        private TextView list_user;
        private TextView list_usermushname;



        public MushroomFeedViewHolder(@NonNull View itemView) {
            super(itemView);

            list_user = itemView.findViewById(R.id.userComment);
            list_usermushname = itemView.findViewById(R.id.userMushName);


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.goToIdentifier:
                startActivity(new Intent(Home.this, MushroomFinder.class));
                break;
            case R.id.goToMap:
                startActivity(new Intent(Home.this, MapsActivity.class));
                break;

        }
    }


}
