package com.example.mvp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.aolinrice, R.drawable.cc, R.drawable.lc};
    Button button;
    //  List<Location> locationData;
    Map<String, Object> locationMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.contacts);

        //initialization of class + passing all values:
        MyAdaptor myAdaptor = new MyAdaptor(this, s1, s2, images, button);
        recyclerView.setAdapter(myAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add a new location to the database
//        addLocation(db, "DeWitt Wallace Library", "651-696-6377", "1600 Grand Ave, St Paul, MN 55105");

        // Retrieves location data from the database
//        getLocations(db);

    }


    /**
    * This function adds locations to the database
    */
    protected void addLocation(FirebaseFirestore db, String name, String contact, String address){
        locationMap.put("name", name);
        locationMap.put("contact", contact);
        locationMap.put("address", address);
        db.collection("locations").add(locationMap);
    }

    /*
    * This function currently gets and logs the current locations in the database
    *  TODO: return location information to be used for location list?
     */
    protected void getLocations(FirebaseFirestore db){
        db.collection("locations").get()
          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                  if (task.isSuccessful()) {
                      for (QueryDocumentSnapshot doc : task.getResult()) {
                          // Log.d("CHECK", document.getId() + " => " + document.getData().values());
                          // Log.d("CHECK STRING", document.getData().get("contact").toString());
                          Location location = new Location(doc.getData().get("name").toString(), doc.getData().get("contact").toString(), doc.getData().get("address").toString());
                          Log.d("LOCATION OBJECTTEST", location.name + ", " + location.contact + ", " + location.address);
                          // locationData.add(location);
                      }
                  } else {
                      Log.w("ERROR", "Error getting documents.", task.getException());
                  }
              }
          });
    }

    // This is a sample function of addUser function (example to build from)
//    protected void addUser(FirebaseFirestore db, String first, String last, int born, String email){
//        user.put("first", first);
//        user.put("last", last);
//        user.put("born", born);
//        user.put("email", email);
//        db.collection("users").add(user);
//    }

}