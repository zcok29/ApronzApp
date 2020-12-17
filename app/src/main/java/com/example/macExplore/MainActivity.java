package com.example.macExplore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.macExplore.login.LoginActivity;
import com.example.macExplore.login.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;

    String s1[], s2[];
    Button button;

    List<Location> locationData;

    Map<String, Object> locationMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getLocations(db);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.addLocation:
                startActivity(new Intent(MainActivity.this,AddLocationActivity.class));
                return true;
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** This function currently gets and logs the current locations in the database
     */
    protected void getLocations(FirebaseFirestore db){
        db.collection("locations").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            locationData = new ArrayList<Location>(task.getResult().size());

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString(), doc.getId());
                                Log.d("LOCATION OBJECT", location.documentID + " - " + location.name + ", " + location.contact + ", " + location.address);

                                locationData.add(location);
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }

                        buildUI(locationData);
                    }
                });
    }

    /**
     * Build the UI using recycler view.
     * @param locationData
     */
    protected void buildUI(List<Location> locationData){
        int[] images = new int[locationData.size()];
        for(int i = 0; i < locationData.size(); i++){
            images[i] = R.drawable.adapter_img;
        }
        recyclerView = findViewById(R.id.mainRecyclerView);
        LocationAdapter locationAdapter = new LocationAdapter(this, locationData, images, button);
        recyclerView.setAdapter(locationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}