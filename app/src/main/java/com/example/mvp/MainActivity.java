/*
 * Main Activity
 */

package com.example.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.aolinrice, R.drawable.cc, R.drawable.lc, R.drawable.aolinrice_copy};
    Button button;

    // Location Data
    List<Location> locationData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Retrieves location data from the database (proceeds to build UI)
        getLocations(db);
        FloatingActionButton addLocationFab = findViewById(R.id.addLocation);
        addLocationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
                startActivity(intent);
            }
        });


//     FOR REFERENCE
//        Add a new location to the database
//        addLocation(db, "DeWitt Wallace Library", "651-696-6377", "1600 Grand Ave, St Paul, MN 55105");
//        Retrieves location data of a requested location from the database
//        getOneLocation(db, "name", "Olin Rice");
    }


    /**
     * This function gets the information of one location by passing in the key and value to make a
     * conditional query. This function shows how you can use .whereEqualTo() to perform simple
     * queries.
     *
     * Function Example:   getOneLocation(db, "name", "Leonard Center")
     *
     */
    public void getOneLocation(FirebaseFirestore db, String key, String value){
        db.collection("locations").whereEqualTo(key, value).get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot doc : task.getResult()) {
                          // Creates a new location object with the data pulled of the location that fits the condition
                          Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString(), doc.getId());
                          // Logs the location object for testing purposes
                          Log.d("SINGLE LOCATION OBJECT", location.documentID + " - " + location.name + ", " + location.contact + ", " + location.address);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }


    /*
     * This function currently gets and logs the current locations in the database
     */
    public void getLocations(FirebaseFirestore db){
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

        public void buildUI(List<Location> locationData){
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

            recyclerView = findViewById(R.id.recyclerView);

//            s1 = getResources().getStringArray(R.array.locations);
//            s2 = getResources().getStringArray(R.array.contacts);

            //initialization of class + passing all values:
//            MyAdaptor myAdaptor = new MyAdaptor(this, s1=names, s2=contacts, images, button);
            MyAdaptor myAdaptor = new MyAdaptor(this, locationData, images, button);
            recyclerView.setAdapter(myAdaptor);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

}