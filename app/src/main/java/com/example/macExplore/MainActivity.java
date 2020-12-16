package com.example.macExplore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.macExplore.ui.login.LoginActivity;
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
    int images[] = {R.drawable.aolinrice, R.drawable.cc, R.drawable.lc, R.drawable.aolinrice_copy};
    Button button;

    // Location Data
    List<Location> locationData;

    // Used in addLocation function
    Map<String, Object> locationMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Retrieves location data from the database (proceeds to build UI)
        getLocations(db);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setActionBar(toolbar);

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

    /*
     * This function currently gets and logs the current locations in the database
     */
    protected void getLocations(FirebaseFirestore db){
        db.collection("locations").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Creates an array of locations with the proper size
                            locationData = new ArrayList<Location>(task.getResult().size());

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                // Keeping these commented logs to use as a reference
                                // Log.d("CHECK", document.getId() + " => " + document.getData().values());
                                // Log.d("CHECK STRING", document.getData().get("contact").toString());

                                // Creates a new location object with the data pulled from the database
                                Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString(), doc.getId());
                                // Logs the location object for testing purposes
                                Log.d("LOCATION OBJECT", location.documentID + " - " + location.name + ", " + location.contact + ", " + location.address);

                                // Adds location object to locationData array
                                locationData.add(location);
                            }
                        } else {
                            Log.w("ERROR", "Error getting documents.", task.getException());
                        }

                        // Uses the retrieved location information to build the UI for the app
                        buildUI(locationData);
                    }
                });
    }

    protected void buildUI(List<Location> locationData){
//            String names[] = new String[locationData.length];
//            String contacts[] = new String[locationData.length];
//            String documentIDs[] = new String[locationData.length];
//
//            for (int i = 0; i < locationData.length; i++){
//                names[i] = locationData[i].name;
//                contacts[i] = locationData[i].contact;
//                documentIDs[i] = locationData[i].documentID;
//                // Logs for testing
//                Log.d("BUILDUI NAME AT INDEX " + i, names[i]);
//                Log.d("BUILDUI CONTACT AT INDEX " + i, contacts[i]);
//                Log.d("BUILDUI DOCID AT INDEX " + i, documentIDs[i]);
//            }

        recyclerView = findViewById(R.id.mainrecyclerView);

//            s1 = getResources().getStringArray(R.array.locations);
//            s2 = getResources().getStringArray(R.array.contacts);

        //initialization of class + passing all values:
//            LocationAdapter locationAdapter = new LocationAdapter(this, s1=names, s2=contacts, images, button);
        LocationAdapter locationAdapter = new LocationAdapter(this, locationData, images, button);
        recyclerView.setAdapter(locationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}